package com.example.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SwipeRefreshLayout swipeRefreshLayout;

    public static SharedPreferences sharedpreferences;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<ModelUtils> personUtilsList;

    RequestQueue rq;

    String request_url = "https://mock-api-mobile.dev.lalamove.com/v2/deliveries";
    public void populateRV(List<ModelUtils> PersonUtils)
    {
        this.personUtilsList = PersonUtils;
        mAdapter.notifyDataSetChanged();
    }

    public static boolean check(String id) {
        return sharedpreferences.getString(id, "").equals("fav");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        rq = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();
        mAdapter = new CustomRecyclerAdapter(MainActivity.this, personUtilsList);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        recyclerView.setAdapter(mAdapter);


        sendRequest();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });


    }



    public void sendRequest(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               personUtilsList.clear();

                for(int i = 0; i < response.length(); i++){

                    ModelUtils modelUtils = new ModelUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        JSONObject rout = jsonObject.getJSONObject("route");
                        modelUtils.setStart(rout.getString("start"));
                        modelUtils.setEnd(rout.getString("end"));

                        modelUtils.setImage(jsonObject.getString("goodsPicture"));
                        float deliveryFee =Float.parseFloat(jsonObject.getString("deliveryFee").substring(1));


                        float surcharge =Float.parseFloat(jsonObject.getString("surcharge").substring(1));
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        float price =  (deliveryFee+surcharge);

                        modelUtils.setPrice(String.format(Locale.ENGLISH,"%.02f",price)+"$");

                        modelUtils.setId(jsonObject.getString("id"));
                        if(sharedpreferences.getString(modelUtils.getId(),"")==""|| sharedpreferences.getString(modelUtils.getId(),"").equals("null")){
                            modelUtils.setFavourite(false);
                            System.out.println( modelUtils.isFavourite());
                        }
                        else {
                            modelUtils.setFavourite(true);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    personUtilsList.add(modelUtils);
                    mAdapter.notifyDataSetChanged();


                }


                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error: ", String.valueOf(error));
            }
        });

        rq.add(jsonArrayRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }}


