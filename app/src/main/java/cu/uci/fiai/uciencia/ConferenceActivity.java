package cu.uci.fiai.uciencia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cu.uci.fiai.uciencia.pojo.old.Conference;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ConferenceActivity extends SwipeBackActivity {

    private Conference conference;

    private CoordinatorLayout content;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView authorImg;
    private TextView tvOrigin;
    private AppBarLayout appBarLayout;
    private TextView tvTitleKeynote;
    private TextView tvKeynote;
    private TextView tvTitleAbs;
    private TextView tvAbstract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_conference);
        changeStatusBarColor();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);

        if (getIntent().hasExtra(Conference.TAG)) {
            conference = (Conference) getIntent().getSerializableExtra(Conference.TAG);

            setupToolbar();
            initViews();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        content = (CoordinatorLayout) findViewById(R.id.clConferenceContent);
        authorImg = (ImageView) findViewById(R.id.ivHeader);
        tvOrigin = (TextView) findViewById(R.id.tvOrigin);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        tvTitleKeynote = (TextView) findViewById(R.id.tvTitleKeynote);
        tvKeynote = (TextView) findViewById(R.id.tvKeynote);
        tvTitleAbs = (TextView) findViewById(R.id.tvTitleAbs);
        tvAbstract = (TextView) findViewById(R.id.tvAbstract);
    }

    private void setupViews() {
        collapsingToolbar.setTitle(conference.getPeople());
        tvOrigin.setText(conference.getOrigin());
        tvTitleKeynote.setText(conference.isSpanish() ? "Conferencia" : "Keynote");
        tvKeynote.setText(conference.getKeynote());
        tvTitleAbs.setText(conference.isSpanish() ? "Resumen" : "Abstract");
        tvAbstract.setText(conference.getAbstract());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                conference.getResID());
        authorImg.setImageBitmap(bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(
                        R.color.colorPrimary);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.md_black_1000_75);
            }
        });
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

}
