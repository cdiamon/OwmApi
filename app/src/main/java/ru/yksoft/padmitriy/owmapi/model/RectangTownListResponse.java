package ru.yksoft.padmitriy.owmapi.model;

/**
 * Created by padmitriy on 07.08.17.
 * <p>
 * это объект города, погоды
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class RectangTownListResponse implements Serializable {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("calctime")
    @Expose
    private Double calctime;
    @SerializedName("cnt")
    @Expose
    private Long cnt;
    @SerializedName("list")
    @Expose
    private List<TList> list = null;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getCalctime() {
        return calctime;
    }

    public void setCalctime(Double calctime) {
        this.calctime = calctime;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public List<TList> getList() {
        return list;
    }

    public void setList(List<TList> list) {
        this.list = list;
    }


    public class Clouds implements Serializable {

        @SerializedName("all")
        @Expose
        private Long all;

        public Long getAll() {
            return all;
        }

        public void setAll(Long all) {
            this.all = all;
        }

    }

    public class Coord implements Serializable {

        @SerializedName("lon")
        @Expose
        private Double lon;
        @SerializedName("lat")
        @Expose
        private Double lat;

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

    }

    public class TList implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("coord")
        @Expose
        private Coord coord;
        @SerializedName("main")
        @Expose
        private Main main;
        @SerializedName("dt")
        @Expose
        private Long dt;
        @SerializedName("wind")
        @Expose
        private Wind wind;
        @SerializedName("rain")
        @Expose
        private Rain rain;
        @SerializedName("clouds")
        @Expose
        private Clouds clouds;
        @SerializedName("weather")
        @Expose
        private List<Weather> weather = null;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public Long getDt() {
            return dt;
        }

        public void setDt(Long dt) {
            this.dt = dt;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Rain getRain() {
            return rain;
        }

        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

    }

    public class Main implements Serializable {

        @SerializedName("temp")
        @Expose
        private Double temp;
        @SerializedName("temp_min")
        @Expose
        private Double tempMin;
        @SerializedName("temp_max")
        @Expose
        private Double tempMax;
        @SerializedName("pressure")
        @Expose
        private Double pressure;
        @SerializedName("sea_level")
        @Expose
        private Double seaLevel;
        @SerializedName("grnd_level")
        @Expose
        private Double grndLevel;
        @SerializedName("humidity")
        @Expose
        private Double humidity;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getTempMin() {
            return tempMin;
        }

        public void setTempMin(Double tempMin) {
            this.tempMin = tempMin;
        }

        public Double getTempMax() {
            return tempMax;
        }

        public void setTempMax(Double tempMax) {
            this.tempMax = tempMax;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(Double seaLevel) {
            this.seaLevel = seaLevel;
        }

        public Double getGrndLevel() {
            return grndLevel;
        }

        public void setGrndLevel(Double grndLevel) {
            this.grndLevel = grndLevel;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

    }

    public class Rain implements Serializable {

        @SerializedName("3h")
        @Expose
        private Double _3h;

        public Double get3h() {
            return _3h;
        }

        public void set3h(Double _3h) {
            this._3h = _3h;
        }

    }

    public class Weather implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon")
        @Expose
        private String icon;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

    public class Wind implements Serializable {

        @SerializedName("speed")
        @Expose
        private Double speed;
        @SerializedName("deg")
        @Expose
        private Double deg;
        @SerializedName("var_beg")
        @Expose
        private Double varBeg;
        @SerializedName("var_end")
        @Expose
        private Double varEnd;

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public Double getDeg() {
            return deg;
        }

        public void setDeg(Double deg) {
            this.deg = deg;
        }

        public Double getVarBeg() {
            return varBeg;
        }

        public void setVarBeg(Double varBeg) {
            this.varBeg = varBeg;
        }

        public Double getVarEnd() {
            return varEnd;
        }

        public void setVarEnd(Double varEnd) {
            this.varEnd = varEnd;
        }

    }
}