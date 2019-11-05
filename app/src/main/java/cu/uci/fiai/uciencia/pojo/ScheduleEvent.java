package cu.uci.fiai.uciencia.pojo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import cu.uci.fiai.uciencia.util.Utils;

/**
 * Created by Tyto on 21/9/2018.
 */

public class ScheduleEvent implements Serializable {

    public static final String TAG = "5ch3dul3";
    static final String TABLE_NAME = "schedule";

    static final String FIELD_ID = "id";
    static final String FIELD_DAY = "day";
    static final String FIELD_TIME_START = "time_start";
    static final String FIELD_TIME_END = "time_end";
    static final String FIELD_LOCATION = "location";
    static final String FIELD_TITLE_ES = "title_es";
    static final String FIELD_TITLE_EN = "title_en";
    static final String FIELD_TYPE = "type";
    static final String FIELD_POI = "poi";
    static final String FIELD_WORKSHOP = "workshop";
    static final String FIELD_SPEAKERS = "speakers";

    long id;
    String day;
    String timeStart;
    String timeEnd;
    String location;
    String title_es;
    String title_en;
    String type;
    String poi;
    Workshop workshop;
    String speakers;

    public ScheduleEvent(long id, String day, String timeStart, String timeEnd,
                         String location, String title_es, String title_en,
                         String type, String poi, String speakers) {
        this.id = id;
        this.day = day;
        this.timeStart = timeStart.replace(":", "");
        this.timeEnd = timeStart.replace(":", "");
        this.location = location;
        this.title_es = title_es;
        this.title_en = title_en;
        this.type = type;
        this.poi = poi;
        this.workshop = null;
        this.speakers = speakers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeStart() {
        return timeStart.replace(":", "");
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd.replace(":", "");
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        Locale locale = Locale.getDefault();

        if (locale.toString().contains("es")) {//es_US
            return title_es.trim();
        } else {
            return title_en.trim();
        }
    }

    public String getTitle_es() {
        return title_es;
    }

    public void setTitle_es(String title_es) {
        this.title_es = title_es;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public String getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public Calendar getStartCalendar() throws ParseException {
        return Utils.getCalendar(day.trim(), timeStart.trim(), "dd/MM/yyyy");
    }

    public Calendar getEndCalendar() throws ParseException {
        if (timeEnd.trim().length() > 0) {
            return Utils.getCalendar(day, timeEnd, "dd/MM/yyyy");
        } else {
            return getStartCalendar();
        }
    }

}
