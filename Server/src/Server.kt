import signals.*
import java.net.InetAddress
import java.net.ServerSocket
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

class Server(private val maxConnections: Int = 10, private val port: Int = 5803) : SignalAdapter{
    companion object{
        var count = 0
    }
    private var stop = false
    private val sSocket = ServerSocket(port)
    private val connected = ConcurrentHashMap<Int, SocketIO>()
    private val registered = ConcurrentHashMap<Int, String>()

    init {
        thread {
            while(!stop){
                try {
                    println(sSocket.inetAddress.hostName)
                    println(sSocket.localPort)
                    println("Waiting for new connection...")
                    val cSocket = sSocket.accept()
                    val sio = SocketIO(this, cSocket.getInputStream(), cSocket.getOutputStream())
                    println("New connection is accepted")
                    if(connected.size >= maxConnections){
                        sio.sendData(SignalError("Server is full!\nConnection is closed"))
                        sio.stop()
                        cSocket.close()
                        println("New connection has been closed. Reason: server is full")
                        continue
                    }
                    connected[count] = sio
                    sio.sendData(SignalIdentify(count))
                    count++
                } catch (ex: InterruptedException){
                    ex.printStackTrace()
                }
            }
        }
    }

    override fun inputSignal(signal: Signal, comm: SocketIO) {
        when(signal.getType()){
            "Login" -> {
                login(signal as SignalLogin, comm)
            }
        }
    }

    private fun login(signal: SignalLogin, sio: SocketIO){
        if(signal.id !in connected.keys){
            sio.sendData(SignalError("Not identified access"))
            sio.stop()
        }
        registered[signal.id] = signal.username
        sio.sendData(SignalSuccess("Login is success"))
        println("User: ${signal.username} is registered")
    }
}