package ru.yksoft.padmitriy.owmapi.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;

import ru.yksoft.padmitriy.owmapi.MyApplication;

/**
 * Created by padmitriy on 08.08.17.
 * <p>
 * сюда вынесли работу с sharedpreferences и другие утилиты
 */

public class Utils {

    public static void saveListSharedpref(Context context, List<RectangTownListResponse.TList> townList) {
        Gson gson = new Gson();
        String json = gson.toJson(townList);
        SharedPreferences sharedPref = context.getSharedPreferences("42", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("listTown", json);
        editor.apply();
    }

    public static List<RectangTownListResponse.TList> retrieveListSharedpref(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences("42", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("listTown", "");

        Type type = new TypeToken<List<RectangTownListResponse.TList>>() {
        }.getType();
        MyApplication.townGlobalList = gson.fromJson(jsonPreferences, type);
        Toast.makeText(context, "list updated from SPref", Toast.LENGTH_SHORT).show();
        return MyApplication.townGlobalList;
    }

    public static String degreesToDirections(Double x) {
        String directions[] = {"Северный", "Северо-восточный", "Восточный", "Юго-восточный",
                "Южный", "Юго-западный", "Западный", "Северо-западный", "Северный"};
        if (x == null) {
            return "нет данных";
        }
        return directions[(int) Math.round((((double) x % 360) / 45))];
    }
}
