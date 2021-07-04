package com.example.practicalexam.presenter;

import android.content.Context;
import android.util.Log;

import com.example.practicalexam.fetching.FetchingData;
import com.example.practicalexam.model.CountriesModel;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.practicalexam.globals.GlobalString.isNetworkAvailable;

public class LoadDataInteractor implements LoadDataContract.Interactor{

    private Context context;

    LoadDataInteractor(Context context) {
        this.context = context;
    }



    @Override
    public void getOnlineData(Context context, OnlineDataListener listener) {
        if (!isNetworkAvailable(context)){
            listener.onGetDetailsError("Internet Connection is Required!");
        }
        else {
            FetchingData.getData(context, listener);
        }
    }

    @Override
    public void getOfflineData(OfflineDataListener listener) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CountriesModel> realmResults = realm.where(CountriesModel.class).findAll();
        if (!realmResults.isEmpty()) {
            Log.e("realmResults",realmResults.toString());
            listener.onOfflineDetailSuccess(realmResults);
        } else {
            listener.onOfflineDetailFailure();
        }
    }
}
