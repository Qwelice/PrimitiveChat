import signals.Signal

interface SignalAdapter {
    fun inputSignal(signal: Signal, comm: SocketIO)
}