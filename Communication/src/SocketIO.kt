import signals.Signal
import java.io.*
import java.util.concurrent.ArrayBlockingQueue
import kotlin.concurrent.thread

class SocketIO(private val adapter: SignalAdapter, inputStream: InputStream, outputStream: OutputStream) {
    private val signals = ArrayBlockingQueue<Signal>(20, true)
    private val asyncInput = AsyncInput(inputStream)
    private val asyncOutput = AsyncOutput(outputStream)

    fun stop(){
        asyncInput.stop()
        asyncOutput.stop()
    }

    fun sendData(signal: Signal){
        signals.put(signal)
    }

    inner class AsyncInput(input: InputStream) : AsyncStream(input){
        init {
            thread {
                try {
                    val stream = ObjectInputStream(input)
                    while(!stopped){
                        val signal = Signal.fromBytesStream(stream)
                        adapter.inputSignal(signal, this@SocketIO)
                    }
                } catch (ex: InterruptedException){
                    ex.printStackTrace()
                }
            }
        }
    }

    inner class AsyncOutput(output: OutputStream) : AsyncStream(output){
        init{
            thread {
                try {
                    val stream = ObjectOutputStream(output)
                    while(!stopped){
                        signals.take().toBytesStream(stream)
                    }
                } catch (ex: InterruptedException){
                    ex.printStackTrace()
                }
            }
        }
    }
}