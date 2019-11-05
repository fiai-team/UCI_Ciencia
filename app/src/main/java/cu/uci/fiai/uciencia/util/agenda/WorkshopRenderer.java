package cu.uci.fiai.uciencia.util.agenda;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;
import com.github.tibolte.agendacalendarview.render.EventRenderer;
import com.joanzapata.iconify.widget.IconTextView;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.util.Utils;

/**
 * Created by Tyto on 21/9/2018.
 */

public class WorkshopRenderer extends EventRenderer<WorkshopCalendar> {

    private Context context;

    public WorkshopRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void render(View view, WorkshopCalendar event) {
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
        LinearLayout locationContainer = (LinearLayout) view
                .findViewById(R.id.llEventLocation);
        LinearLayout timeContainer = (LinearLayout) view
                .findViewById(R.id.llEventTime);
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

        if (event.getEvent().getTimeStart().trim().length() > 0
                && event.getEvent().getTimeEnd().trim().length() > 0) {
            timeContainer.setVisibility(View.VISIBLE);

            String time = Utils.timeFromText(event.getEvent().getTimeStart());

            /*if (event.getEvent().getTimeEnd().trim().length() > 0) {
                time += " - " + Utils.timeFromText(event.getEvent().getTimeEnd());
            }/**/

            txtTime.setText(time);
        } else {
            timeContainer.setVisibility(View.GONE);
        }

        if (event.getTitle().equals(view.getResources().getString(com.github.tibolte.agendacalendarview.R.string.agenda_event_no_events))) {
            txtTitle.setTextColor(view.getResources().getColor(android.R.color.black));
        } else {
            txtTitle.setTextColor(context.getResources()
                    .getColor(event.getColorLight()));
        }

        cardView.setCardBackgroundColor(event.getColor());
        txtLocation.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
        txtTime.setTextColor(view.getResources().getColor(com.github.tibolte.agendacalendarview.R.color.theme_text_icons));
    }

    @Override
    public int getEventLayout() {
        return R.layout.card_workshop_event;
    }

    @Override
    public Class<WorkshopCalendar> getRenderType() {
        return WorkshopCalendar.class;
    }

}
