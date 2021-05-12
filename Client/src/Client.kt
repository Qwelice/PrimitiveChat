import signals.*
import java.net.Socket

class Client(private val host: String, private val port: Int): SignalAdapter{
    private val cSocket: Socket = Socket(this.host, this.port)
    private val sio: SocketIO = SocketIO(this,
        cSocket.getInputStream(), cSocket.getOutputStream())

    override fun inputSignal(signal: Signal, comm: SocketIO) {
        when(signal.getType()){
            "Success" -> {
                val s = signal as SignalSuccess
                println(s.reason)
            }
            "Error" -> {
                val s = signal as SignalError
            }
        }
    }
}