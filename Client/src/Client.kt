import signals.*
import java.net.Socket
import java.util.*

class Client(private val host: String, private val port: Int): SignalAdapter{
    private val cSocket: Socket = Socket(host, port)
    private val sio: SocketIO = SocketIO(this,
        cSocket.getInputStream(), cSocket.getOutputStream())
    init {
        println(cSocket.isConnected)
        println(cSocket.inetAddress)
    }

    override fun inputSignal(signal: Signal, comm: SocketIO) {
        when(signal.getType()){
            "Identify" -> {
                val s = signal as SignalIdentify
                println("Write your username")
                sio.sendData(SignalLogin(Scanner(System.`in`).nextLine(), s.id))
            }
            "Success" -> {
                val s = signal as SignalSuccess
                println(s.reason)
            }
            "Error" -> {
                val s = signal as SignalError
                println(s.reason)
            }
        }
    }
}