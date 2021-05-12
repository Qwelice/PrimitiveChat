package signals

class SignalUserList(private val list: List<String>) : Signal(){
    val users: String
        get() = list.joinToString(";")
}