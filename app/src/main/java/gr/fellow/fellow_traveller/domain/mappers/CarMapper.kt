package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.car.CarBase
import gr.fellow.fellow_traveller.domain.car.CarInfoBase
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarBaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity


fun CarInfoResponse.mapToCar(): Car =
    Car(id, brand, model, plate, color)


fun CarEntity.mapToCar(): Car =
    Car(id, brand, model, plate, color)


fun Car.mapToCarEntity(): CarEntity =
    CarEntity(id, brand, model, plate, color)


fun CarBaseResponse.mapToCarBase(): CarBase =
    CarBase(brand, model)

fun CarInfoResponse.mapToCarInfoBase(): CarInfoBase =
    CarInfoBase(brand, model, plate, color)