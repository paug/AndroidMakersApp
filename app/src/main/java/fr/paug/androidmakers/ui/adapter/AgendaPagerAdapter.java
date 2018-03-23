package fr.paug.androidmakers.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.ui.activity.SessionDetailActivity;
import fr.paug.androidmakers.util.sticky_headers.StickyHeadersLinearLayoutManager;

public class AgendaPagerAdapter extends PagerAdapter {

    private final List<DaySchedule> mAgenda;
    private final SparseArray<View> mAgendaViews = new SparseArray<>();
    private Activity activity;

    private RecyclerView recyclerView;

    public AgendaPagerAdapter(List<DaySchedule> mAgenda, Activity activity) {
        this.mAgenda = mAgenda;
        this.activity = activity;
    }

    public void refreshSessionsSelected() {
        for (int i = 0; i < mAgendaViews.size(); i++) {
            View view = mAgendaViews.get(mAgendaViews.keyAt(i));
            RecyclerView recyclerView = view.findViewById(android.R.id.list);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout.schedule_single_day_list, null, false);

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

        recyclerView.setAdapter(adapter);

        collection.addView(view);
        mAgendaViews.put(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
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

}