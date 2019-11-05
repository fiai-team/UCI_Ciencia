package cu.uci.fiai.uciencia.util.agenda;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;
import com.github.tibolte.agendacalendarview.render.EventRenderer;
import com.joanzapata.iconify.widget.IconTextView;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.dialog.AuthorDialog;
import cu.uci.fiai.uciencia.pojo.old.Conference;
import cu.uci.fiai.uciencia.util.Utils;

/**
 * Created by Tyto on 15/9/2018.
 */

public class UcienciaEventRenderer extends EventRenderer<UcienciaCalendarEvent> {

    private Context context;

    public UcienciaEventRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void render(View view, UcienciaCalendarEvent event) {
        TextView txtTitle = (TextView) view
                .findViewById(R.id.tvEventTitle);
        TextView txtLocation = (TextView) view
                .findViewById(R.id.tvEventLocation);
        IconTextView itvMarcador = (IconTextView) view
                .findViewById(R.id.itvMarcador);
        TextView txtTime = (TextView) view
                .findViewById(R.id.tvEventTime);
        IconTextView itvTimer = (IconTextView) view
                .findViewById(R.id.itvTimer);
        AvatarImageView aivAuthor = (AvatarImageView) view
                .findViewById(R.id.aivAuthor);
        LinearLayout timeContainer = (LinearLayout) view
                .findViewById(R.id.llEventTime);
        LinearLayout locationContainer = (LinearLayout) view
                .findViewById(R.id.llEventLocation);
        CardView cardView = (CardView) view
                .findViewById(R.id.cardEvent);

        cardView.setVisibility(View.VISIBLE);

        itvMarcador.setTextColor(context.getResources()
                .getColor(event.getColorLight()));
        itvTimer.setTextColor(context.getResources()
                .getColor(event.getColorLight()));
        txtTitle.setText(event.getTitle());

        txtLocation.setText(event.getLocation());

        if (event.getLocation().trim().length() > 0) {
            locationContainer.setVisibility(View.VISIBLE);
            txtLocation.setText(event.getLocation());
        } else {
            locationContainer.setVisibility(View.GONE);
        }

        if (event.getTitle().equals(view.getResources().getString(com.github.tibolte.agendacalendarview.R.string.agenda_event_no_events))) {
            txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));
        } else {
            txtTitle.setTextColor(context.getResources()
                    .getColor(event.getColorLight()));
        }

        if (event.getEvent().getStart().trim().length() > 0
                && event.getEvent().getEnd().trim().length() > 0) {
            timeContainer.setVisibility(View.VISIBLE);

            String time = Utils.timeFromText(event.getEvent().getStart());

            if (event.getEvent().getEnd().trim().length() > 0) {
                time += " - " + Utils.timeFromText(event.getEvent().getEnd());
            }

            txtTime.setText(time);
        } else {
            timeContainer.setVisibility(View.GONE);
        }

        if (event.isConference()) {
            final Conference conference = event.getConference();

            aivAuthor.setText(conference.getPeople());

            if (conference.getResID() != 0) {
                aivAuthor.setImageResource(conference.getResID());
                aivAuthor.setState(AvatarImageView.SHOW_IMAGE);
                aivAuthor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuthorDialog.newInstance(conference).show(
                                ((FragmentActivity) context)
                                        .getSupportFragmentManager(),
                                AuthorDialog.TAG);
                    }
                });
            }
        } else {
            aivAuthor.setVisibility(View.GONE);
        }

        cardView.setCardBackgroundColor(event.getColor());
        txtLocation.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
        txtTime.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
    }

    @Override
    public int getEventLayout() {
        return R.layout.card_uciencia_event;
    }

    @Override
    public Class<UcienciaCalendarEvent> getRenderType() {
        return UcienciaCalendarEvent.class;
    }

}
