package co.harismiftahulhudha.imagecalculator.core.extension

fun String?.orBlank(): String {
    return this ?: ""
}
fun String?.orZero(): Long {
    return this?.let {
        it.split(".")[0].toLong()
    } ?: 0L
}
fun String?.orZeroInt(): Int {
    return this?.let {
        it.split(".")[0].toInt()
    } ?: 0
}
fun String?.orSuccessCode(): String {
    return this ?: "00"
}
fun String?.orSuccessStatus(): String {
    return this ?: "success"
}
fun Boolean?.orFalse(): Boolean {
    return this ?: false
}
fun Boolean?.orTrue(): Boolean {
    return this ?: true
}
fun Int?.orZero(): Int {
    return this ?: 0
}
fun Long?.orZero(): Long {
    return this ?: 0L
}
fun Double?.orZero(): Double {
    return this ?: 0.0
}
fun Int?.orMinusOne(): Int {
    return this ?: -1
}
fun Long?.orMinusOne(): Long {
    return this ?: -1L
}