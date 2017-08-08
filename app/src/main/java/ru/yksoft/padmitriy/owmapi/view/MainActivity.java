package ru.yksoft.padmitriy.owmapi.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.yksoft.padmitriy.owmapi.MyApplication;
import ru.yksoft.padmitriy.owmapi.R;
import ru.yksoft.padmitriy.owmapi.model.Api;
import ru.yksoft.padmitriy.owmapi.model.RectangTownListResponse;
import ru.yksoft.padmitriy.owmapi.model.Utils;
import ru.yksoft.padmitriy.owmapi.model.adapter.TownAdapter;

import static ru.yksoft.padmitriy.owmapi.model.Constants.API_KEY;
import static ru.yksoft.padmitriy.owmapi.model.Constants.COORDINATES;

/**
 * Created by padmitriy on 08.08.17.
 * <p>
 * при старте берем города с площади по координатам (хардкод) или из sharedpref если есть
 */

public class MainActivity extends AppCompatActivity implements LocationListener {

    private final String TAG = "TownActivityLog ";
    private Api api;
    private RecyclerView townRecyclerView;
    private TownAdapter townAdapter;
    private LocationManager locationManager;
    private TextView textTownName;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = this;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "дайте вручную все необходимые разрешения", Toast.LENGTH_SHORT).show();
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewTownActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        textTownName = (TextView) findViewById(R.id.text_town_main);
        api = MyApplication.retrofitOwm.create(Api.class);
        townRecyclerView = (RecyclerView) findViewById(R.id.town_recycler_view);
        RecyclerView.LayoutManager townLayoutManager = new LinearLayoutManager(this);
        townRecyclerView.setLayoutManager(townLayoutManager);
        townAdapter = new TownAdapter(this);
        townRecyclerView.setAdapter(townAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                townAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // if 1 time get list
        if (Utils.retrieveListSharedpref(this) == null) {
            getAreaList();
        } else {
            townAdapter.setTownList(Utils.retrieveListSharedpref(this));
            townAdapter.notifyDataSetChanged();
        }
    }

    private Call<RectangTownListResponse> callTownList;

    private void getAreaList() {
        if (MyApplication.townGlobalList == null) {
            swipeRefreshLayout.setRefreshing(true);
            callTownList = api.getTownListFromArea(COORDINATES, API_KEY);
            callTownList.enqueue(new Callback<RectangTownListResponse>() {
                @Override
                public void onResponse(Call<RectangTownListResponse> call, Response<RectangTownListResponse> response) {
                    Log.i(TAG, "onResponse: " + response.body());
                    if (response.body() != null) {
                        if (response.body().getList() != null) {
                            MyApplication.townGlobalList = response.body().getList();
//                            MyApplication.townGlobalList = townList;
                            if (!MyApplication.townGlobalList.isEmpty()) {
                                townAdapter.setTownList(MyApplication.townGlobalList);
                                townAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                                Snackbar.make(getWindow().getDecorView(), "downloaded", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<RectangTownListResponse> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t);
                    Snackbar.make(getWindow().getDecorView(), "unknown server error", Snackbar.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            townAdapter.setTownList(MyApplication.townGlobalList);
            townAdapter.notifyDataSetChanged();
            Snackbar.make(getWindow().getDecorView(), "loaded from memory", Snackbar.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //не обновляю адаптер, чтобы обновлять через swipe refresh
        if (townAdapter.getTownList() == null) {
            townAdapter.setTownList(Utils.retrieveListSharedpref(this));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (townAdapter.getTownList() != null) {
            MyApplication.townGlobalList = townAdapter.getTownList();
            Utils.saveListSharedpref(this, townAdapter.getTownList());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String longitude = "Longitude: " + location.getLongitude();
        Log.i(TAG, longitude);
        String latitude = "Latitude: " + location.getLatitude();
        Log.i(TAG, latitude);

        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cName = " Мой город: " + cityName;
        this.setTitle(cityName);
        textTownName.setText(cName);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        textTownName.setText("Ищем координаты");
    }

    @Override
    public void onProviderDisabled(String s) {
        textTownName.setText("Включите GPS, чтобы найти ваш город");
    }
}
