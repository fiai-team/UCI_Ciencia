package cu.uci.fiai.uciencia.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.pojo.Workshop;

/**
 * Created by Tyto on 21/9/2018.
 */

public class ChairmanDialog extends DialogFragment {

    public static final String TAG = "AuthorDialog";

    private static final String ARG_WORKSHOP = "D14L0GW0RKSH0P";

    private Workshop workshop;

    public ChairmanDialog() {}

    public static ChairmanDialog newInstance(Workshop workshop) {
        ChairmanDialog dialog = new ChairmanDialog();
        Bundle args = new Bundle();

        args.putSerializable(ARG_WORKSHOP, workshop);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workshop = (Workshop) getArguments()
                    .getSerializable(ARG_WORKSHOP);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_chairman, null);

        ((TextView) v.findViewById(R.id.tvNameView))
                .setText(workshop.getChairman());
        ((TextView) v.findViewById(R.id.tvOriginView))
                .setText(workshop.getMetadata());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

}
