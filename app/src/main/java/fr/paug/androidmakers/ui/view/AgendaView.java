package fr.paug.androidmakers.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.model.ScheduleSlot;

/**
 * Created by stan on 18/03/2017.
 */
public class AgendaView extends ScrollView {

    private static final long OFFSET_MS = DateUtils.HOUR_IN_MILLIS / 2;

    private static int[] sBackgrounds = {
            R.drawable.bg_box_blue_selector,
            R.drawable.bg_box_red_selector,
            R.drawable.bg_box_green_selector
    };
    private Calendar mCalendar = Calendar.getInstance();
    private SparseArray<String> mTimeLabel = new SparseArray<>();
    private int mTimeWidth;
    private long mInitialTime;
    private Paint mLinePaint;
    private Paint mLabelBackgroundPaint;
    private TextPaint mTextPaint;
    private int mPadding;

    private RectF mRectF = new RectF();

    public AgendaView(Context context) {
        super(context);
        init();
    }

    public AgendaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AgendaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AgendaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setAgenda(DaySchedule daySchedule, AgendaClickListener listener) {
        long start = Long.MAX_VALUE;
        long end = Long.MIN_VALUE;

        List<RoomSchedule> agenda = daySchedule.getRoomSchedules();
        for (int i = 0; i < agenda.size(); i++) {
            List<Item> agendaPerTrack = agenda.get(i).getItems();
            for (Item agendaItem : agendaPerTrack) {
                start = Math.min(start, agendaItem.getStartTimestamp());
                end = Math.max(end, agendaItem.getEndTimestamp());
            }
        }

        removeAllViews();
        if (end - start > DateUtils.DAY_IN_MILLIS) {
            mInitialTime = 0;
            return; // more than a day? It's a bug
        }

        Context context = getContext();
        LinearLayout rootView = new LinearLayout(context);
        rootView.setOrientation(LinearLayout.HORIZONTAL);

        mInitialTime = start - OFFSET_MS;

        // add all agenda items

        for (int i = 0; i < agenda.size(); i++) {
            RoomSchedule roomSchedule = agenda.get(i);
            List<Item> agendaPerTrack = roomSchedule.getItems();
            // for track 1
            FrameLayout frameLayout = new FrameLayout(context);
            for (Item agendaItem : agendaPerTrack) {
                long startDiffWithBegin = agendaItem.getStartTimestamp() - mInitialTime;
                long duration = agendaItem.getEndTimestamp() - agendaItem.getStartTimestamp();

                TextView textView = new TextView(context);
                textView.setText(agendaItem.getTitle());
                textView.setTextColor(Color.WHITE);
                textView.getPaint().setFakeBoldText(true);
                textView.setBackgroundResource(
                        sBackgrounds[Math.abs(agendaItem.getRoomId()) % sBackgrounds.length]
                );
                textView.setOnClickListener(new ViewClickListener(listener, agendaItem));

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        getPxFromDurationMs(duration));
                lp.topMargin = getPxFromDurationMs(startDiffWithBegin);
                frameLayout.addView(textView, lp);
            }
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1
            ));
            frameLayout.setTag(roomSchedule.mTitle);
            rootView.addView(frameLayout);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        if (getChildCount() == 0) {
            lp.leftMargin = mTimeWidth;
        }
        addView(rootView, lp);

        long now = System.currentTimeMillis();
        if (start < now && now < end) {
            scrollToSee(now - DateUtils.HOUR_IN_MILLIS);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mInitialTime > 0) {
            int y = 0;
            long timestampFirstHour = getHourBeforeTimestamp(mInitialTime);
            for (long timestamp = timestampFirstHour;
                 y < getScrollY() + getHeight(); timestamp += DateUtils.HOUR_IN_MILLIS) {

                y = getPxFromDurationMs(timestamp - mInitialTime) + getPaddingTop();
                String label = getTimeLabel(timestamp);
                drawLineHour(canvas, y, label);
            }
        }

        super.draw(canvas);

        drawRoomName(canvas);
    }

    private void drawRoomName(Canvas canvas) {
        if (getChildCount() != 1) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);

        float offsetY = getScrollY();

        int x = mTimeWidth;
        int childCount = rootView.getChildCount();
        int widthPerItem = (getWidth() - mTimeWidth) / childCount;
        for (int i = 0; i < childCount; i++) {
            View view = rootView.getChildAt(i);
            Object tag = view.getTag();
            if (tag instanceof String) {
                String roomName = (String) tag;

                float measureText = mTextPaint.measureText(roomName);
                float xText = x + widthPerItem / 2 - measureText / 2f;
                float yText = offsetY + mTextPaint.getTextSize() + mPadding;

                mRectF.set(xText - mPadding, offsetY + mPadding / 2,
                        xText + measureText + mPadding, yText + mPadding);

                canvas.drawRoundRect(mRectF, mPadding / 2, mPadding / 2, mLabelBackgroundPaint);
                canvas.drawText(roomName, xText, yText, mTextPaint);
            }
            x += widthPerItem;
        }
    }

    private String getTimeLabel(long timestamp) {
        int hash = Long.valueOf(timestamp).hashCode();
        String label = mTimeLabel.get(hash);
        if (label == null) {
            mCalendar.setTimeInMillis(timestamp);
            label = String.format(Locale.getDefault(), "%02d:00",
                    mCalendar.get(Calendar.HOUR_OF_DAY));
        }
        return label;
    }

    private void drawLineHour(Canvas canvas, int y, String label) {
        canvas.drawLine(mTimeWidth, y, getWidth(), y, mLinePaint);

        float measureText = mTextPaint.measureText(label);
        canvas.drawText(label,
                0, label.length(),
                mTimeWidth / 2 - measureText / 2f,
                y + (mTextPaint.getTextSize() - mTextPaint.descent()) / 2,
                mTextPaint);
    }

    private long getHourBeforeTimestamp(long timestamp) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timestamp);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTimeInMillis();
    }

    private int getPxFromDurationMs(long ms) {
        float sp = 150.0f * ms / DateUtils.HOUR_IN_MILLIS;
        return getPixelFromSp(sp);
    }

    private int getPixelFromSp(float sp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics()));
    }

    private void scrollToSee(long timestamp) {
        final int pxFromDurationMs = getPxFromDurationMs(timestamp - mInitialTime);
        post(new Runnable() {
            @Override
            public void run() {
                setScrollY(pxFromDurationMs);
            }
        });
    }

    private void init() {
        setWillNotDraw(false);
        setFillViewport(true);
        setClipChildren(false);
        setClipToPadding(false);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(1f);

        mLabelBackgroundPaint = new Paint();
        mLabelBackgroundPaint.setColor(Color.argb(220, 250, 250, 250));
        mLabelBackgroundPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(getPixelFromSp(14));

        mTimeWidth = getPixelFromSp(50);

        mPadding = getResources().getDimensionPixelSize(R.dimen.padding);
        setPadding(0, mPadding, 0, mPadding);
    }

    public interface AgendaClickListener {
        void onClick(Item agendaItem);
    }

    public static class DaySchedule {
        private final String mTitle;
        @NonNull
        private final List<RoomSchedule> mRoomSchedules;

        public DaySchedule(String title, @NonNull List<RoomSchedule> roomSchedules) {
            mTitle = title;
            mRoomSchedules = roomSchedules;
        }

        public String getTitle() {
            return mTitle;
        }

        @NonNull
        public List<RoomSchedule> getRoomSchedules() {
            return mRoomSchedules;
        }
    }

    public static class RoomSchedule implements Comparable<RoomSchedule> {
        private final int mRoomId;
        private final String mTitle;
        @NonNull
        private final List<AgendaView.Item> mItems;

        public RoomSchedule(int roomId, String title, @NonNull List<Item> items) {
            mRoomId = roomId;
            mTitle = title;
            mItems = items;
        }

        public int getRoomId() {
            return mRoomId;
        }

        public String getTitle() {
            return mTitle;
        }

        @NonNull
        public List<Item> getItems() {
            return mItems;
        }

        @Override
        public int compareTo(@NonNull RoomSchedule o) {
            return mRoomId - o.mRoomId;
        }
    }

    public static class Item {

        private ScheduleSlot mScheduleSlot;
        private String mTitle;

        public Item(ScheduleSlot scheduleSlot, String title) {
            mScheduleSlot = scheduleSlot;
            mTitle = title;
        }

        public long getStartTimestamp() {
            return mScheduleSlot.startDate;
        }

        public long getEndTimestamp() {
            return mScheduleSlot.endDate;
        }

        public String getTitle() {
            return mTitle;
        }

        public int getRoomId() {
            return mScheduleSlot.room;
        }

        public int getSessionId() {
            return mScheduleSlot.sessionId;
        }
    }


    private static class ViewClickListener implements OnClickListener {

        private AgendaClickListener mAgendaClickListener;
        private Item mAgendaItem;

        public ViewClickListener(AgendaClickListener agendaClickListener, Item agendaItem) {
            mAgendaClickListener = agendaClickListener;
            mAgendaItem = agendaItem;
        }

        @Override
        public void onClick(View v) {
            mAgendaClickListener.onClick(mAgendaItem);
        }
    }
}
