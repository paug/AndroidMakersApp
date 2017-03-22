package fr.paug.androidmakers.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.ui.activity.DetailActivity;
import fr.paug.androidmakers.ui.adapter.AgendaPagerAdapter;
import fr.paug.androidmakers.ui.view.AgendaView;

public class AgendaFragment extends Fragment implements AgendaView.AgendaClickListener {

    private View mProgressView;
    private View mEmptyView;
    private ViewPager mViewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mProgressView = view.findViewById(R.id.progressbar);
        mEmptyView = view.findViewById(R.id.empty_view);

        // auto dismiss loading
        new Handler().postDelayed(new RefreshRunnable(this), 3000);

        AgendaRepository.getInstance().load(new AgendaLoadListener(this));

        return view;
    }

    @Override
    public void onClick(AgendaView.Item agendaItem) {
        DetailActivity.startActivity(getActivity(), agendaItem);
    }

    private void onAgendaLoaded() {
        List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();

        SparseArray<AgendaView.Items> itemByDayOfTheYear = new SparseArray<>();

        Calendar calendar = Calendar.getInstance();
        for (ScheduleSlot scheduleSlot : scheduleSlots) {
            List<AgendaView.Item> agendaItems = getAgendaItems(
                    itemByDayOfTheYear, calendar, scheduleSlot);
            agendaItems.add(new AgendaView.Item(scheduleSlot, getTitle(scheduleSlot.sessionId)));
        }

        List<AgendaView.Items> items = getItemsOrdered(itemByDayOfTheYear);
        PagerAdapter adapter = new AgendaPagerAdapter(items, this);
        mViewPager.setAdapter(adapter);

        int indexOfToday = getTodayIndex(items);
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

    private int getTodayIndex(List<AgendaView.Items> items) {
        if (items == null || items.size() < 2) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        for (int i = 1; i < items.size(); i++) {
            AgendaView.Items agendaItems = items.get(i);
            SparseArray<List<AgendaView.Item>> agendaItemByRoom = agendaItems.getItems();
            if (agendaItemByRoom != null && agendaItemByRoom.size() > 0) {
                List<AgendaView.Item> list = agendaItemByRoom.get(agendaItemByRoom.keyAt(0));
                if (list != null && !list.isEmpty()) {
                    AgendaView.Item item = list.get(0);
                    calendar.setTimeInMillis(item.getStartTimestamp());
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
    private List<AgendaView.Item> getAgendaItems(SparseArray<AgendaView.Items> itemByDayOfTheYear,
                                                 Calendar calendar, ScheduleSlot scheduleSlot) {
        SparseArray<List<AgendaView.Item>> itemsSparseArray =
                getItemsByRoomList(itemByDayOfTheYear, calendar, scheduleSlot);
        List<AgendaView.Item> agendaItems = itemsSparseArray.get(scheduleSlot.room);
        if (agendaItems == null) {
            agendaItems = new ArrayList<>();
            itemsSparseArray.put(scheduleSlot.room, agendaItems);
        }
        return agendaItems;
    }

    private SparseArray<List<AgendaView.Item>> getItemsByRoomList(
            SparseArray<AgendaView.Items> itemByDayOfTheYear,
            Calendar calendar, ScheduleSlot scheduleSlot) {

        calendar.setTimeInMillis(scheduleSlot.startDate);
        int dayIndex = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR) * 1000;
        AgendaView.Items items = itemByDayOfTheYear.get(dayIndex);
        SparseArray<List<AgendaView.Item>> itemsSparseArray;
        if (items == null) {
            itemsSparseArray = new SparseArray<>();
            String title = DateFormat.getDateInstance().format(calendar.getTime());
            items = new AgendaView.Items(title, itemsSparseArray);
            itemByDayOfTheYear.put(dayIndex, items);
            return itemsSparseArray;
        } else {
            return items.getItems();
        }
    }

    @NonNull
    private List<AgendaView.Items> getItemsOrdered(
            SparseArray<AgendaView.Items> itemByDayOfTheYear) {
        int size = itemByDayOfTheYear.size();
        int[] keysSorted = new int[size];
        for (int i = 0; i < size; i++) {
            keysSorted[i] = itemByDayOfTheYear.keyAt(i);
        }
        Arrays.sort(keysSorted);
        List<AgendaView.Items> items = new ArrayList<>(size);
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
        private WeakReference<AgendaFragment> mAgendaActivity;

        private AgendaLoadListener(AgendaFragment agendaFragment) {
            mAgendaActivity = new WeakReference<>(agendaFragment);
        }

        @Override
        public void onAgendaLoaded() {
            AgendaFragment agendaFragment = mAgendaActivity.get();
            if (agendaFragment == null) {
                return;
            }
            agendaFragment.onAgendaLoaded();
        }
    }
}
