package com.example.cardoso_brach_projectiut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cardoso_brach_projectiut.R;
import com.example.cardoso_brach_projectiut.model.Country;

import java.util.List;

public class AdapterForCountries extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Country> countries;

    public AdapterForCountries(Context context, List<Country> countries) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return this.countries.size();
    }

    @Override
    public Object getItem(int position) {
        return this.countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = this.layoutInflater.inflate(R.layout.country_list_item, parent, false);

        TextView countryName = (TextView) itemView.findViewById(R.id.country_name);

        countryName.setText(this.countries.get(position).getName());

        return itemView;
    }
}
