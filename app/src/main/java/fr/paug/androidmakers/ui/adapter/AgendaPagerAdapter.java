package fr.paug.androidmakers.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.paug.androidmakers.R;

public class AgendaPagerAdapter extends PagerAdapter {

//    private final AgendaView.AgendaClickListener mAgendaClickListener;
    private final List<DaySchedule> mAgenda;
    private final SparseArray<View> mAgendaViews = new SparseArray<>();
//    private final AgendaView.AgendaSelector mAgendaSelector;


    private RecyclerView recyclerView;

    public AgendaPagerAdapter(List<DaySchedule> mAgenda) {
        this.mAgenda = mAgenda;
    }

    //    public AgendaPagerAdapter(List<AgendaView.DaySchedule> agenda,
//                              AgendaView.AgendaSelector agendaSelector,
//                              AgendaView.AgendaClickListener listener) {
//        mAgenda = agenda;
//        mAgendaClickListener = listener;
//        mAgendaSelector = agendaSelector;
//    }

//    public void refreshSessionsSelected() {
//        for (int i = 0; i < mAgendaViews.size(); i++) {
//            AgendaView agendaView = mAgendaViews.get(mAgendaViews.keyAt(i));
//            agendaView.refreshSessionsSelected(mAgendaSelector);
//        }
//    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        //TODO Recyclerviews

        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout.schedule_single_day_list, null, false);

        recyclerView = view.findViewById(android.R.id.list);//new RecyclerView(collection.getContext());
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(collection.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        ScheduleDayAdapter adapter = new ScheduleDayAdapter(getItems(position));
        recyclerView.setAdapter(adapter);

        collection.addView(view);
        mAgendaViews.put(position, view);
        return view;

//        AgendaView agendaView = new AgendaView(collection.getContext());
//        agendaView.setAgenda(getItems(position), mAgendaClickListener);
//        agendaView.refreshSessionsSelected(mAgendaSelector);
//        collection.addView(agendaView);
//        mAgendaViews.put(position, agendaView);
//        return agendaView;
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
