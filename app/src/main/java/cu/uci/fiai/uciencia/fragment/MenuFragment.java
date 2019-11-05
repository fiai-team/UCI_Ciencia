package cu.uci.fiai.uciencia.fragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cu.uci.fiai.uciencia.MainActivity;
import cu.uci.fiai.uciencia.R;

public class MenuFragment extends Fragment {

    private NavigationView navigationView;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener((MainActivity) getActivity());

        return view;
    }

    public void selectItem(int pos) {
        navigationView.getMenu().getItem(pos).setChecked(true);
    }

}
