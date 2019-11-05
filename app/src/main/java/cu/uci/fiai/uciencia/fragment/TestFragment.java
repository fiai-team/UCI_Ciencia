package cu.uci.fiai.uciencia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;
import com.github.tibolte.agendacalendarview.render.EventRenderer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import cu.uci.fiai.uciencia.R;

public class TestFragment extends Fragment implements CalendarPickerController {

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);

        AgendaCalendarView mAgendaCalendarView = (AgendaCalendarView)
                v.findViewById(R.id.agenda_calendar_view);

        Calendar minDate = new GregorianCalendar(2018, 8, 15);
        Calendar maxDate = new GregorianCalendar(2018, 9, 2);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());

        return v;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Toast.makeText(getContext(), "Selected day: " + dayItem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEventSelected(CalendarEvent calendarEvent) {
        Toast.makeText(getContext(), "Selected event: %s" + calendarEvent,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar()
                    .setTitle(calendar
                            .getDisplayName(Calendar.MONTH, Calendar.LONG,
                                    Locale.getDefault()));
        }
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = new GregorianCalendar(2018, 8, 20);
        Calendar endTime1 = new GregorianCalendar(2018, 8, 26);

        /* id The id of the event.
        color The color of the event.
        title The title of the event.
        description The description of the event.
        location The location of the event.
        dateStart The start date of the event.
        dateEnd The end date of the event.
        allDay Int that can be equal to 0 or 1.
        duration The duration of the event in RFC2445 format */
        BaseCalendarEvent event1 = new BaseCalendarEvent("Evento 1",
                "A wonderful journey!",
                "Iceland",
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark),
                startTime1,
                endTime1, true);

        Calendar startTime2 = new GregorianCalendar(2018, 8, 20);
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = new GregorianCalendar(2018, 8, 27);
        endTime2.add(Calendar.DAY_OF_YEAR, 3);

        BaseCalendarEvent event2 = new BaseCalendarEvent("Evento 2",
                "A beautiful small town",
                "Dalvík",
                ContextCompat.getColor(getContext(), R.color.colorAccent), startTime2, endTime2, true);

        Calendar startTime3 = new GregorianCalendar(2018, 8, 20);
        Calendar endTime3 = new GregorianCalendar(2018, 8, 27);
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);

        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Evento 3", "", "Dalvík",
                ContextCompat.getColor(getContext(), R.color.colorPrimary), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);

        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
    }

    public class DrawableCalendarEvent extends BaseCalendarEvent {

        private int mDrawableId;

        public DrawableCalendarEvent(long id, int color, String title, String description, String location, long dateStart, long dateEnd, int allDay, String duration, int drawableId) {
            super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);
            this.mDrawableId = drawableId;
        }

        public DrawableCalendarEvent(String title, String description, String location, int color, Calendar startTime, Calendar endTime, boolean allDay, int drawableId) {
            super(title, description, location, color, startTime, endTime, allDay);
            this.mDrawableId = drawableId;
        }

        public DrawableCalendarEvent(DrawableCalendarEvent calendarEvent) {
            super(calendarEvent);
            this.mDrawableId = calendarEvent.getDrawableId();
        }

        public int getDrawableId() {
            return mDrawableId;
        }

        public void setDrawableId(int drawableId) {
            this.mDrawableId = drawableId;
        }

        @Override
        public CalendarEvent copy() {
            return new DrawableCalendarEvent(this);
        }
    }

    public class DrawableEventRenderer extends EventRenderer<DrawableCalendarEvent> {
        @Override
        public void render(View view, DrawableCalendarEvent event) {
            ImageView imageView = (ImageView) view.findViewById(R.id.view_agenda_event_image);
            TextView txtTitle = (TextView) view.findViewById(com.github.tibolte.agendacalendarview.R.id.view_agenda_event_title);
            TextView txtLocation = (TextView) view.findViewById(com.github.tibolte.agendacalendarview.R.id.view_agenda_event_location);
            LinearLayout descriptionContainer = (LinearLayout) view.findViewById(com.github.tibolte.agendacalendarview.R.id.view_agenda_event_description_container);
            LinearLayout locationContainer = (LinearLayout) view.findViewById(com.github.tibolte.agendacalendarview.R.id.view_agenda_event_location_container);

            descriptionContainer.setVisibility(View.VISIBLE);

            imageView.setImageDrawable(view.getContext().getResources().getDrawable(event.getDrawableId()));

            txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));

            txtTitle.setText(event.getTitle());
            txtLocation.setText(event.getLocation());
            if (event.getLocation().length() > 0) {
                locationContainer.setVisibility(View.VISIBLE);
                txtLocation.setText(event.getLocation());
            } else {
                locationContainer.setVisibility(View.GONE);
            }

            if (event.getTitle().equals(view.getResources().getString(com.github.tibolte.agendacalendarview.R.string.agenda_event_no_events))) {
                txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));
            } else {
                txtTitle.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
            }
            descriptionContainer.setBackgroundColor(event.getColor());
            txtLocation.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
        }

        @Override
        public int getEventLayout() {
            return R.layout.view_agenda_drawable_event;
        }

        @Override
        public Class<DrawableCalendarEvent> getRenderType() {
            return DrawableCalendarEvent.class;
        }
    }

}
