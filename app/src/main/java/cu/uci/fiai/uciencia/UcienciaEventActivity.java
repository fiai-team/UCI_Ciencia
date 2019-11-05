package cu.uci.fiai.uciencia;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cu.uci.fiai.uciencia.dialog.MapDialog;
import cu.uci.fiai.uciencia.pojo.old.Conference;
import cu.uci.fiai.uciencia.pojo.old.Event;
import cu.uci.fiai.uciencia.pojo.POIMap;
import cu.uci.fiai.uciencia.util.AppUCI;
import cu.uci.fiai.uciencia.util.Utils;
import cu.uci.fiai.uciencia.util.agenda.UcienciaCalendarEvent;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class UcienciaEventActivity extends SwipeBackActivity
        implements View.OnClickListener {

    private Event event;
    private Calendar instanceDay;
    private boolean appBarExpanded = true;

    private CoordinatorLayout content;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvLocation;
    private CardView cardAuthor;
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_ucienciaevent);
        changeStatusBarColor();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);

        if (getIntent().hasExtra(Event.TAG) && getIntent()
                .hasExtra(UcienciaCalendarEvent.TAG)) {
            event = (Event) getIntent().getSerializableExtra(Event.TAG);
            instanceDay = (Calendar) getIntent()
                    .getSerializableExtra(UcienciaCalendarEvent.TAG);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!appBarExpanded) {
            getMenuInflater().inflate(R.menu.menu_uciencia_event, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuSeeMap:
                seeMap();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabMapEvent:
                seeMap();
                break;
        }
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        content = (CoordinatorLayout) findViewById(R.id.clEventContent);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        tvTitle = (TextView) findViewById(R.id.tvTitleEvent);
        tvTime = (TextView) findViewById(R.id.tvTimeEvent);
        tvLocation = (TextView) findViewById(R.id.tvLocationEvent);
        cardAuthor = (CardView) findViewById(R.id.cvAuthor);
        ivPhoto = (ImageView) findViewById(R.id.ivPhotoView);
        tvName = (TextView) findViewById(R.id.tvNameView);
        tvOrigin = (TextView) findViewById(R.id.tvOriginView);
    }

    private void initListeners() {
        findViewById(R.id.fabMapEvent).setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //Log.e("TAG", "onOffsetChanged: verticalOffset: " + verticalOffset);
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }

    public void setupViews() {
        collapsingToolbar.setTitle(setupTitle());
        tvTitle.setText(event.getTitle(this));

        final String time = Utils.timeFromText(event.getStart()) + " - " +
                Utils.timeFromText(event.getEnd());

        tvTime.setText(time);
        tvLocation.setText(event.getLocation());

        if (event.isConference()) {
            Conference conference = event.getConferenceOBJ();

            ivPhoto.setImageResource(conference.getResID());
            tvName.setText(conference.getPeople());
            tvOrigin.setText(conference.getOrigin());
        } else {
            cardAuthor.setVisibility(View.GONE);
        }
    }

    private String setupTitle() {
        Locale locale = Locale.getDefault();
        String dayWeek;
        String month;
        int dayMonth = instanceDay.get(Calendar.DAY_OF_MONTH);

        if (locale.toString().contains("es")) {//es_US
            dayWeek = instanceDay.getDisplayName(Calendar.DAY_OF_WEEK,
                    Calendar.LONG, locale);
            month = instanceDay.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                    locale);
            dayWeek = dayWeek.substring(0, 1).toUpperCase() + dayWeek.substring(1);
            month = month.substring(0, 1).toUpperCase() + month.substring(1);

            return dayWeek + " " + dayMonth + " de " + month;
        } else {//en_US
            locale = Locale.UK;

            dayWeek = instanceDay.getDisplayName(Calendar.DAY_OF_WEEK,
                    Calendar.LONG, locale);
            month = instanceDay.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                    locale);
            return dayWeek + ", " + month + " " + dayMonth;
        }
    }

    private void seeMap() {
        ArrayList<Long> mapsID = event.getMaps();
        ArrayList<POIMap> poiMaps = new ArrayList<>();

        for (int i = 0; i < mapsID.size(); i++) {
            POIMap poiMap = ((AppUCI) getApplication()).getPOIMap(mapsID.get(i));

            if (poiMap != null) {
                poiMaps.add(poiMap);
            }
        }

        MapDialog.newInstance(poiMaps).show(getSupportFragmentManager(),
                MapDialog.TAG);
    }

}
