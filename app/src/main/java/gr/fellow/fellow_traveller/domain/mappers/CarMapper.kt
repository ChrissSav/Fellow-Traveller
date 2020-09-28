package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.data.models.CarBase
import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity


fun CarResponse.mapToCar(): Car {
    return Car(id, brand, model, plate, color)
}

fun CarEntity.mapToCar(): Car {
    return Car(id, brand, model, plate, color)
}

fun Car.mapToCarEntity(): CarEntity {
    return CarEntity(id, brand, model, plate, color)
}


fun CarResponse.toCar() = CarBase(
    brand, model
)