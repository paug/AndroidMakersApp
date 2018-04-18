package fr.paug.androidmakers.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

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
import fr.paug.androidmakers.ui.util.SessionFilter;
import fr.paug.androidmakers.util.EmojiUtils;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static fr.paug.androidmakers.ui.util.SessionFilter.FilterType.BOOKMARK;
import static fr.paug.androidmakers.ui.util.SessionFilter.FilterType.LANGUAGE;
import static fr.paug.androidmakers.ui.util.SessionFilter.FilterType.ROOM;

public class AgendaFragment extends Fragment {

    private View mProgressView;
    private View mEmptyView;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ViewGroup mFiltersView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);

        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        mViewPager = view.findViewById(R.id.viewpager);
        mProgressView = view.findViewById(R.id.progressbar);
        mEmptyView = view.findViewById(R.id.empty_view);
        mFiltersView = view.findViewById(R.id.filters);
        mDrawerLayout = (DrawerLayout) view;

        // auto dismiss loading
        new Handler().postDelayed(new RefreshRunnable(this), 3000); 

        AgendaRepository.getInstance().load(new AgendaLoadListener(this));

        initFilters();

        return view;
    }

    private List<SessionFilter> allSessionFilters = new ArrayList<>();
    private List<CheckBox> allCheckBoxes = new ArrayList<>();

    private CompoundButton.OnCheckedChangeListener mCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            applyFilters();
        }

    };

    private void applyFilters() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter instanceof AgendaPagerAdapter) {
            List<SessionFilter> sessionFilterList = new ArrayList<SessionFilter>();

            for (int i = 0; i < allCheckBoxes.size(); i++) {
                if (allCheckBoxes.get(i).isChecked()) {
                    sessionFilterList.add(allSessionFilters.get(i));
                }
            }
            ((AgendaPagerAdapter) adapter).setSessionFilterList(sessionFilterList);
        }
    }

    private void initFilters() {
        mFiltersView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // a dummy touch listener that makes sure we don't click through the filter list
                return true;
            }
        });
        addFilterHeader(R.string.filter);
        addFilter(new SessionFilter(BOOKMARK, null), null);

        addFilterHeader(R.string.language);
        addFilter(new SessionFilter(LANGUAGE, "fr"), null);
        addFilter(new SessionFilter(LANGUAGE, "en"), null);

        AgendaRepository.getInstance().load(new AgendaRepository.OnLoadListener() {
            @Override
            public void onAgendaLoaded() {
                addFilterHeader(R.string.rooms);
                SparseArray<Room> rooms = AgendaRepository.getInstance().getAllRooms();
                for(int i = 0; i < rooms.size(); i++) {
                    int key = rooms.keyAt(i);
                    String roomName = rooms.get(key).name;

                    if (!TextUtils.isEmpty(roomName)) {
                        addFilter(new SessionFilter(ROOM, key), roomName);
                    }
                }
                AgendaRepository.getInstance().removeListener(this);
            }
        });

    }

    private void addFilter(SessionFilter sessionFilter, String roomName) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_item, mDrawerLayout, false);
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(mCheckBoxOnCheckedChangeListener);
        mFiltersView.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Context context = mFiltersView.getContext();

        allCheckBoxes.add(checkBox);
        allSessionFilters.add(sessionFilter);

        String name = "";

        View bookmark = view.findViewById(R.id.bookmark);
        TextView flag = view.findViewById(R.id.flag);

        bookmark.setVisibility(View.GONE);
        flag.setVisibility(View.GONE);

        switch(sessionFilter.type) {
            case BOOKMARK: {
                name = context.getString(R.string.bookmarked);
                bookmark.setVisibility(View.VISIBLE);
                break;
            }
            case LANGUAGE: {
                int nameResId = "fr".equals(sessionFilter.value) ? R.string.french : R.string.english;
                name = context.getString(nameResId);

                flag.setText(EmojiUtils.getLanguageInEmoji((String)sessionFilter.value));
                flag.setVisibility(View.VISIBLE);
                break;
            }
            case ROOM: {
                bookmark.setVisibility(View.INVISIBLE);
                name = roomName;
                break;
            }
        }

        ((TextView)view.findViewById(R.id.name)).setText(name);
    }

    private void addFilterHeader(@StringRes int titleResId) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_header, mDrawerLayout, false);
        ((TextView)view).setText(titleResId);
        mFiltersView.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.add(getActivity().getString(R.string.filter));
        menuItem.setIcon(R.drawable.ic_filter_list_white_24dp);
        menuItem.setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
        });
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
            agendaScheduleSessions.add(new ScheduleSession(scheduleSlot, getTitle(scheduleSlot.sessionId), getLanguage(scheduleSlot.sessionId)));
        }

        final List<DaySchedule> days = getItemsOrdered(itemByDayOfTheYear);

        final AgendaPagerAdapter adapter = new AgendaPagerAdapter(days, getActivity());
        mViewPager.setAdapter(adapter);
        applyFilters();

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

    private String getLanguage(int sessionId) {
        Session session = AgendaRepository.getInstance().getSession(sessionId);
        return session == null ? "?" : session.language;
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