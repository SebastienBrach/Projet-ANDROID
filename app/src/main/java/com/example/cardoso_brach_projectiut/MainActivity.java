package com.example.cardoso_brach_projectiut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cardoso_brach_projectiut.adapter.AdapterForCountries;
import com.example.cardoso_brach_projectiut.model.Country;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Diogo";

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(configuration);

        this.editText = (EditText) findViewById(R.id.searchCountry);
        ImageView imageView = (ImageView) findViewById(R.id.search);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(SecondActivity.INPUT_PARAMETER, editText.getText().toString());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        List<Country> countries = new ArrayList<>(0);

        RequestQueue queue = Volley.newRequestQueue(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();



        if (networkInfo != null && networkInfo.isConnected()) {
            //Log.d(TAG, "Connected to internet");

            String url = "https://www.thesportsdb.com/api/v1/json/1/all_countries.php";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray listOfCountries = json.getJSONArray("countries");

                        JSONObject jsonItem;
                        Country country;

                        for (int i=0; i<listOfCountries.length(); i++) {
                            jsonItem = listOfCountries.getJSONObject(i);
                            country = new Country(jsonItem.getString("name_en"));
                            countries.add(country);
                        }

                        ListView listView = (ListView) findViewById(R.id.countryList);
                        AdapterForCountries adapterForCountries = new AdapterForCountries(MainActivity.this, countries);
                        listView.setAdapter(adapterForCountries);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Log.d(TAG, countries.get(position).getName());

                                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(SecondActivity.INPUT_PARAMETER, countries.get(position).getName());
                                intent.putExtras(bundle);

                                startActivity(intent);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur de requête", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            queue.add(stringRequest);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Non connecté à internet", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }
}