package cu.uci.fiai.uciencia;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

import cu.uci.fiai.uciencia.dialog.ChairmanDialog;
import cu.uci.fiai.uciencia.dialog.MapDialog;
import cu.uci.fiai.uciencia.pojo.POIMap;
import cu.uci.fiai.uciencia.pojo.ScheduleEvent;
import cu.uci.fiai.uciencia.pojo.Workshop;
import cu.uci.fiai.uciencia.pojo.old.Event;
import cu.uci.fiai.uciencia.util.AppUCI;
import cu.uci.fiai.uciencia.util.agenda.WorkshopCalendar;
import cu.uci.fiai.uciencia.util.agenda.WorkshopRenderer;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class WorkshopActivity extends SwipeBackActivity
        implements View.OnClickListener, CalendarPickerController {

    private Workshop workshop;
    private ArrayList<ScheduleEvent> events;
    private boolean appBarExpanded = true;

    private AgendaCalendarView acvSchedule;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView tvTitle;
    private AppBarLayout appBarLayout;
    private TextView tvLocation;
    private TextView tvMetadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);

        if (getIntent().hasExtra(Workshop.TAG)){
            workshop = (Workshop) getIntent().getSerializableExtra(Workshop.TAG);
            events = ((AppUCI) getApplication()).getScheduleEvents(workshop.getId());

            setupToolbar();
            initViews();
            initListeners();
            setupViews();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Slide slideEnter = new Slide(Gravity.TOP);
                slideEnter.setDuration(1000);
                getWindow().setEnterTransition(slideEnter);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabMapWorkshop:
                seeMap();
                break;
            case R.id.fabChairmanWorkshop:
                seeChairman();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent calendarEvent) {
        if (calendarEvent instanceof WorkshopCalendar) {
            ScheduleEvent event = ((WorkshopCalendar) calendarEvent).getEvent();

            if (event != null) {
                ActivityOptionsCompat compat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, null);
                Intent intent = new Intent(this, UcienciaEventActivity.class);
                intent.putExtra(ScheduleEvent.TAG, event);
                /*intent.putExtra(UcienciaCalendarEvent.TAG,
                        calendarEvent.getInstanceDay());*/
                //startActivity(intent, compat.toBundle());
            }
        }
    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initViews() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        acvSchedule = (AgendaCalendarView) findViewById(R.id.agenda_calendar_view);
        tvTitle = (TextView) findViewById(R.id.tvNameWorkshop);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
    }

    private void initListeners() {
        findViewById(R.id.fabMapWorkshop).setOnClickListener(this);
        findViewById(R.id.fabChairmanWorkshop).setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //Log.e("TAG", "onOffsetChanged: verticalOffset: " + verticalOffset);
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 100) {
                    appBarExpanded = false;

                    invalidateOptionsMenu();
                    collapsingToolbar.setTitle(workshop.getName());
                    findViewById(R.id.fabMapWorkshop).setVisibility(View.GONE);
                    findViewById(R.id.fabChairmanWorkshop).setVisibility(View.GONE);
                } else {
                    appBarExpanded = true;

                    invalidateOptionsMenu();
                    collapsingToolbar.setTitle(null);
                    findViewById(R.id.fabMapWorkshop).setVisibility(View.VISIBLE);
                    findViewById(R.id.fabChairmanWorkshop).setVisibility(View.VISIBLE);
                }

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowTitleEnabled(appBarExpanded);
                }
            }
        });
    }

    public void setupViews() {
        tvTitle.setText(workshop.getName());
        tvLocation.setText(workshop.getLocation());
        setupAgenda();
    }

    public void setupAgenda() {
        final Calendar minDate = new GregorianCalendar(2018, 8, 20);
        final Calendar maxDate = new GregorianCalendar(2018, 8, 27);

        List<CalendarEvent> eventList = new ArrayList<>();

        try {
            for (ScheduleEvent event : events) {
                eventList.add(new WorkshopCalendar(this, event,
                        getColorEvent(event), getColorEventLight(event)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        acvSchedule.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        acvSchedule.addEventRenderer(new WorkshopRenderer(this));
    }

    private void seeMap() {
        ArrayList<POIMap> poiMaps = new ArrayList<>();
        poiMaps.add(workshop.getPoi());

        MapDialog.newInstance(poiMaps).show(getSupportFragmentManager(),
                MapDialog.TAG);
    }

    private void seeChairman() {
        ChairmanDialog.newInstance(workshop).show(getSupportFragmentManager(),
                ChairmanDialog.TAG);
    }

    private int getColorEvent(ScheduleEvent event){
        switch (Integer.parseInt(event.getDay().substring(0, 2))) {
            case 21:
                return ContextCompat.getColor(this,
                        R.color.md_red_900);
            case 23:
                return ContextCompat.getColor(this,
                        R.color.md_deep_purple_900);
            case 24:
                return ContextCompat.getColor(this,
                        R.color.md_green_900);
            case 25:
                return ContextCompat.getColor(this,
                        R.color.md_teal_900);
            case 26:
                return ContextCompat.getColor(this,
                        R.color.md_indigo_900);
            default:
                return ContextCompat.getColor(this,
                        R.color.colorPrimary);
        }
    }

    private int getColorEventLight(ScheduleEvent event){
        switch (Integer.parseInt(event.getDay().substring(0, 2))) {
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
