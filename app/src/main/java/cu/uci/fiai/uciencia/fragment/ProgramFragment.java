package cu.uci.fiai.uciencia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.UcienciaEventActivity;
import cu.uci.fiai.uciencia.pojo.old.Event;
import cu.uci.fiai.uciencia.util.AppUCI;
import cu.uci.fiai.uciencia.util.agenda.UcienciaCalendarEvent;
import cu.uci.fiai.uciencia.util.agenda.UcienciaEventRenderer;

public class ProgramFragment extends Fragment implements CalendarPickerController {

    private ArrayList<Event> dayEvents;
    
    private AgendaCalendarView acvSchedule;

    public ProgramFragment() {
        // Required empty public constructor
    }

    public static ProgramFragment newInstance() {
        return new ProgramFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.nav_drawer_4);

        dayEvents = ((AppUCI) getActivity().getApplication()).getEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program, container, false);

        initViews(view);
        fillViews();

        return view;
    }


    @Override
    public void onDaySelected(DayItem dayItem) {}

    @Override
    public void onEventSelected(CalendarEvent calendarEvent) {
        if (calendarEvent instanceof UcienciaCalendarEvent) {
            Event event = ((UcienciaCalendarEvent) calendarEvent).getEvent();

            if (event != null) {
                ActivityOptionsCompat compat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), null);
                Intent intent = new Intent(getContext(),
                        UcienciaEventActivity.class);
                intent.putExtra(Event.TAG, event);
                intent.putExtra(UcienciaCalendarEvent.TAG,
                        calendarEvent.getInstanceDay());
                getActivity().startActivity(intent, compat.toBundle());
            }
        }
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
    }

    private void initViews(View view) {
        acvSchedule = (AgendaCalendarView) view.findViewById(R.id.agenda_calendar_view);
        //tabLayout = (TabLayout) view.findViewById(R.id.tabEvents);
        //viewPager = (ViewPager) view.findViewById(R.id.viewPagerEvents);
    }

    private void fillViews() {
        //viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        //viewPager.addOnPageChangeListener(this);
        //viewPager.setPageTransformer(true, new DepthTransformation());
        //tabLayout.setupWithViewPager(viewPager);
        Calendar minDate = new GregorianCalendar(2018, 8, 20);
        Calendar maxDate = new GregorianCalendar(2018, 8, 27);

        List<CalendarEvent> eventList = new ArrayList<>();

        try {
            for (Event event : dayEvents) {
                eventList.add(new UcienciaCalendarEvent(getContext(), event,
                        getColorEvent(event), getColorEventLight(event)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        acvSchedule.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        acvSchedule.addEventRenderer(new UcienciaEventRenderer(getActivity()));
    }

    private int getColorEvent(Event event){
        switch (Integer.parseInt(event.getDayStart().substring(0, 2))) {
            case 21:
                return ContextCompat.getColor(getContext(),
                        R.color.md_red_900);
            case 23:
                return ContextCompat.getColor(getContext(),
                        R.color.md_deep_purple_900);
            case 24:
                return ContextCompat.getColor(getContext(),
                        R.color.md_green_900);
            case 25:
                return ContextCompat.getColor(getContext(),
                        R.color.md_teal_900);
            case 26:
                return ContextCompat.getColor(getContext(),
                        R.color.md_indigo_900);
            default:
                return ContextCompat.getColor(getContext(),
                        R.color.colorPrimary);
        }
    }

    private int getColorEventLight(Event event){
        switch (Integer.parseInt(event.getDayStart().substring(0, 2))) {
            case 21:
                return R.color.md_red_100;
            case 23:
                return R.color.md_deep_purple_100;
            case 24:
                return R.color.md_green_100;
            case 25:
                return R.color.md_teal_100;
            case 26:
                return R.color.md_indigo_100;
            default:
                return R.color.colorPrimaryLight;
        }
    }

}
