package cu.uci.fiai.uciencia.util.agenda;

import android.content.Context;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.text.ParseException;

import cu.uci.fiai.uciencia.pojo.ScheduleEvent;

/**
 * Created by Tyto on 21/9/2018.
 */

public class WorkshopCalendar extends BaseCalendarEvent {

    public static final String TAG = "UC13NC14W0RKSH0P";

    private Context context;
    private ScheduleEvent event;
    private int colorLight;

    public WorkshopCalendar(long id, int color, String title, String description,
                            String location, long dateStart, long dateEnd,
                            int allDay, String duration, int colorLight) {
        super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);
        this.colorLight = colorLight;
    }

    public WorkshopCalendar(WorkshopCalendar calendarEvent) {
        super(calendarEvent);
        setId(calendarEvent.getId());

        this.event = calendarEvent.getEvent();
        this.context = calendarEvent.getContext();
        this.colorLight = calendarEvent.getColorLight();
    }

    public WorkshopCalendar(Context context, ScheduleEvent event, int color,
                                 int colorLight) throws ParseException {
        super(event.getTitle(), "", event.getLocation(), color,
                event.getStartCalendar(), event.getEndCalendar(), false);
        setId(event.getId());

        this.event = event;
        this.context = context;
        this.colorLight = colorLight;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
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

    @Override
    public CalendarEvent copy() {
        return new WorkshopCalendar(this);
    }

}
