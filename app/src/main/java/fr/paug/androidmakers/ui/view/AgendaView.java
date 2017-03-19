package fr.paug.androidmakers.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.model.AgendaItem;

/**
 * Created by stan on 18/03/2017.
 */
public class AgendaView extends ScrollView {

    private static final long OFFSET_MS = DateUtils.HOUR_IN_MILLIS;

    private static int[] sBackgrounds = {
            R.drawable.bg_box_blue_selector,
            R.drawable.bg_box_red_selector,
            R.drawable.bg_box_green_selector
    };

    public AgendaView(Context context) {
        super(context);
    }

    public AgendaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AgendaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AgendaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAgenda(SparseArray<List<AgendaItem>> agenda, AgendaClickListener listener) {
        long start = Long.MAX_VALUE;
        long end = Long.MIN_VALUE;

        int[] keys = new int[agenda.size()];
        for (int i = 0; i < agenda.size(); i++) {
            keys[i] = agenda.keyAt(i);

            List<AgendaItem> agendaPerTrack = agenda.get(keys[i]);
            for (AgendaItem agendaItem : agendaPerTrack) {
                start = Math.min(start, agendaItem.getStartTimestamp());
                end = Math.max(end, agendaItem.getEndTimestamp());
            }
        }

        removeAllViews();
        if (end - start > DateUtils.DAY_IN_MILLIS) {
            return; // more than a day? It's a bug
        }

        Context context = getContext();
        LinearLayout rootView = new LinearLayout(context);
        rootView.setOrientation(LinearLayout.HORIZONTAL);

        Calendar instance = Calendar.getInstance();

        int padding = getResources().getDimensionPixelSize(R.dimen.padding);

        // add time
        FrameLayout timeFrameLayout = new FrameLayout(context);
        for (long timestamp = getClosestHourTimestamp(start - OFFSET_MS); timestamp < end + OFFSET_MS;
             timestamp += DateUtils.HOUR_IN_MILLIS) {

            instance.setTimeInMillis(timestamp);
            TextView textView = new TextView(context);
            textView.setPadding(padding, 0, padding, 0);
            textView.setText(String.format(Locale.getDefault(),
                    "%2dh", instance.get(Calendar.HOUR_OF_DAY)));
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = getDpFromDurationMs(timestamp - start);
            timeFrameLayout.addView(textView, lp);
        }
        timeFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rootView.addView(timeFrameLayout);

        // add all agenda items

        for (int i = 0; i < agenda.size(); i++) {
            List<AgendaItem> agendaPerTrack = agenda.get(keys[i]);
            // for track 1
            FrameLayout frameLayout = new FrameLayout(context);
            for (AgendaItem agendaItem : agendaPerTrack) {
                long startDiffWithBegin = agendaItem.getStartTimestamp() - start;
                long endDiffWithBegin = agendaItem.getEndTimestamp() - start;
                long duration = endDiffWithBegin - startDiffWithBegin;

                TextView textView = new TextView(context);
                textView.setText(agendaItem.getTitle());
                textView.setBackgroundResource(
                        sBackgrounds[Math.abs(agendaItem.getRoomColorIndex()) % sBackgrounds.length]
                );
                textView.setOnClickListener(new ViewClickListener(listener, agendaItem));

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        getDpFromDurationMs(duration));
                lp.topMargin = getDpFromDurationMs(startDiffWithBegin + OFFSET_MS);
                frameLayout.addView(textView, lp);
            }
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1
            ));
            rootView.addView(frameLayout);
        }

        setFillViewport(true);
        addView(rootView);
    }

    private long getClosestHourTimestamp(long timestamp) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timestamp);
        instance.add(Calendar.HOUR, 1);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTimeInMillis();
    }

    private int getDpFromDurationMs(long ms) {
        float sp = 150.0f * ms / DateUtils.HOUR_IN_MILLIS;
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics()));
    }

    public interface AgendaClickListener {
        void onClick(AgendaItem agendaItem);
    }

    private static class ViewClickListener implements OnClickListener {

        private AgendaClickListener mAgendaClickListener;
        private AgendaItem mAgendaItem;

        public ViewClickListener(AgendaClickListener agendaClickListener, AgendaItem agendaItem) {
            mAgendaClickListener = agendaClickListener;
            mAgendaItem = agendaItem;
        }

        @Override
        public void onClick(View v) {
            mAgendaClickListener.onClick(mAgendaItem);
        }
    }
}
