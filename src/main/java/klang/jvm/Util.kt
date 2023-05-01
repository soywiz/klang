package klang.jvm

fun <E : Enum<E>?> buildOptionsMask(vararg values: E): Int {
    var result = 0
    for (value in values) {
        result = result or (1 shl value!!.ordinal)
    }
    return result
}