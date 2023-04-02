package co.harismiftahulhudha.imagecalculator.core.result

sealed class UseCaseResult<out Data> {
    data class Success<out Data>(val data: Data, val message: String = "") : UseCaseResult<Data>()

    inline fun <V> fold(success: (Data, String) -> V): V = when (this) {
        is Success -> success(this.data, this.message)
    }
}