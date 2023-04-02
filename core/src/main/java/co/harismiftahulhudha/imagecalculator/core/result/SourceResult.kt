package co.harismiftahulhudha.imagecalculator.core.result

sealed class SourceResult<out Data> {
    data class Success<out Data>(val data: Data) : SourceResult<Data>()

    inline fun <V> fold(success: (Data) -> V): V = when (this) {
        is Success -> success(this.data)
    }
}