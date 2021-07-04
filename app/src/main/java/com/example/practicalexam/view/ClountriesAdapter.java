package com.example.practicalexam.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.practicalexam.R;
import com.example.practicalexam.globals.RecyclerViewClickListener;
import com.example.practicalexam.model.CountriesModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ClountriesAdapter extends RecyclerView.Adapter<ClountriesAdapter.MyViewHolder> {
    RealmResults<CountriesModel> List;
    private Activity activity;


    RecyclerViewClickListener listener;


    public ClountriesAdapter(RealmResults<CountriesModel> List, Activity activity, RecyclerViewClickListener listener) {
        this.List = List;
        this.listener = listener;
        this.activity = activity;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private RecyclerViewClickListener listener;


        @BindView(R.id.txtCountryName)
        TextView countryName;


        @BindView(R.id.imageViewFlag)
        ImageView imgFlag;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition(), false);
        }


        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view, getAdapterPosition(), true);
            return true;
        }

    }

    @Override
    public ClountriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_countries_layout, parent, false);

        return new ClountriesAdapter.MyViewHolder(itemView, listener);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ClountriesAdapter.MyViewHolder holder, int position) {

        CountriesModel countriesModel = List.get(position);


        holder.countryName.setText(countriesModel.getCountryName());
        SvgLoader.pluck()
                .with(activity)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(countriesModel.getFlag(), holder.imgFlag);


    }

    @Override
    public int getItemCount() {
        if (List != null) {
            return List.size();
        }
        return 0;

    }
}