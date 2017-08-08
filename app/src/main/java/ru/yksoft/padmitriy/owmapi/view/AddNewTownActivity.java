package ru.yksoft.padmitriy.owmapi.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.yksoft.padmitriy.owmapi.MyApplication;
import ru.yksoft.padmitriy.owmapi.R;
import ru.yksoft.padmitriy.owmapi.model.Api;
import ru.yksoft.padmitriy.owmapi.model.RectangTownListResponse;
import ru.yksoft.padmitriy.owmapi.model.Utils;

import static ru.yksoft.padmitriy.owmapi.model.Constants.API_KEY;

/**
 * Created by padmitriy on 08.08.17.
 * <p>
 * Добавляем новый город
 */

public class AddNewTownActivity extends AppCompatActivity {

    private Api api;
    private EditText townNameInput;
    private Button townNameButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final String TAG = "AddTown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_town);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        api = MyApplication.retrofitOwm.create(Api.class);

        townNameInput = (EditText) findViewById(R.id.town_name_input);
        townNameButton = (Button) findViewById(R.id.town_name_button);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);

        townNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String townName = townNameInput.getText().toString();
                if (TextUtils.isEmpty(townName)) {
                    townNameInput.setError("Введите корректный город");
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                    Call<RectangTownListResponse.TList> callTownList = api.getTownListFromName(townName, API_KEY);
                    callTownList.enqueue(new Callback<RectangTownListResponse.TList>() {
                        @Override
                        public void onResponse(Call<RectangTownListResponse.TList> call, Response<RectangTownListResponse.TList> response) {
                            Log.i(TAG, "onResponse: " + response.body() + " " + townName);
                            if (response.body() != null) {

                                if (response.body() != null) {
                                    MyApplication.townGlobalList.add(0, response.body());
                                    Utils.saveListSharedpref(AddNewTownActivity.this, MyApplication.townGlobalList);
                                    swipeRefreshLayout.setRefreshing(false);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RectangTownListResponse.TList> call, Throwable t) {
                            townNameInput.setError("ошибка, попробуйте другое название");
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
