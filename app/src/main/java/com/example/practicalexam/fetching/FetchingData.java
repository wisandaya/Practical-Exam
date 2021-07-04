package com.example.practicalexam.fetching;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.practicalexam.database.Database;
import com.example.practicalexam.globals.CustomTrust;
import com.example.practicalexam.model.CountriesModel;
import com.example.practicalexam.presenter.LoadDataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;

import static com.example.practicalexam.globals.GlobalString.Borders;
import static com.example.practicalexam.globals.GlobalString.CallingCode;
import static com.example.practicalexam.globals.GlobalString.CountryName;
import static com.example.practicalexam.globals.GlobalString.Currencies;
import static com.example.practicalexam.globals.GlobalString.DATA_URL;
import static com.example.practicalexam.globals.GlobalString.Flag;
import static com.example.practicalexam.globals.GlobalString.Languages;
import static com.example.practicalexam.globals.GlobalString.LngLat;
import static com.example.practicalexam.globals.GlobalString.Population;
import static com.example.practicalexam.globals.GlobalString.Region;
import static com.example.practicalexam.globals.GlobalString.generateRandomHexToken;

public class FetchingData {

    public static void getData(Context context, LoadDataContract.Interactor.OnlineDataListener listener) {
        Realm realm = Realm.getDefaultInstance();

        CustomTrust customTrust = new CustomTrust(context);
        OkHttpClient client2 = customTrust.getClient();
        AndroidNetworking.get(DATA_URL)
                .setPriority(Priority.HIGH)
                .setOkHttpClient(client2)
                .build()
                .setDownloadProgressListener((bytesDownloaded, totalBytes) -> {

                })
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", String.valueOf(response));
                        if (response != null) {

                            realm.beginTransaction();
                            RealmResults<CountriesModel> countriesModels = realm.where(CountriesModel.class).findAll();
                            if (countriesModels != null) {
                                countriesModels.deleteAllFromRealm();
                                realm.commitTransaction();
                            }
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ArrayList<String> listCurrencies = new ArrayList<String>();
                                    ArrayList<String> listLanguages = new ArrayList<String>();
                                    JSONArray jsonArrayCurrencies = jsonObject.getJSONArray(Currencies);
                                    JSONArray jsonArrayLanguages = jsonObject.getJSONArray(Languages);
                                    String id = generateRandomHexToken(16);
                                    String countryName = jsonObject.getString(CountryName);
                                    String region = jsonObject.getString(Region);
                                    String callingCode = jsonObject.getString(CallingCode);
                                    String population = jsonObject.getString(Population);

                                    for (int i1 = 0; i < jsonArrayCurrencies.length(); i++) {
                                        JSONObject jsonObjectCurrencies = jsonArrayCurrencies.getJSONObject(i);
                                        listCurrencies.add(jsonObjectCurrencies.getString("code"));
                                        listCurrencies.add(jsonObjectCurrencies.getString("name"));
                                        listCurrencies.add(jsonObjectCurrencies.getString("symbol"));
                                    }

                                    String currencies = String.valueOf(listCurrencies);
                                    Log.d("Currencies", currencies);
                                    String lngLat = jsonObject.getString(LngLat);
                                    for (int i2 = 0; i < jsonArrayLanguages.length(); i++) {
                                        JSONObject jsonObjectLanguages = jsonArrayLanguages.getJSONObject(i);
                                        listLanguages.add(jsonObjectLanguages.getString("name"));
                                    }

                                    String languages = String.valueOf(listLanguages);
                                    Log.d("Languages", languages);
                                    String borders = jsonObject.getString(Borders);
                                    String flag = jsonObject.getString(Flag);
                                    Database.SaveData(id, region, countryName, "", callingCode, population, currencies, lngLat, languages, borders, flag);
                                    RealmResults<CountriesModel> realmResults = realm.where(CountriesModel.class).findAll();
                                    if (!realmResults.isEmpty()) {
                                        Log.e("realmResults", realmResults.toString());
                                        listener.onGetDetailsSuccess(realmResults);
                                    }

                                    Log.d("country", countryName);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onGetDetailsError("JsonException Error");
                            }
                        } else {
                            listener.onGetDetailsError("Error in Fetching Data");
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onGetDetailsError("Fetching error" + anError.getErrorDetail());
//                        Log.d("error", anError.getErrorBody());
                        Log.d("error", anError.getErrorDetail());
                        Log.d("error", String.valueOf(anError.getErrorCode()));
                        Log.d("error", String.valueOf(anError.getResponse()));
                        Log.d("error", String.valueOf(anError.getMessage()));
                    }
                });

    }
}
