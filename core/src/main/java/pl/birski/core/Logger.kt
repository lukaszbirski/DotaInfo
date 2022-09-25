package pl.birski.core

class Logger(
    private val tag: String,
    private val isDebug: Boolean = true
) {
    fun log(msg: String) {
        if (!isDebug) {
            // production logging
        } else {
            printLog(tag, msg)
        }
    }

    companion object Factory {
        fun buildDebug(className: String) = Logger(
            tag = className,
            isDebug = true,
        )

        fun buildRelease(className: String) = Logger(
            tag = className,
            isDebug = false,
        )
    }

}

private fun printLog(tag: String, msg: String) {
    println("$tag: $msg")
}