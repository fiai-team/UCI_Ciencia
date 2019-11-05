package cu.uci.fiai.uciencia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;

import cu.uci.fiai.uciencia.ConferenceActivity;
import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.adapter.ConferenceAdapter;
import cu.uci.fiai.uciencia.pojo.old.Conference;
import cu.uci.fiai.uciencia.util.AppUCI;

//import android.view.GestureDetector;
//import android.view.MotionEvent;

public class ConferencesFragment extends Fragment
        implements /*RecyclerView.OnItemTouchListener,*/ View.OnClickListener {

    private ArrayList<Conference> conferences;
    //private GestureDetector gestureDetector;

    private RecyclerView recyclerView;

    public ConferencesFragment() {
        // Required empty public constructor
    }

    public static ConferencesFragment newInstance() {
        return new ConferencesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.nav_drawer_2);

        conferences = ((AppUCI) getActivity().getApplication()).getConferences();
        //gestureDetector = new GestureDetector(getContext(),
        //        new GestureDetector.SimpleOnGestureListener() {
        //            @Override
        //            public boolean onSingleTapUp(MotionEvent e) {
        //                return true;
        //            }
        //        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conferences, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvConferences);
        final ConferenceAdapter adapter = new ConferenceAdapter(getContext(), conferences);

        adapter.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(adapter);
        //recyclerView.addOnItemTouchListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int position = -1;

        switch (v.getId()) {
            case R.id.aivAuthor:
                position = recyclerView.getChildAdapterPosition(
                        (View) v.getParent().getParent().getParent());
                break;
            case R.id.flConferenceContainer:
                position = recyclerView.getChildAdapterPosition(v);
        }

        if (position > -1 && position < conferences.size()) {
            ActivityOptionsCompat compat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), null);
            Intent intent = new Intent(getContext(), ConferenceActivity.class);
            intent.putExtra(Conference.TAG, conferences.get(position));
            getActivity().startActivity(intent, compat.toBundle());
        }
    }

    //@Override
    //public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
    //    View child = rv.findChildViewUnder(e.getX(), e.getY());
    //
    //    if (child != null && gestureDetector.onTouchEvent(e)) {
    //        ActivityOptionsCompat compat = ActivityOptionsCompat
    //                .makeSceneTransitionAnimation(getActivity(), null);
    //
    //        final int position = rv.getChildAdapterPosition(child);
    //        Intent intent = new Intent(getContext(), ConferenceActivity.class);
    //        intent.putExtra(Conference.TAG, conferences.get(position));
    //        getActivity().startActivity(intent, compat.toBundle());
    //    }
    //
    //    return false;
    //}
    //
    //@Override
    //public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
    //
    //@Override
    //public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

}
