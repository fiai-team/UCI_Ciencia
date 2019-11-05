package cu.uci.fiai.uciencia.util.agenda;

import android.content.Context;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.text.ParseException;

import cu.uci.fiai.uciencia.pojo.old.Conference;
import cu.uci.fiai.uciencia.pojo.old.Event;

/**
 * Created by Tyto on 13/9/2018.
 */

public class UcienciaCalendarEvent extends BaseCalendarEvent {

    public static final String TAG = "UC13NC143V3NT";

    private Context context;
    private Event event;
    private int colorLight;

    public UcienciaCalendarEvent(long id, int color, String title,
                                 String description, String location,
                                 long dateStart, long dateEnd, int allDay,
                                 String duration, int colorLight) {
        super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);
        this.colorLight = colorLight;
    }

    public UcienciaCalendarEvent(UcienciaCalendarEvent calendarEvent) {
        super(calendarEvent);
        setId(calendarEvent.getId());

        this.event = calendarEvent.getEvent();
        this.context = calendarEvent.getContext();
        this.colorLight = calendarEvent.getColorLight();
    }

    public UcienciaCalendarEvent(Context context, Event event, int color,
                                 int colorLight) throws ParseException {
        super(event.getTitle(context), event.getDescription(),
                event.getLocation(), color, event.getStartCalendar(),
                event.getEndCalendar(), event.isAllDay());
        setId(event.getId());

        this.event = event;
        this.context = context;
        this.colorLight = colorLight;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getColorLight() {
        return colorLight;
    }

    public void setColorLight(int colorLight) {
        this.colorLight = colorLight;
    }

    public boolean isConference() {
        return event.isConference();
    }

    public Conference getConference() {
        return event.getConferenceOBJ();
    }

    @Override
    public CalendarEvent copy() {
        return new UcienciaCalendarEvent(this);
    }

}
