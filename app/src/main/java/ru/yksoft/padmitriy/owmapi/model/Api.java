package ru.yksoft.padmitriy.owmapi.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by padmitriy on 07.08.17.
 * <p>
 * запросы к API здесь
 */

public interface Api {

    /**
     * Cities within a rectangle zone
     * Description:
     * JSON returns the data from cities within the defined rectangle specified by the geographic coordinates.
     * Parameters:
     * bbox bounding box [lon-left,lat-bottom,lon-right,lat-top,zoom]
     * callback javascript functionName
     * cluster use server clustering of points. Possible values ​​are [yes, no]
     * lang language [ru, en ... ]
     * Examples of API calls:
     * http://api.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10
     * b1b15e88fa797225412429c1c50c122a1
     */
    @GET("/data/2.5/box/city")
    Call<RectangTownListResponse> getTownListFromArea(@Query("bbox") String coordinates,
                                                      @Query("appid") String API_KEY);

    /**
     * By city name
     * Description:
     * You can call by city name or city name and country code. API responds with a list of results that match a searching word.
     * There is a possibility to receive a central district of the city/town with its own parameters (geographic coordinates/id/name) in API response. Example
     * API call:
     * api.openweathermap.org/data/2.5/weather?q={city name}
     * api.openweathermap.org/data/2.5/weather?q={city name},{country code}
     * Parameters:
     * q city name and country code divided by comma, use ISO 3166 country codes
     * Examples of API calls:
     * api.openweathermap.org/data/2.5/weather?q=London
     * api.openweathermap.org/data/2.5/weather?q=London,uk
     */
    @GET("/data/2.5/weather")
    Call<RectangTownListResponse.TList> getTownListFromName(@Query("q") String townName,
                                                            @Query("appid") String API_KEY);

}
