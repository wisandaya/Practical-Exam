package com.example.practicalexam.presenter;

import android.content.Context;

import com.example.practicalexam.model.CountriesModel;
import com.example.practicalexam.view.MainActivity;

import io.realm.RealmResults;

public class LoadDataPresenter implements LoadDataContract.Presenter,
LoadDataContract.Interactor.OfflineDataListener, LoadDataContract.Interactor.OnlineDataListener{

    private MainActivity view;
    private LoadDataInteractor interactor;
    private Context context;

    public LoadDataPresenter(MainActivity view, Context context) {
        this.view = view;
        this.interactor = new LoadDataInteractor(context);
        this.context = context;
    }

    @Override
    public void getData() {
        if (view != null) {
            view.showProgress();
        }
        interactor.getOfflineData(this);
    }

    @Override
    public void onRefresh() {
        view.showAlertDialog("Alert", "Fetching Data Online");
    }


    @Override
    public void requestOnlineData() {
        view.showProgress();
        interactor.getOnlineData(context, this);
    }


    @Override
    public void onOfflineDetailSuccess(RealmResults<CountriesModel> realmResults) {
        view.showToastMessage("Load Offline Data Success");
        view.hideRefresh();
        view.showSynSuccess(realmResults);
    }

    @Override
    public void onOfflineDetailFailure() {
        view.showToastMessage("Empty Data, Fetching Data Online");
        interactor.getOnlineData(context, this);
    }

    @Override
    public void onGetDetailsSuccess(RealmResults<CountriesModel> realmResults) {
        view.showToastMessage("Load Online Data Success");
        view.hideRefresh();
        view.showSynSuccess(realmResults);
    }

    @Override
    public void onGetDetailsError(String message) {
        view.showToastMessage(message);
        view.hideRefresh();
    }
}
