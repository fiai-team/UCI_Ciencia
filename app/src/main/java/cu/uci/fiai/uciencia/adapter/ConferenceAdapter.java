package cu.uci.fiai.uciencia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;

import java.util.ArrayList;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.pojo.old.Conference;

/**
 * Created by Tyto on 9/9/2018.
 */

public class ConferenceAdapter
        extends RecyclerView.Adapter<ConferenceAdapter.ConferenceHolder>
        implements View.OnClickListener {

    private final Context context;
    private final ArrayList<Conference> conferences;
    private View.OnClickListener listener;

    public ConferenceAdapter(Context context, ArrayList<Conference> conferences) {
        this.context = context;
        this.conferences = conferences;
    }

    @Override
    public ConferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(
                R.layout.item_list_conference, parent, false);

        itemView.setOnClickListener(this);

        return new ConferenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConferenceHolder holder, int position) {
        holder.bindData(conferences.get(position));
    }

    @Override
    public int getItemCount() {
        return conferences.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    class ConferenceHolder extends RecyclerView.ViewHolder {

        AvatarImageView aivAuthor;
        TextView tvKeynote;

        ConferenceHolder(View itemView) {
            super(itemView);

            aivAuthor = (AvatarImageView) itemView.findViewById(R.id.aivAuthor);
            tvKeynote = (TextView) itemView.findViewById(R.id.tvKeynote);
        }

        void bindData(Conference conference) {
            aivAuthor.setText(conference.getPeople());

            if (conference.getResID() != 0) {
                aivAuthor.setImageResource(conference.getResID());
                aivAuthor.setState(AvatarImageView.SHOW_IMAGE);
            }

            tvKeynote.setText(conference.getKeynote());

            aivAuthor.setOnClickListener(listener);
        }

    }

}
