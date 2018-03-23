package fr.paug.androidmakers.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.service.SessionAlarmService;
import fr.paug.androidmakers.ui.adapter.AgendaPagerAdapter;
import fr.paug.androidmakers.ui.adapter.DaySchedule;
import fr.paug.androidmakers.ui.adapter.RoomSchedule;
import fr.paug.androidmakers.ui.adapter.ScheduleSession;

public class AgendaFragment extends Fragment {

    private View mProgressView;
    private View mEmptyView;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        mViewPager = view.findViewById(R.id.viewpager);
        mProgressView = view.findViewById(R.id.progressbar);
        mEmptyView = view.findViewById(R.id.empty_view);

        // auto dismiss loading
        new Handler().postDelayed(new RefreshRunnable(this), 3000); 

        AgendaRepository.getInstance().load(new AgendaLoadListener(this));

        //setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter instanceof AgendaPagerAdapter) {
            ((AgendaPagerAdapter) adapter).refreshSessionsSelected();
        }
    }

    private void onAgendaLoaded() {
        final SparseArray<DaySchedule> itemByDayOfTheYear = new SparseArray<>();

        final Calendar calendar = Calendar.getInstance();
        final List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();
        for (final ScheduleSlot scheduleSlot : scheduleSlots) {
            final List<ScheduleSession> agendaScheduleSessions = getAgendaItems(
                    itemByDayOfTheYear, calendar, scheduleSlot);
            agendaScheduleSessions.add(new ScheduleSession(scheduleSlot, getTitle(scheduleSlot.sessionId)));
        }

        final List<DaySchedule> days = getItemsOrdered(itemByDayOfTheYear);
        Log.d("days items", days.toString());

        final AgendaPagerAdapter adapter = new AgendaPagerAdapter(days, getActivity());
        mViewPager.setAdapter(adapter);

        final int indexOfToday = getTodayIndex(days);
        if (indexOfToday > 0) {
            mViewPager.setCurrentItem(indexOfToday, true);
        }
        refreshViewsDisplay();
    }

    private void refreshViewsDisplay() {
        mProgressView.setVisibility(View.GONE);
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
        }
    }

    private int getTodayIndex(List<DaySchedule> items) {
        if (items == null || items.size() < 2) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        for (int i = 1; i < items.size(); i++) {
            DaySchedule agendaDaySchedule = items.get(i);
            List<RoomSchedule> roomSchedules = agendaDaySchedule.getRoomSchedules();
            if (!roomSchedules.isEmpty()) {
                List<ScheduleSession> scheduleSessionList = roomSchedules.get(0).getItems();
                if (!scheduleSessionList.isEmpty()) {
                    ScheduleSession scheduleSession = scheduleSessionList.get(0);
                    calendar.setTimeInMillis(scheduleSession.getStartTimestamp());
                    if (calendar.get(Calendar.YEAR) == year
                            && calendar.get(Calendar.DAY_OF_YEAR) == dayOfYear) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    @NonNull
    private List<ScheduleSession> getAgendaItems(SparseArray<DaySchedule> itemByDayOfTheYear,
                                                 Calendar calendar, ScheduleSlot scheduleSlot) {
        List<RoomSchedule> roomSchedules =
                getRoomScheduleForDay(itemByDayOfTheYear, calendar, scheduleSlot);
        RoomSchedule roomScheduleForThis = null;
        for (RoomSchedule roomSchedule : roomSchedules) {
            if (roomSchedule.getRoomId() == scheduleSlot.room) {
                roomScheduleForThis = roomSchedule;
                break;
            }
        }
        if (roomScheduleForThis == null) {
            List<ScheduleSession> agendaScheduleSessions = new ArrayList<>();
            Room room = AgendaRepository.getInstance().getRoom(scheduleSlot.room);
            String titleRoom = (room == null) ? null : room.name;
            roomScheduleForThis = new RoomSchedule(
                    scheduleSlot.room, titleRoom, agendaScheduleSessions);
            roomSchedules.add(roomScheduleForThis);
            Collections.sort(roomSchedules);
            return agendaScheduleSessions;
        } else {
            return roomScheduleForThis.getItems();
        }
    }

    private List<RoomSchedule> getRoomScheduleForDay(
            SparseArray<DaySchedule> itemByDayOfTheYear,
            Calendar calendar, ScheduleSlot scheduleSlot) {
        calendar.setTimeInMillis(scheduleSlot.startDate);
        int dayIndex = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR) * 1000;
        DaySchedule daySchedule = itemByDayOfTheYear.get(dayIndex);
        if (daySchedule == null) {
            List<RoomSchedule> roomSchedule = new ArrayList<>();
            String title = DateFormat.getDateInstance().format(calendar.getTime());
            daySchedule = new DaySchedule(title, roomSchedule);
            itemByDayOfTheYear.put(dayIndex, daySchedule);
            return roomSchedule;
        } else {
            return daySchedule.getRoomSchedules();
        }
    }

    @NonNull
    private List<DaySchedule> getItemsOrdered(
            SparseArray<DaySchedule> itemByDayOfTheYear) {
        int size = itemByDayOfTheYear.size();
        int[] keysSorted = new int[size];
        for (int i = 0; i < size; i++) {
            keysSorted[i] = itemByDayOfTheYear.keyAt(i);
        }
        Arrays.sort(keysSorted);
        List<DaySchedule> items = new ArrayList<>(size);
        for (int key : keysSorted) {
            items.add(itemByDayOfTheYear.get(key));
        }
        return items;
    }

    private String getTitle(int sessionId) {
        Session session = AgendaRepository.getInstance().getSession(sessionId);
        return session == null ? "?" : session.title;
    }

    private static class RefreshRunnable implements Runnable {
        private WeakReference<AgendaFragment> mAgendaActivity;

        private RefreshRunnable(AgendaFragment agendaFragment) {
            mAgendaActivity = new WeakReference<>(agendaFragment);
        }

        @Override
        public void run() {
            AgendaFragment agendaFragment = mAgendaActivity.get();
            if (agendaFragment != null) {
                agendaFragment.refreshViewsDisplay();
            }
        }
    }

    private static class AgendaLoadListener implements AgendaRepository.OnLoadListener {
        private WeakReference<AgendaFragment> reference;

        private AgendaLoadListener(AgendaFragment agendaFragment) {
            reference = new WeakReference<>(agendaFragment);
        }

        @Override
        public void onAgendaLoaded() {
            AgendaFragment agendaFragment = reference.get();
            if (agendaFragment == null) {
                return;
            }
            agendaFragment.onAgendaLoaded();

            final AgendaFragment fragment = reference.get();
            if (fragment != null) {
                // reschedule all starred blocks in case one session start or stop time has changed
                final Context ctx = fragment.getContext();
                Intent scheduleIntent = new Intent(
                        SessionAlarmService.ACTION_SCHEDULE_ALL_STARRED_BLOCKS,
                        null, ctx, SessionAlarmService.class);
                ctx.startService(scheduleIntent);
            }
        }
    }

}