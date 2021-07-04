package com.example.practicalexam.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.practicalexam.R;
import com.example.practicalexam.globals.RecyclerViewClickListener;
import com.example.practicalexam.model.CountriesModel;
import com.example.practicalexam.presenter.LoadDataContract;
import com.example.practicalexam.presenter.LoadDataPresenter;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.practicalexam.globals.GlobalString.Borders;
import static com.example.practicalexam.globals.GlobalString.CallingCode;
import static com.example.practicalexam.globals.GlobalString.CountryName;
import static com.example.practicalexam.globals.GlobalString.Currencies;
import static com.example.practicalexam.globals.GlobalString.Flag;
import static com.example.practicalexam.globals.GlobalString.Languages;
import static com.example.practicalexam.globals.GlobalString.LngLat;
import static com.example.practicalexam.globals.GlobalString.Population;
import static com.example.practicalexam.globals.GlobalString.Region;

public class MainActivity extends AppCompatActivity implements LoadDataContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {
    private LoadDataPresenter presenter;

    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;

    SearchView searchView;

    ClountriesAdapter adapter;


    RealmResults<CountriesModel> ListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new LoadDataPresenter(this, this);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        onSearch();
    }

    private void onSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchString) {
                RealmResults<CountriesModel> realmResults;
                Realm realm = Realm.getDefaultInstance();
                if (searchString == null || "".equals(searchString)) {
                    realmResults = realm.where(CountriesModel.class).findAll();
                } else {
                    realmResults = realm.where(CountriesModel.class).equalTo("CountryName", searchString, Case.INSENSITIVE).findAll();
                }
                showSynSuccess(realmResults);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchString) {
                RealmResults<CountriesModel> realmResults;
                Realm realm = Realm.getDefaultInstance();
                if (searchString == null || "".equals(searchString)) {
                    realmResults = realm.where(CountriesModel.class).findAll();
                } else {
                    realmResults = realm.where(CountriesModel.class).equalTo("CountryName", searchString, Case.INSENSITIVE).findAll();
                }
                showSynSuccess(realmResults);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getData();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    presenter.requestOnlineData();
                })
                .setNegativeButton("Cancel ", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                })
                .create()
                .show();
    }

    @Override
    public void hideRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showSynSuccess(RealmResults<CountriesModel> realmResults) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClountriesAdapter(realmResults, this, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        ListItem = realmResults;
    }


    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClicked) {
        CountriesModel countriesModel = ListItem.get(position);
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra(CountryName, countriesModel.getCountryName());
        i.putExtra(Region, countriesModel.getRegion());
        i.putExtra(CallingCode, countriesModel.getCallingCodes());
        i.putExtra(Population, countriesModel.getPopulation());
        i.putExtra(Currencies, countriesModel.getCurrencies());
        i.putExtra(LngLat, countriesModel.getLnglat());
        i.putExtra(Languages, countriesModel.getLanguages());
        i.putExtra(Borders, countriesModel.getBorder());
        i.putExtra(Flag, countriesModel.getFlag());
        this.startActivity(i);
    }
}