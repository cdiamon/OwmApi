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

    public static String degreesToDirections(Float heading)
    {
        String strHeading = "?";
        Hashtable<String, Float> cardinal = new Hashtable<String, Float>();
        cardinal.put("Север_1", new Float(0));
        cardinal.put("Северо-восточный", new Float(45));
        cardinal.put("Восточный", new Float(90));
        cardinal.put("Юго-восточный", new Float(135));
        cardinal.put("Южный", new Float(180));
        cardinal.put("Юго-западный", new Float(225));
        cardinal.put("Западный", new Float(270));
        cardinal.put("Северо-западный", new Float(315));
        cardinal.put("Север_2", new Float(360));

        for (String key: cardinal.keySet())
        {
            Float value = cardinal.get(key);
            if (Math.abs(heading - value) < 30)
            {
                strHeading = key;
                if (key.contains("Север_"))
                {
                    strHeading = "Северный";
                }
                break;
            }
        }
        return strHeading;
    }

}
