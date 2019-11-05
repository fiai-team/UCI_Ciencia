package cu.uci.fiai.uciencia.pojo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import cu.uci.fiai.uciencia.pojo.old.Conference;
import cu.uci.fiai.uciencia.pojo.old.Event;

/**
 * Created by Tyto on 6/9/2018.
 */

public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public static final int INVALID_ID = -1;

    private SQLiteDatabase database;
    private Context context;


    private static final String TABLE_MAP = POIMap.TABLE_NAME;
    private static final String TABLE_SPEAKER = Speaker.TABLE_NAME;
    private static final String TABLE_SCHEDULE = ScheduleEvent.TABLE_NAME;

    private static final String TABLE_CONFERENCES = Conference.TABLE_NAME;
    private static final String TABLE_EVENTS = Event.TABLE_NAME;
    private static final String TABLE_WORKSHOP = Workshop.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public synchronized void close() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null;
        }

        super.close();
    }

    public void openForWriting() {
        database = getWritableDatabase();
    }

    public void openForReading() {
        database = getReadableDatabase();
    }

    private static final String CONF_COLUMN_ID = Conference.FIELD_ID;
    private static final String CONF_COLUMN_KEYNOTE = Conference.FIELD_KEYNOTE;
    private static final String CONF_COLUMN_PEOPLE = Conference.FIELD_PEOPLE;
    private static final String CONF_COLUMN_ORIGIN = Conference.FIELD_ORIGIN;
    private static final String CONF_COLUMN_ABSTRACT = Conference.FIELD_ABSTRACT;
    private static final String CONF_COLUMN_RES = Conference.FIELD_RES;
    private static final String CONF_COLUMN_SPANISH = Conference.FIELD_SPANISH;

    private static final String[] allColumnsConferences = {
            CONF_COLUMN_ID, CONF_COLUMN_KEYNOTE, CONF_COLUMN_PEOPLE,
            CONF_COLUMN_ORIGIN, CONF_COLUMN_ABSTRACT, CONF_COLUMN_RES,
            CONF_COLUMN_SPANISH
    };

    public Conference getConference(long id) {
        if (id == INVALID_ID){
            return new Conference();
        }

        Cursor cursor = database.query(TABLE_CONFERENCES, allColumnsConferences,
                CONF_COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Conference conference = new Conference(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
        conference.setResID(context.getResources().getIdentifier(conference.getRes(),
                "drawable","cu.uci.fiai.uciencia"));

        cursor.close();

        return conference;
    }

    public ArrayList<Conference> getAllConferences() {
        Cursor cursor = database.query(TABLE_CONFERENCES,
                allColumnsConferences, null, null, null, null, null);
        ArrayList<Conference> conferences = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Conference conference = new Conference();

                conference.setId(cursor.getLong(cursor.getColumnIndex(CONF_COLUMN_ID)));
                conference.setKeynote(cursor.getString(cursor
                        .getColumnIndex(CONF_COLUMN_KEYNOTE)));
                conference.setPeople(cursor.getString(cursor
                        .getColumnIndex(CONF_COLUMN_PEOPLE)));
                conference.setOrigin(cursor.getString(cursor
                        .getColumnIndex(CONF_COLUMN_ORIGIN)));
                conference.setAbstract(cursor.getString(cursor
                        .getColumnIndex(CONF_COLUMN_ABSTRACT)));
                conference.setRes(cursor.getString(cursor
                        .getColumnIndex(CONF_COLUMN_RES)));
                conference.setSpanish(cursor.getString(cursor
                        .getColumnIndex(CONF_COLUMN_SPANISH)));
                conference.setResID(context.getResources().getIdentifier(
                        conference.getRes(), "drawable","cu.uci.fiai.uciencia"));

                conferences.add(conference);
            }
        }

        cursor.close();

        return conferences;
    }

    public int updateConference(Conference conference) {
        ContentValues values = new ContentValues();
        values.put(CONF_COLUMN_ID, conference.getId());
        values.put(CONF_COLUMN_KEYNOTE, conference.getKeynote());
        values.put(CONF_COLUMN_PEOPLE, conference.getPeople());
        values.put(CONF_COLUMN_ORIGIN, conference.getOrigin());
        values.put(CONF_COLUMN_ABSTRACT, conference.getAbstract());
        values.put(CONF_COLUMN_RES, conference.getRes());
        values.put(CONF_COLUMN_SPANISH, conference.getSpanish());

        return database.update(TABLE_CONFERENCES, values, CONF_COLUMN_ID + "=?",
                new String[]{String.valueOf(conference.getId())});
    }

    private static final String MAP_COLUMN_ID = POIMap.FIELD_ID;
    private static final String MAP_COLUMN_NAME = POIMap.FIELD_NAME;
    private static final String MAP_COLUMN_LATITUDE = POIMap.FIELD_LATITUDE;
    private static final String MAP_COLUMN_LONGITUDE = POIMap.FIELD_LONGITUDE;
    private static final String MAP_COLUMN_UCI = POIMap.FIELD_UCI_PLACE;
    private static final String MAP_COLUMN_ICON = POIMap.FIELD_ICON;
    private static final String MAP_COLUMN_PICTURE_1 = POIMap.FIELD_PICTURE_1;
    private static final String MAP_COLUMN_PICTURE_2 = POIMap.FIELD_PICTURE_2;
    private static final String MAP_COLUMN_PICTURE_3 = POIMap.FIELD_PICTURE_3;

    private static final String[] allColumnsMap = {
            MAP_COLUMN_ID, MAP_COLUMN_NAME, MAP_COLUMN_LATITUDE,
            MAP_COLUMN_LONGITUDE, MAP_COLUMN_UCI, MAP_COLUMN_ICON,
            MAP_COLUMN_PICTURE_1, MAP_COLUMN_PICTURE_2, MAP_COLUMN_PICTURE_3
    };

    public POIMap getPOIMap(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_MAP, allColumnsMap, MAP_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        POIMap poiMap = new POIMap(cursor.getLong(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8));
        poiMap.setResID(context.getResources().getIdentifier(poiMap.getIcon(),
                "drawable","cu.uci.fiai.uciencia"));
        poiMap.setResIDPict1(context.getResources().getIdentifier(poiMap.getPicture1(),
                "drawable","cu.uci.fiai.uciencia"));
        poiMap.setResIDPict2(context.getResources().getIdentifier(poiMap.getPicture2(),
                "drawable","cu.uci.fiai.uciencia"));
        poiMap.setResIDPict3(context.getResources().getIdentifier(poiMap.getPicture3(),
                "drawable","cu.uci.fiai.uciencia"));

        cursor.close();

        return poiMap;
    }

    public ArrayList<POIMap> getAllPOIsMap() {
        Cursor cursor = database.query(TABLE_MAP, allColumnsMap, null, null,
                null, null, null);
        ArrayList<POIMap> pois = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                POIMap poiMap = new POIMap();

                poiMap.setId(cursor.getLong(cursor.getColumnIndex(MAP_COLUMN_ID)));
                poiMap.setName(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_NAME)));
                poiMap.setLatitude(cursor.getDouble(cursor.getColumnIndex(MAP_COLUMN_LATITUDE)));
                poiMap.setLongitude(cursor.getDouble(cursor.getColumnIndex(MAP_COLUMN_LONGITUDE)));
                poiMap.setUci(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_UCI)));
                poiMap.setIcon(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_ICON)));
                poiMap.setPicture1(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PICTURE_1)));
                poiMap.setPicture2(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PICTURE_2)));
                poiMap.setPicture3(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PICTURE_3)));
                poiMap.setResID(context.getResources().getIdentifier(poiMap.getIcon(),
                        "drawable","cu.uci.fiai.uciencia"));
                poiMap.setResIDPict1(context.getResources().getIdentifier(
                        poiMap.getPicture1().trim(), "drawable","cu.uci.fiai.uciencia"));
                poiMap.setResIDPict2(context.getResources().getIdentifier(
                        poiMap.getPicture2().trim(), "drawable","cu.uci.fiai.uciencia"));
                poiMap.setResIDPict3(context.getResources().getIdentifier(
                        poiMap.getPicture3().trim(), "drawable","cu.uci.fiai.uciencia"));

                pois.add(poiMap);
            }
        }

        cursor.close();

        return pois;
    }

    private static final String EVENT_COLUMN_ID = Event.FIELD_ID;
    private static final String EVENT_COLUMN_DAY_START = Event.FIELD_DAY_START;
    private static final String EVENT_COLUMN_DAY_END = Event.FIELD_DAY_END;
    private static final String EVENT_COLUMN_START = Event.FIELD_START;
    private static final String EVENT_COLUMN_END = Event.FIELD_END;
    private static final String EVENT_COLUMN_ALL_DAY = Event.FIELD_ALL_DAY;
    private static final String EVENT_COLUMN_TITLE = Event.FIELD_TITLE;
    private static final String EVENT_COLUMN_LOCATION = Event.FIELD_LOCATION;
    private static final String EVENT_COLUMN_DESCRIPTION = Event.FIELD_DESCRIPTION;
    private static final String EVENT_COLUMN_CONFERENCE = Event.FIELD_CONFERENCE;
    private static final String EVENT_COLUMN_MAP = Event.FIELD_MAP;

    private static final String[] allColumnsEvent = {
            EVENT_COLUMN_ID, EVENT_COLUMN_DAY_START, EVENT_COLUMN_DAY_END,
            EVENT_COLUMN_START, EVENT_COLUMN_END, EVENT_COLUMN_ALL_DAY,
            EVENT_COLUMN_TITLE, EVENT_COLUMN_LOCATION, EVENT_COLUMN_DESCRIPTION,
            EVENT_COLUMN_CONFERENCE, EVENT_COLUMN_MAP
    };

    public Event getEvent(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_EVENTS, allColumnsEvent,
                EVENT_COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Event event = new Event(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getLong(9),
                cursor.getString(10));
        event.setConferenceOBJ(getConference(event.getConference()));

        cursor.close();

        return event;
    }

    public ArrayList<Event> getAllEvents() {
        Cursor cursor = database.query(TABLE_EVENTS, allColumnsEvent, null, null,
                null, null, EVENT_COLUMN_DAY_START);
        ArrayList<Event> events = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Event event = new Event();

                event.setId(cursor.getLong(cursor
                        .getColumnIndex(EVENT_COLUMN_ID)));
                event.setDayStart(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_DAY_START)));
                event.setDayEnd(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_DAY_END)));
                event.setStart(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_START)));
                event.setEnd(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_END)));
                event.setAllDay(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_ALL_DAY)));
                event.setTitle(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_TITLE)));
                event.setLocation(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_LOCATION)));
                event.setDescription(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_DESCRIPTION)));
                event.setConference(cursor.getLong(cursor
                        .getColumnIndex(EVENT_COLUMN_CONFERENCE)));
                event.setMap(cursor.getString(cursor
                        .getColumnIndex(EVENT_COLUMN_MAP)));
                event.setConferenceOBJ(getConference(event.getConference()));

                events.add(event);
            }
        }

        cursor.close();

        return events;
    }

    /*private static final String WORKSHOP_COLUMN_ID2 = WorkshopOLD.FIELD_ID;
    private static final String WORKSHOP_COLUMN_NAME2 = WorkshopOLD.FIELD_NAME;
    private static final String WORKSHOP_COLUMN_SEAT2 = WorkshopOLD.FIELD_SEAT;
    private static final String WORKSHOP_COLUMN_COORDINATOR2 = WorkshopOLD.FIELD_COORDINATOR;
    private static final String WORKSHOP_COLUMN_MAP2 = WorkshopOLD.FIELD_MAP;

    private static final String[] allColumnsWorkshop2 = {
            WORKSHOP_COLUMN_ID2, WORKSHOP_COLUMN_NAME2, WORKSHOP_COLUMN_SEAT2,
            WORKSHOP_COLUMN_COORDINATOR2, WORKSHOP_COLUMN_MAP2
    };

    public WorkshopOLD getWorkshopOLD(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_WORKSHOP, allColumnsWorkshop2,
                WORKSHOP_COLUMN_ID2 + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        WorkshopOLD workshopOLD = new WorkshopOLD(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getLong(4));
        workshopOLD.setPoiMap(getPOIMap(workshopOLD.getMap()));

        cursor.close();

        return workshopOLD;
    }

    public ArrayList<WorkshopOLD> getAllWorkshopsOLD() {
        Cursor cursor = database.query(TABLE_WORKSHOP, allColumnsWorkshop2, null, null,
                null, null, null);
        ArrayList<WorkshopOLD> workshopOLDs = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                WorkshopOLD workshopOLD = new WorkshopOLD(
                        cursor.getLong(cursor.getColumnIndex(WORKSHOP_COLUMN_ID2)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_COLUMN_NAME2)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_COLUMN_SEAT2)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_COLUMN_COORDINATOR2)),
                        cursor.getLong(cursor.getColumnIndex(WORKSHOP_COLUMN_MAP2)));
                workshopOLD.setPoiMap(getPOIMap(workshopOLD.getMap()));

                workshopOLDs.add(workshopOLD);
            }
        }

        cursor.close();

        return workshopOLDs;
    }/**/

    private static final String SPEAKER_ID = Speaker.FIELD_ID;
    private static final String SPEAKER_NAME = Speaker.FIELD_NAME;
    private static final String SPEAKER_META_ES = Speaker.FIELD_META_ES;
    private static final String SPEAKER_META_EN = Speaker.FIELD_META_EN;

    private static final String[] allColumnsSpeaker = {
            SPEAKER_ID, SPEAKER_NAME, SPEAKER_META_ES,
            SPEAKER_META_EN
    };

    public Speaker getSpeaker(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_SPEAKER, allColumnsSpeaker,
                SPEAKER_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Speaker speaker = new Speaker(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));

        cursor.close();

        return speaker;
    }

    public ArrayList<Speaker> getAllSpeaker() {
        Cursor cursor = database.query(TABLE_SPEAKER, allColumnsSpeaker, null, null,
                null, null, null);
        ArrayList<Speaker> speakers = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Speaker speaker = new Speaker(
                        cursor.getLong(cursor.getColumnIndex(SPEAKER_ID)),
                        cursor.getString(cursor.getColumnIndex(SPEAKER_NAME)),
                        cursor.getString(cursor.getColumnIndex(SPEAKER_META_ES)),
                        cursor.getString(cursor.getColumnIndex(SPEAKER_META_EN)));

                speakers.add(speaker);
            }
        }

        cursor.close();

        return speakers;
    }

    private static final String WORKSHOP_ID = Workshop.FIELD_ID;
    private static final String WORKSHOP_NAME_ES = Workshop.FIELD_NAME_ES;
    private static final String WORKSHOP_NAME_EN = Workshop.FIELD_NAME_EN;
    private static final String WORKSHOP_CHAIRMAN = Workshop.FIELD_CHAIRMAN;
    private static final String WORKSHOP_META_ES = Workshop.FIELD_META_ES;
    private static final String WORKSHOP_META_EN = Workshop.FIELD_META_EN;
    private static final String WORKSHOP_LOCATION = Workshop.FIELD_LOCATION;
    private static final String WORKSHOP_POI = Workshop.FIELD_POI;

    private static final String[] allColumnsWorkshop = {
            WORKSHOP_ID, WORKSHOP_NAME_ES, WORKSHOP_NAME_EN,
            WORKSHOP_CHAIRMAN, WORKSHOP_META_ES, WORKSHOP_META_EN,
            WORKSHOP_LOCATION, WORKSHOP_POI
    };

    public Workshop getWorkshop(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_WORKSHOP, allColumnsWorkshop,
                WORKSHOP_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Workshop workshop = new Workshop(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7));
        workshop.setPoi(getPOIMap(Long.parseLong(cursor.getString(7))));

        cursor.close();

        return workshop;
    }

    public ArrayList<Workshop> getAllWorkshops() {
        Cursor cursor = database.query(TABLE_WORKSHOP, allColumnsWorkshop,
                null, null, null, null, null);
        ArrayList<Workshop> workshops = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Workshop workshop = new Workshop(
                        cursor.getLong(cursor.getColumnIndex(WORKSHOP_ID)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_NAME_ES)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_NAME_EN)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_CHAIRMAN)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_META_ES)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_META_EN)),
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_LOCATION)));
                workshop.setPoi(getPOIMap(Long.parseLong(
                        cursor.getString(cursor.getColumnIndex(WORKSHOP_POI)))));

                workshops.add(workshop);
            }
        }

        cursor.close();

        return workshops;
    }

    private static final String SCHEDULE_ID = ScheduleEvent.FIELD_ID;
    private static final String SCHEDULE_DAY = ScheduleEvent.FIELD_DAY;
    private static final String SCHEDULE_TIME_START = ScheduleEvent.FIELD_TIME_START;
    private static final String SCHEDULE_TIME_END = ScheduleEvent.FIELD_TIME_END;
    private static final String SCHEDULE_LOCATION = ScheduleEvent.FIELD_LOCATION;
    private static final String SCHEDULE_TITLE_ES = ScheduleEvent.FIELD_TITLE_ES;
    private static final String SCHEDULE_TITLE_EN = ScheduleEvent.FIELD_TITLE_EN;
    private static final String SCHEDULE_TYPE = ScheduleEvent.FIELD_TYPE;
    private static final String SCHEDULE_POI = ScheduleEvent.FIELD_POI;
    private static final String SCHEDULE_WORKSHOP = ScheduleEvent.FIELD_WORKSHOP;
    private static final String SCHEDULE_SPEAKER = ScheduleEvent.FIELD_SPEAKERS;

    private static final String[] allColumnsScheduleEvent = {
            SCHEDULE_ID, SCHEDULE_DAY, SCHEDULE_TIME_START,
            SCHEDULE_TIME_END, SCHEDULE_LOCATION, SCHEDULE_TITLE_ES,
            SCHEDULE_TITLE_EN, SCHEDULE_TYPE, SCHEDULE_POI, SCHEDULE_WORKSHOP,
            SCHEDULE_SPEAKER
    };

    public ScheduleEvent getScheduleEvent(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_SCHEDULE, allColumnsScheduleEvent,
                SCHEDULE_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        ScheduleEvent scheduleEvent = new ScheduleEvent(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(10));
        scheduleEvent.setWorkshop(getWorkshop(cursor.getLong(9)));

        cursor.close();

        return scheduleEvent;
    }

    public ArrayList<ScheduleEvent> getAllScheduleEvents() {
        Cursor cursor = database.query(TABLE_SCHEDULE, allColumnsScheduleEvent,
                null, null, null, null, null);
        ArrayList<ScheduleEvent> events = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                ScheduleEvent event = new ScheduleEvent(
                        cursor.getLong(cursor.getColumnIndex(SCHEDULE_ID)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_DAY)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TIME_START)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TIME_END)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TITLE_ES)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TITLE_EN)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TYPE)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_POI)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_SPEAKER)));
                event.setWorkshop(getWorkshop(cursor.getLong(
                        cursor.getColumnIndex(SCHEDULE_WORKSHOP))));

                events.add(event);
            }
        }

        cursor.close();

        return events;
    }

    public ArrayList<ScheduleEvent> getAllScheduleEvents(long workshop) {
        Cursor cursor = database.query(TABLE_SCHEDULE, allColumnsScheduleEvent,
                SCHEDULE_WORKSHOP + "=?", new String[]{String.valueOf(workshop)},
                null, null, null);
        ArrayList<ScheduleEvent> events = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                ScheduleEvent event = new ScheduleEvent(
                        cursor.getLong(cursor.getColumnIndex(SCHEDULE_ID)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_DAY)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TIME_START)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TIME_END)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TITLE_ES)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TITLE_EN)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_TYPE)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_POI)),
                        cursor.getString(cursor.getColumnIndex(SCHEDULE_SPEAKER)));
                event.setWorkshop(getWorkshop(cursor.getLong(
                        cursor.getColumnIndex(SCHEDULE_WORKSHOP))));

                events.add(event);
            }
        }

        cursor.close();

        return events;
    }

}
