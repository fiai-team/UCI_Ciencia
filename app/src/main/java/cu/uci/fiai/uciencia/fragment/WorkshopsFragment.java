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

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.WorkshopActivity;
import cu.uci.fiai.uciencia.adapter.WorkshopAdapter;
import cu.uci.fiai.uciencia.pojo.Workshop;
import cu.uci.fiai.uciencia.util.AppUCI;

public class WorkshopsFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Workshop> workshops;

    private RecyclerView recyclerView;

    public WorkshopsFragment() {
        // Required empty public constructor
    }

    public static WorkshopsFragment newInstance() {
        return new WorkshopsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.nav_drawer_3));

        workshops = ((AppUCI) getActivity().getApplication()).getWorkshops();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workshops, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvWorkshops);
        final WorkshopAdapter adapter = new WorkshopAdapter(getContext(), workshops);

        adapter.setOnClickListener(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        int position = -1;

        switch (v.getId()) {
            case R.id.flWorkshopContainer:
                position = recyclerView.getChildAdapterPosition(v);
        }

        if (position > -1 && position < workshops.size()) {
            ActivityOptionsCompat compat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), null);
            Intent intent = new Intent(getContext(), WorkshopActivity.class);
            intent.putExtra(Workshop.TAG, workshops.get(position));
            getActivity().startActivity(intent, compat.toBundle());
        }
    }

}
