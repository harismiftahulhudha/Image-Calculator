package co.harismiftahulhudha.imagecalculator.home.domain.payloads

data class CreateImageCalculatorPayload(
    val image: String,
    val input: String,
    val result: String
)
