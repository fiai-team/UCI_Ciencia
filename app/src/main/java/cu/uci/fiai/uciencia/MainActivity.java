package cu.uci.fiai.uciencia;

import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;

import cu.uci.fiai.uciencia.fragment.AboutFragment;
import cu.uci.fiai.uciencia.fragment.ConferencesFragment;
import cu.uci.fiai.uciencia.fragment.MainFragment;
import cu.uci.fiai.uciencia.fragment.MapFragment;
import cu.uci.fiai.uciencia.fragment.MenuFragment;
import cu.uci.fiai.uciencia.fragment.ProgramFragment;
import cu.uci.fiai.uciencia.fragment.WorkshopsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int POS_UCIENCIA = 0;
    private static final int POS_CONFERENCES = 1;
    private static final int POS_WORKSHOPS = 2;
    private static final int POS_SHEDULE = 3;
    private static final int POS_MAP = 4;
    private static final int POS_ABOUT = 5;

    private ArrayList<Integer> commits;
    private FragmentManager fragmentManager;

    private FlowingDrawer drawer;
    private MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupMenu();

        commits = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null){
            setFragment(R.id.nav1);
            commits.add(R.id.nav1);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();

        setFragment(item.getItemId());
        drawer.closeMenu();

        if (commits.size() > 0) {
            if (id != commits.get(commits.size() - 1)) {
                commits.add(id);
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isMenuVisible()) {
            drawer.closeMenu();
        } else {
            int sizeCommits = commits.size();

            if (sizeCommits > 1) {
                setFragment(commits.get(sizeCommits - 2));
                updateMenuDrawer(commits.get(sizeCommits - 2));
                commits.remove(sizeCommits - 1);
            } else  {
                super.onBackPressed();
                finishAffinity();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        setupToolbar();
        updateMenuDrawer(commits.get(commits.size() - 1));
        setFragment(commits.get(commits.size() - 1));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (FlowingDrawer) findViewById(R.id.drawerLayout);

        drawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.toggleMenu();
            }
        });
    }

    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        menuFragment = (MenuFragment)
                fm.findFragmentById(R.id.containerMenu);

        if (menuFragment == null) {
            menuFragment = MenuFragment.newInstance();
            fm.beginTransaction().add(R.id.containerMenu, menuFragment).commit();
        }
    }

    private void setFragment(@IdRes final int id) {
        switch (id) {
            case R.id.nav1:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                MainFragment.newInstance())
                        .commit();
                break;
            case R.id.nav2:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                ConferencesFragment.newInstance())
                        .commit();
                break;
            case R.id.nav3:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                WorkshopsFragment.newInstance())
                        .commit();
                break;
            case R.id.nav4:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                ProgramFragment.newInstance())
                        .commit();
                break;
            case R.id.nav5:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                MapFragment.newInstance())
                        .commit();
                break;
            case R.id.nav6:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                AboutFragment.newInstance())
                        .commit();
                break;
            case R.id.navExit:
                finishAffinity();
                break;
        }
    }

    private void updateMenuDrawer(@IdRes int n) {
        switch (n){
            case R.id.nav1:
                menuFragment.selectItem(POS_UCIENCIA);
                break;
            case R.id.nav2:
                menuFragment.selectItem(POS_CONFERENCES);
                break;
            case R.id.nav3:
                menuFragment.selectItem(POS_WORKSHOPS);
                break;
            case R.id.nav4:
                menuFragment.selectItem(POS_SHEDULE);
                break;
            case R.id.nav5:
                menuFragment.selectItem(POS_MAP);
                break;
            case R.id.nav6:
                menuFragment.selectItem(POS_ABOUT);
                break;
        }
    }

}
