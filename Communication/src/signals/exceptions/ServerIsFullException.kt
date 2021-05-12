package signals.exceptions

import java.lang.Exception

class ServerIsFullException() : Exception() {
    private var msg: String? = null
    private var cs: Throwable? = null
    override val message: String?
        get() = msg

    override val cause: Throwable?
        get() = cs

    constructor(message: String) : this(){
        msg = message
    }

    constructor(cause: Throwable) : this(){
        cs = cause
    }
}