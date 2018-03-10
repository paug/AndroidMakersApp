package fr.paug.androidmakers.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.paug.androidmakers.R;

/**
 * Created by benju on 09/03/2018.
 */
//TODO Kotlin ?
public class ScheduleDayAdapter extends RecyclerView.Adapter<ScheduleDayAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView sessionTitle;
        public TextView sessionDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            sessionTitle = itemView.findViewById(R.id.sessionTitleTextView);
            sessionDescription = itemView.findViewById(R.id.sessionDescriptionTextView);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
        }
    }

    DaySchedule daySchedule;
    public ScheduleDayAdapter(DaySchedule daySchedule) {
        this.daySchedule = daySchedule;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_session, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = daySchedule.getRoomSchedules().get(0).getItems().get(position);
        holder.sessionTitle.setText(item.getTitle());
        holder.sessionDescription.setText("" + item.getRoomId() + "/" + item.getStartTimestamp());
    }

    @Override
    public int getItemCount() {
        //return 0;
        return daySchedule.getRoomSchedules().get(0).getItems().size();
    }
}
