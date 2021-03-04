package com.example.cardoso_brach_projectiut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cardoso_brach_projectiut.adapter.AdapterForCountries;
import com.example.cardoso_brach_projectiut.adapter.AdapterForTeams;
import com.example.cardoso_brach_projectiut.model.Country;
import com.example.cardoso_brach_projectiut.model.Team;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private String TAG = "Diogo";

    public static String INPUT_PARAMETER = "input_parameter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        List<Team> teams = new ArrayList<>(0);

        RequestQueue queue = Volley.newRequestQueue(this);

        String input = getIntent().getExtras().getString(INPUT_PARAMETER);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //Log.d(TAG, "Connected to internet");

            String url = "https://www.thesportsdb.com/api/v1/json/1/";
            if (StringUtils.isBlank(input)) {
                Toast toast = Toast.makeText(getApplicationContext(),"Erreur", Toast.LENGTH_LONG);
                toast.show();
                finish();
            } else {
                url += "search_all_teams.php?s=Soccer&c=" + input.replaceAll(" ", ".");
            }

            //Log.d(TAG, "URL is " + url);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                JSONArray listOfTeams = json.getJSONArray("teams");

                                JSONObject jsonItem;
                                Team team;
                                for (int i=0; i<listOfTeams.length(); i++)
                                {
                                    jsonItem = listOfTeams.getJSONObject(i);

                                    team = new Team(jsonItem.getString("strTeam"),
                                            jsonItem.getString("strTeamBadge"), jsonItem.getString("strLeague"));
                                    teams.add(team);
                                }

                                ListView listView = (ListView) findViewById(R.id.listView);
                                AdapterForTeams adapterForTeams = new AdapterForTeams(SecondActivity.this, teams);
                                listView.setAdapter(adapterForTeams);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d(TAG, teams.get(position).getName());
                                    }
                                });
                            } catch (JSONException e) {
                                Log.e(TAG, "Error while parsing games result", e);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}