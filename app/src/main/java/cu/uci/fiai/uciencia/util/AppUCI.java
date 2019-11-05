package cu.uci.fiai.uciencia.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import java.util.ArrayList;

import cu.uci.fiai.uciencia.pojo.ScheduleEvent;
import cu.uci.fiai.uciencia.pojo.Workshop;
import cu.uci.fiai.uciencia.pojo.old.Conference;
import cu.uci.fiai.uciencia.pojo.DatabaseHelper;
import cu.uci.fiai.uciencia.pojo.old.Event;
import cu.uci.fiai.uciencia.pojo.POIMap;

/**
 * Created by Tyto on 6/9/2018.
 */

public class AppUCI extends Application {

    private DatabaseHelper dbHelper;

    @Override
    public void onCreate() {
        dbHelper = new DatabaseHelper(this);
        dbHelper.openForWriting();

        super.onCreate();

        Iconify.with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
    }

    @Override
    public void onTerminate() {
        dbHelper.close();
        super.onTerminate();
    }

    public SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    public Conference getConference(long id) {
        return dbHelper.getConference(id);
    }

    public ArrayList<Conference> getConferences() {
        return dbHelper.getAllConferences();
    }

    public ArrayList<POIMap> getPOIsMap() {
        return dbHelper.getAllPOIsMap();
    }

    public POIMap getPOIMap(long id) {
        return dbHelper.getPOIMap(id);
    }

    public ArrayList<Event> getEvents() {
        return dbHelper.getAllEvents();
    }

    public ArrayList<ScheduleEvent> getScheduleEvents() {
        return dbHelper.getAllScheduleEvents();
    }

    public ArrayList<ScheduleEvent> getScheduleEvents(long workshop) {
        return dbHelper.getAllScheduleEvents(workshop);
    }

    public ArrayList<Workshop> getWorkshops() {
        return dbHelper.getAllWorkshops();
    }

}
