package com.example.fellow_traveller.PlacesAPI.Models;

public class PlaceApiLatLonModel {

    private ResultModel result;

    public ResultModel getResult() {
        return result;
    }

    public void setResult(ResultModel result) {
        this.result = result;
    }

    public Float getLatitude(){
        return result.getGeometry().getLocation().getLatitude();
    }

    public Float getLongitude(){
        return result.getGeometry().getLocation().getLongitude();
    }


    public String getPlaceId(){
        return result.getPlaceΙd();
    }

    public String getFormattedAddress(){
        return result.getFormattedAddress();
    }


}












