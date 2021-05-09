package signals

import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.jvm.Throws

abstract class Signal : Serializable{
    companion object{
        @Throws(InvalidSignalException::class)
        fun fromBytesStream(stream: ObjectInputStream) : Signal {
            return try {
                val obj = stream.readObject()
                if(obj !is Signal){
                    throw IOException()
                }
                obj as Signal
            } catch (ex: IOException){
                println("Failed to read signal")
                SignalEmpty()
            }
        }
    }

    fun toBytesStream(stream: ObjectOutputStream){
        try{
            stream.writeObject(this)
        } catch (ex: IOException){
            println("Failed to write signal")
        }
    }

    fun getType() : String{
        if(this.javaClass.simpleName.startsWith("Signal")){
            return this.javaClass.simpleName.substringAfter("Signal")
        }
        return this.javaClass.simpleName
    }
}