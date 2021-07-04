package com.example.practicalexam.database;

import com.example.practicalexam.model.CountriesModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class Database {

    public static void SaveData(String id,
                                String Region,
                                String CountryName,
                                String Abbv,
                                String CallingCodes,
                                String Population,
                                String Currencies,
                                String Lnglat,
                                String Languages,
                                String Border,
                                String Flag) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            CountriesModel countries = realm1.createObject(CountriesModel.class);
            countries.setId(id);
            countries.setRegion(Region);
            countries.setCountryName(CountryName);
            countries.setAbbv(Abbv);
            countries.setCallingCodes(CallingCodes);
            countries.setPopulation(Population);
            countries.setCurrencies(Currencies);
            countries.setLnglat(Lnglat);
            countries.setLanguages(Languages);
            countries.setBorder(Border);
            countries.setFlag(Flag);

        });

    }
}
