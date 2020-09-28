package gr.fellow.fellow_traveller.domain

data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val plate: String,
    val color: String
) {
    val baseInfo: String
        get() = "$brand $model"

    val fullInfo: String
        get() = "$brand $model | $plate | $color"

}