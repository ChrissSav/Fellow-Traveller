package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.data.models.CarBase
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity


fun CarResponse.mapToCar(): Car =
    Car(id, brand, model, plate, color)


fun CarEntity.mapToCar(): Car =
    Car(id, brand, model, plate, color)


fun Car.mapToCarEntity(): CarEntity =
    CarEntity(id, brand, model, plate, color)


fun CarResponse.mapToBaseCar(): CarBase =
    CarBase(brand, model)