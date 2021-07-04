package com.example.practicalexam.model;

import io.realm.RealmObject;

public class CountriesModel extends RealmObject {


    String id;
    String CountryName;
    String Capital;
    String Region;
    String Abbv;
    String CallingCodes;
    String Population;
    String Currencies;
    String Lnglat;
    String Languages;
    String Border;
    String Flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getAbbv() {
        return Abbv;
    }

    public void setAbbv(String abbv) {
        Abbv = abbv;
    }

    public String getCallingCodes() {
        return CallingCodes;
    }

    public void setCallingCodes(String callingCodes) {
        CallingCodes = callingCodes;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }

    public String getCurrencies() {
        return Currencies;
    }

    public void setCurrencies(String currencies) {
        Currencies = currencies;
    }

    public String getLnglat() {
        return Lnglat;
    }

    public void setLnglat(String lnglat) {
        Lnglat = lnglat;
    }

    public String getLanguages() {
        return Languages;
    }

    public void setLanguages(String languages) {
        Languages = languages;
    }

    public String getBorder() {
        return Border;
    }

    public void setBorder(String border) {
        Border = border;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

}
