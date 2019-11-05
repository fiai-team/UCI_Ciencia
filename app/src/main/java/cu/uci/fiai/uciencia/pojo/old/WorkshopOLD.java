package cu.uci.fiai.uciencia.pojo.old;

import android.content.Context;

import java.io.Serializable;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.pojo.DatabaseHelper;
import cu.uci.fiai.uciencia.pojo.POIMap;

/**
 * Created by Tyto on 16/9/2018.
 */

public class WorkshopOLD implements Serializable {

    public static final String TAG = "W0rksh0p";

    public  static final String TABLE_NAME = "workshopsOLD";

    public  static final String FIELD_ID = "id_workshop";
    public  static final String FIELD_NAME = "name_workshop";
    public  static final String FIELD_SEAT = "seat_workshop";
    public  static final String FIELD_COORDINATOR = "coordinator_workshop";
    public static final String FIELD_MAP = "map_workshop";

    private long id;
    private String name;
    private String seat;
    private String coordinator;
    private long map;
    private POIMap poiMap;

    public WorkshopOLD(long id, String name, String seat, String coordinator, long map) {
        this.id = id;
        this.name = name;
        this.seat = seat;
        this.coordinator = coordinator;
        this.map = map;
    }

    public WorkshopOLD(String name, String seat, String coordinator, long map) {
        this(DatabaseHelper.INVALID_ID, name, seat, coordinator, map);
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

    public String getName(Context context) {
        switch ((int)id) {
            case 1:
                return context.getString(R.string.workshop_1);
            case 2:
                return context.getString(R.string.workshop_2);
            case 3:
                return context.getString(R.string.workshop_3);
            case 4:
                return context.getString(R.string.workshop_4);
            case 5:
                return context.getString(R.string.workshop_5);
            case 6:
                return context.getString(R.string.workshop_6);
            case 7:
                return context.getString(R.string.workshop_7);
            case 8:
                return context.getString(R.string.workshop_8);
            case 9:
                return context.getString(R.string.workshop_9);
            default:
                return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public String getSeat(Context context) {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public String getCoordinator(Context context) {
        return context.getString(R.string.string_cordinator) +
                getCoordinator();
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public long getMap() {
        return map;
    }

    public void setMap(long map) {
        this.map = map;
    }

    public POIMap getPoiMap() {
        return poiMap;
    }

    public void setPoiMap(POIMap poiMap) {
        this.poiMap = poiMap;
    }

}
