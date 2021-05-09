import java.io.Closeable

abstract class AsyncStream(protected val closeable: Closeable){
    private var stop = false
    protected val stopped: Boolean
        get() = stop

    fun stop(){
        stop = true
        closeable.close()
    }
}