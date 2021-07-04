package com.example.practicalexam.presenter;

import android.content.Context;

import com.example.practicalexam.model.CountriesModel;

import io.realm.RealmResults;

public interface LoadDataContract {

    interface View {
        void showToastMessage(String message);

        void showAlertDialog(String title, String message);

        void hideRefresh();

        void showProgress();

        void showSynSuccess(RealmResults<CountriesModel> realmResults);
    }

    interface Presenter{
        void getData();

        void onRefresh();


        void requestOnlineData();

    }

    interface Interactor {

        interface OfflineDataListener {
            void onOfflineDetailSuccess(RealmResults<CountriesModel> realmResults);

            void onOfflineDetailFailure();
        }

        interface OnlineDataListener {
            void onGetDetailsSuccess(RealmResults<CountriesModel> realmResults);

            void onGetDetailsError(String message);
        }

        void getOnlineData(Context context, OnlineDataListener listener);

        void getOfflineData(OfflineDataListener listener);
    }

}
