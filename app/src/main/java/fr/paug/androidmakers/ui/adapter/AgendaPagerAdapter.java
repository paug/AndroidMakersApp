package fr.paug.androidmakers.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.ui.activity.SessionDetailActivity;
import fr.paug.androidmakers.ui.util.SessionFilter;
import fr.paug.androidmakers.util.sticky_headers.StickyHeadersLinearLayoutManager;

public class AgendaPagerAdapter extends PagerAdapter {

    private final List<DaySchedule> mAgenda;
    private final SparseArray<View> mAgendaViews = new SparseArray<>();
    private Activity activity;

    private List<SessionFilter> sessionFilterList = new ArrayList<>();
    private Set<ScheduleDayAdapter> activeAdapterSet = new HashSet<>();

    public AgendaPagerAdapter(List<DaySchedule> mAgenda, Activity activity) {
        this.mAgenda = mAgenda;
        this.activity = activity;
    }

    public void refreshSessionsSelected() {
        for (RecyclerView.Adapter adapter: activeAdapterSet) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout.schedule_single_day_list, null, false);

        RecyclerView recyclerView;
        recyclerView = view.findViewById(android.R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new StickyHeadersLinearLayoutManager<ScheduleDayAdapter>(activity));

        ScheduleDayAdapter adapter = new ScheduleDayAdapter(activity, getItems(position), true, new ScheduleDayAdapter.OnItemClickListener() {
            @Override public void onItemClick(ScheduleSession agendaItem) {
                SessionDetailActivity.startActivity(activity, agendaItem);
            }
        });
        adapter.setSessionFilterList(sessionFilterList);

        recyclerView.setAdapter(adapter);
        activeAdapterSet.add(adapter);
        moveToCurrentTimeSlot(recyclerView,true, adapter);

        collection.addView(view);
        mAgendaViews.put(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        RecyclerView recyclerView = ((View)view).findViewById(android.R.id.list);
        activeAdapterSet.remove(recyclerView.getAdapter());
        mAgendaViews.remove(position);
    }

    @Override
    public int getCount() {
        return mAgenda.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItems(position).getTitle();
    }

    private DaySchedule getItems(int position) {
        return mAgenda.get(position);
    }

    private void moveToCurrentTimeSlot(RecyclerView recyclerView, boolean animate, ScheduleDayAdapter adapter) {
        final long now = System.currentTimeMillis(); // current time
        final int position = adapter.findTimeHeaderPositionForTime(now);

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (position >= 0) {
            if (animate) {
                smoothScroller.setTargetPosition(position);
                layoutManager.startSmoothScroll(smoothScroller);
            } else {
                layoutManager.scrollToPositionWithOffset(position,
                        this.activity.getResources().getDimensionPixelSize(R.dimen.default_padding));
            }
        }
    }

    public void setSessionFilterList(List<SessionFilter> sessionFilterList) {
        this.sessionFilterList = sessionFilterList;
        for (ScheduleDayAdapter adapter: activeAdapterSet) {
            adapter.setSessionFilterList(sessionFilterList);
        }
    }
}