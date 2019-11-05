package cu.uci.fiai.uciencia.pojo;

import java.io.Serializable;

/**
 * Created by Tyto on 10/9/2018.
 */

public class POIMap implements Serializable {

    public static final String TAG = "P01M4P";

    static final String TABLE_NAME = "mapa";

    static final String FIELD_ID = "id_lugar";
    static final String FIELD_NAME = "nombre_lugar";
    static final String FIELD_LATITUDE = "latitud";
    static final String FIELD_LONGITUDE = "longitud";
    static final String FIELD_UCI_PLACE = "uci_lugar";
    static final String FIELD_ICON = "icono_lugar";
    static final String FIELD_PICTURE_1 = "foto1_lugar";
    static final String FIELD_PICTURE_2 = "foto2_lugar";
    static final String FIELD_PICTURE_3 = "foto3_lugar";

    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private boolean uci;
    private String icon;
    private String picture1;
    private String picture2;
    private String picture3;
    private int resID;
    private int resIDPict1;
    private int resIDPict2;
    private int resIDPict3;

    public POIMap() {
        this(DatabaseHelper.INVALID_ID, "", 0, 0, true, "", "", "", "");
    }

    public POIMap(long id, String name, double latitude,
                  double longitude, boolean uci, String icon, String picture1,
                  String picture2, String picture3) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uci = uci;
        this.icon = icon;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.resID = DatabaseHelper.INVALID_ID;
        this.resIDPict1 = DatabaseHelper.INVALID_ID;
        this.resIDPict2 = DatabaseHelper.INVALID_ID;
        this.resIDPict3 = DatabaseHelper.INVALID_ID;
    }

    public POIMap(long id, String name, double latitude,
                  double longitude, String uci, String icon, String picture1,
                  String picture2, String picture3) {
        this(id, name, latitude, longitude, Boolean.parseBoolean(uci),
                icon, picture1, picture2, picture3);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isUci() {
        return uci;
    }

    public String getUci() {
        return String.valueOf(uci);
    }

    public void setUci(boolean uci) {
        this.uci = uci;
    }

    public void setUci(String uci) {
        setUci(Boolean.parseBoolean(uci));
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getResIDPict1() {
        return resIDPict1;
    }

    public void setResIDPict1(int resIDPict1) {
        this.resIDPict1 = resIDPict1;
    }

    public int getResIDPict2() {
        return resIDPict2;
    }

    public void setResIDPict2(int resIDPict2) {
        this.resIDPict2 = resIDPict2;
    }

    public int getResIDPict3() {
        return resIDPict3;
    }

    public void setResIDPict3(int resIDPict3) {
        this.resIDPict3 = resIDPict3;
    }

    @Override
    public String toString() {
        return "POIMap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", uci='" + uci + '\'' +
                ", icon='" + icon + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                '}';
    }

}
