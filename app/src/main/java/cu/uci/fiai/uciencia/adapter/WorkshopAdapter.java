package cu.uci.fiai.uciencia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.pojo.Workshop;

/**
 * Created by Tyto on 9/9/2018.
 */

public class WorkshopAdapter
        extends RecyclerView.Adapter<WorkshopAdapter.WorkshopHolder>
        implements View.OnClickListener {

    private final Context context;
    private final ArrayList<Workshop> workshops;
    private View.OnClickListener listener;

    public WorkshopAdapter(Context context, ArrayList<Workshop> workshop) {
        this.context = context;
        this.workshops = workshop;
    }

    @Override
    public WorkshopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(
                R.layout.item_list_workshop, parent, false);

        itemView.setOnClickListener(this);

        return new WorkshopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkshopHolder holder, int position) {
        holder.bindData(workshops.get(position));
    }

    @Override
    public int getItemCount() {
        return workshops.size();
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

    class WorkshopHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        WorkshopHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvWorkshopName);
        }

        void bindData(Workshop workshop) {
            tvName.setText(workshop.getName());
        }

    }

}
