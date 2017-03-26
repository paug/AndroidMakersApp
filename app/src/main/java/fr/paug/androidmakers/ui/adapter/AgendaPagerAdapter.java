package fr.paug.androidmakers.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.paug.androidmakers.ui.view.AgendaView;

/**
 * Created by stan on 20/03/2017.
 */

public class AgendaPagerAdapter extends PagerAdapter {

    private final AgendaView.AgendaClickListener mAgendaClickListener;
    private final List<AgendaView.DaySchedule> mAgenda;
    private final SparseArray<AgendaView> mAgendaViews = new SparseArray<>();
    private final AgendaView.AgendaSelector mAgendaSelector;

    public AgendaPagerAdapter(List<AgendaView.DaySchedule> agenda,
                              AgendaView.AgendaSelector agendaSelector,
                              AgendaView.AgendaClickListener listener) {
        mAgenda = agenda;
        mAgendaClickListener = listener;
        mAgendaSelector = agendaSelector;
    }

    public void refreshSessionsSelected() {
        for (int i = 0; i < mAgendaViews.size(); i++) {
            AgendaView agendaView = mAgendaViews.get(mAgendaViews.keyAt(i));
            agendaView.refreshSessionsSelected(mAgendaSelector);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        AgendaView agendaView = new AgendaView(collection.getContext());
        agendaView.setAgenda(getItems(position), mAgendaClickListener);
        agendaView.refreshSessionsSelected(mAgendaSelector);
        collection.addView(agendaView);
        mAgendaViews.put(position, agendaView);
        return agendaView;
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

    private AgendaView.DaySchedule getItems(int position) {
        return mAgenda.get(position);
    }

}
