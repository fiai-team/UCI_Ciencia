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
import cu.uci.fiai.uciencia.pojo.old.Conference;

/**
 * Created by Tyto on 15/9/2018.
 */

public class AuthorDialog extends DialogFragment {

    public static final String TAG = "AuthorDialog";

    private static final String ARG_CONFERENCE = "D14L0GC0NF3R3NC3";

    private Conference conference;

    public AuthorDialog() {}

    public static AuthorDialog newInstance(Conference conference) {
        AuthorDialog dialog = new AuthorDialog();
        Bundle args = new Bundle();

        args.putSerializable(ARG_CONFERENCE, conference);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            conference = (Conference) getArguments()
                    .getSerializable(ARG_CONFERENCE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_author, null);

        ((ImageView) v.findViewById(R.id.ivPhotoView))
                .setImageResource(conference.getResID());
        ((TextView) v.findViewById(R.id.tvNameView))
                .setText(conference.getPeople());
        ((TextView) v.findViewById(R.id.tvOriginView))
                .setText(conference.getOrigin());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

}
