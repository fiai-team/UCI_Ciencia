package cu.uci.fiai.uciencia.pojo.old;

import android.content.Context;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.pojo.DatabaseHelper;
import cu.uci.fiai.uciencia.util.Utils;

/**
 * Created by Tyto on 12/9/2018.
 */

public class Event implements Serializable {

    public static final String TAG = "3V3NT";

    public  static final String TABLE_NAME = "eventsOLD";

    public   static final String FIELD_ID = "id_event";
    public  static final String FIELD_DAY_START = "day_start_event";
    public  static final String FIELD_DAY_END = "day_end_event";
    public  static final String FIELD_START = "start_event";
    public  static final String FIELD_END = "end_event";
    public static final String FIELD_ALL_DAY = "all_day_event";
    public  static final String FIELD_TITLE = "title_event";
    public  static final String FIELD_LOCATION = "location_event";
    public static final String FIELD_DESCRIPTION = "description_event";
    public static final String FIELD_CONFERENCE = "conference_event";
    public static final String FIELD_MAP = "map_event";

    private long id;
    private String dayStart;
    private String dayEnd;
    private String start;
    private String end;
    private boolean allDay;
    private String title;
    private String location;
    private String description;
    private long conference;
    private String map;
    private Conference conferenceOBJ;

    public Event() {
        this("", "", "", "", false, "", "", "", -1, "-1");
    }

    public Event(long id, String dayStart, String dayEnd, String start,
                 String end, boolean allDay, String title, String location,
                 String description, long conference, String map,
                 Conference obj) {
        this.id = id;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
        this.title = title;
        this.location = location;
        this.description = description;
        this.conference = conference;
        this.map = map;
        this.conferenceOBJ = obj;
    }

    public Event(long id, String dayStart, String dayEnd, String start,
                 String end, boolean allDay, String title, String location,
                 String description, long conference, String map) {
        this(id, dayStart, dayEnd, start, end, allDay, title, location,
                description, conference, map, new Conference());
    }

    public Event(long id, String dayStart, String dayEnd, String start,
                 String end, String allDay, String title, String location,
                 String description, long conference, String map) {
        this(id, dayStart, dayEnd, start, end, Boolean.parseBoolean(allDay),
                title, location, description, conference, map);
    }

    public Event(String dayStart,String dayEnd, String start, String end,
                 boolean allDay, String title, String location,
                 String description, long conference, String map,
                 Conference obj) {
        this(DatabaseHelper.INVALID_ID, dayStart, dayEnd, start, end, allDay,
                title, location, description, conference, map, obj);
    }

    public Event(String dayStart,String dayEnd, String start, String end,
                 boolean allDay, String title, String location,
                 String description, long conference, String map) {
        this(DatabaseHelper.INVALID_ID, dayStart, dayEnd, start, end, allDay,
                title, location, description, conference, map);
    }

    public Event(String dayStart,String dayEnd, String start, String end,
                 String allDay, String title, String location,
                 String description, long conference, String map) {
        this(DatabaseHelper.INVALID_ID, dayStart, dayEnd, start, end,
                allDay, title, location, description, conference, map);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public String getAllDay() {
        return String.valueOf(allDay);
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public void setAllDay(String allDay) {
        setAllDay(Boolean.parseBoolean(allDay));
    }

    public String getTitle() {
        return title;
    }

    public String getTitle(Context context) {
        switch ((int) id) {
            case 1:
                return context.getString(R.string.event_01);
            case 2:
                return context.getString(R.string.event_02);
            case 3:
                return context.getString(R.string.event_03);
            case 4:
                return context.getString(R.string.event_04);
            case 5:
                return context.getString(R.string.event_05);
            case 6:
                return context.getString(R.string.event_06);
            case 7:
                return context.getString(R.string.event_07);
            case 8:
                return context.getString(R.string.event_08);
            case 9:
                return context.getString(R.string.event_09);
            case 10:
                return context.getString(R.string.event_10);
            case 11:
                return context.getString(R.string.event_11);
            case 12:
                return context.getString(R.string.event_12);
            case 13:
                return context.getString(R.string.event_13);
            case 14:
                return context.getString(R.string.event_14);
            case 15:
                return context.getString(R.string.event_15);
            case 16:
                return context.getString(R.string.event_16);
            case 17:
                return context.getString(R.string.event_17);
            case 18:
                return context.getString(R.string.event_18);
            case 19:
                return context.getString(R.string.event_19);
            case 20:
                return context.getString(R.string.event_20);
            case 21:
                return context.getString(R.string.event_21);
            case 22:
                return context.getString(R.string.event_22);
            case 23:
                return context.getString(R.string.event_23);
            case 24:
                return context.getString(R.string.event_24);
            case 25:
                return context.getString(R.string.event_25);
            case 27:
                return context.getString(R.string.event_27);
            case 28:
                return context.getString(R.string.event_28);
            case 29:
                return context.getString(R.string.event_29);
            case 30:
                return context.getString(R.string.event_30);
            case 34:
                return context.getString(R.string.event_34);
            case 35:
                return context.getString(R.string.event_35);
            case 36:
                return context.getString(R.string.event_36);
            case 37:
                return context.getString(R.string.event_37);
            case 39:
                return context.getString(R.string.event_39);
            case 40:
                return context.getString(R.string.event_40);
            case 41:
                return context.getString(R.string.event_41);
            case 43:
                return context.getString(R.string.event_43);
            case 44:
                return context.getString(R.string.event_44);
            case 46:
                return context.getString(R.string.event_46);
            case 47:
                return context.getString(R.string.event_47);
            default:
                return context.getString(com.github.tibolte.agendacalendarview.R.string.agenda_event_no_events);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getConference() {
        return conference;
    }

    public void setConference(long conference) {
        this.conference = conference;
    }

    public boolean isConference() {
        return conference != -1;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Conference getConferenceOBJ() {
        return conferenceOBJ;
    }

    public void setConferenceOBJ(Conference obj) {
        this.conferenceOBJ = obj;
    }

    public Calendar getStartCalendar() throws ParseException {
        return Utils.getCalendar(dayStart, start, "dd/MM/yyyy");
    }

    public Calendar getEndCalendar() throws ParseException {
        return Utils.getCalendar(dayEnd, end, "dd/MM/yyyy");
    }

    public ArrayList<Long> getMaps() {
        ArrayList<String> mapStrings = Utils.toArrayListFromString(map, ';');
        ArrayList<Long> mapLongs = new ArrayList<>(mapStrings.size());

        for (String mapSt : mapStrings) {
            mapLongs.add(Long.parseLong(mapSt));
        }

        return mapLongs;
    }

    @Override
    public String toString() {
        return "Event{" +
                "\n  id=" + id +
                "\n  dayStart='" + dayStart + '\'' +
                "\n  dayEnd='" + dayEnd + '\'' +
                "\n  start='" + start + '\'' +
                "\n  end='" + end + '\'' +
                "\n  allDay=" + allDay +
                "\n  title='" + title + '\'' +
                "\n  location='" + location + '\'' +
                "\n  description='" + description + '\'' +
                "\n  conference='" + conference + '\'' +
                "\n  map={" + map + '}' +
                "\n}";
    }

}
