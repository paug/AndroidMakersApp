package fr.paug.androidmakers.ui.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

    DaySchedule daySchedule;

    public interface OnItemClickListener {
        void onItemClick(ScheduleSession scheduleSession);
    }

    private final OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout sessionLayout;
        public TextView sessionTitle;
        public TextView sessionDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            sessionLayout = itemView.findViewById(R.id.sessionItemLayout);
            sessionTitle = itemView.findViewById(R.id.sessionTitleTextView);
            sessionDescription = itemView.findViewById(R.id.sessionDescriptionTextView);
        }
    }

    public ScheduleDayAdapter(DaySchedule daySchedule, OnItemClickListener listener) {
        this.daySchedule = daySchedule;
        this.listener = listener;
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
        final ScheduleSession scheduleSession = daySchedule.getRoomSchedules().get(0).getItems().get(position);
        holder.sessionTitle.setText(scheduleSession.getTitle());
        holder.sessionDescription.setText("" + scheduleSession.getRoomId() + "/" + scheduleSession.getStartTimestamp());

        holder.sessionLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(scheduleSession);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return 0;
        return daySchedule.getRoomSchedules().get(0).getItems().size();
    }
}
