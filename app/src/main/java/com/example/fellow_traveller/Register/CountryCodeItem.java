package com.example.fellow_traveller.Register;

public class CountryCodeItem {
    private String codeNumber;
    private int countryImage;

    public CountryCodeItem(String codeNumber, int countryImage) {
        this.codeNumber = codeNumber;
        this.countryImage = countryImage;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCountryImage() {
        return countryImage;
    }

    public void setCountryImage(int countryImage) {
        this.countryImage = countryImage;
    }
}
