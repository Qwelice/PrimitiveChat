import signals.*
import signals.exceptions.ServerIsFullException
import signals.exceptions.SuchUserExistException
import java.net.ServerSocket
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

class Server(private val maxConnections: Int = 10, private val port: Int = 5803) : SignalAdapter{
    private var stop = false
    private val sSocket = ServerSocket(this.port)
    private val connected = ConcurrentHashMap<SocketIO, String>()

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
                        sio.sendData(SignalError(ServerIsFullException()))
                        sio.stop()
                        cSocket.close()
                        println("New connection has been closed. Reason: server is full")
                        continue
                    }
                    connected[sio] = ""
                } catch (ex: InterruptedException){
                    ex.printStackTrace()
                }
            }
        }
    }

    override fun inputSignal(signal: Signal, comm: SocketIO) {
        when(signal.getType()){
            "Connect" -> {
                signalConnect(signal as SignalConnect, comm)
            }
            "Disconnect" -> {
                signalDisconnect(comm)
            }
            "SendMessage" -> {
                signalSendMessage(signal as SignalNewMessage)
            }
        }
    }

    private fun signalConnect(signal: SignalConnect, sio: SocketIO){
        if (!connected.values.contains(signal.username)){
            connected[sio] = signal.username
            sio.sendData(SignalSuccess("Welcome, ${signal.username}!"))
            broadcastSignal(SignalNewMessage("Пользователь ${signal.username} вошел в чат!"))
            broadcastSignal(SignalUserList(connected.values.toList()))
        }else{
            sio.sendData(SignalError(SuchUserExistException()))
        }
    }

    private fun signalDisconnect(sio: SocketIO){
        val username = connected[sio]
        sio.sendData(SignalNewMessage("Вы отключились от чата! До свидания!"))
        removeSocketIO(sio)
        broadcastSignal(SignalNewMessage("Пользователь $username покинул чат"))
        broadcastSignal(SignalUserList(connected.values.toList()))
    }

    private fun signalSendMessage(signal: SignalNewMessage){
        broadcastSignal(signal)
    }

    private fun broadcastSignal(signal: Signal){
        for(s in connected.keys){
            s.sendData(signal)
        }
    }

    private fun removeSocketIO(sio: SocketIO){
        if(connected.keys.contains(sio)){
            sio.stop()
            connected.remove(sio)
        }
    }
}