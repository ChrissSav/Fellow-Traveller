package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.car.CarBase
import gr.fellow.fellow_traveller.domain.car.CarColor
import gr.fellow.fellow_traveller.domain.car.CarInfoBase
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarBaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarColorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse


fun CarInfoResponse.mapToCar(): Car =
    Car(id, brand, model, plate, color.mapToCarColor())

fun CarColorResponse.mapToCarColor(): CarColor =
    CarColor(title, hex)

fun CarBaseResponse.mapToCarBase(): CarBase =
    CarBase(brand, model)

fun CarInfoResponse.mapToCarInfoBase(): CarInfoBase =
    CarInfoBase(brand, model, plate, color.mapToCarColor())