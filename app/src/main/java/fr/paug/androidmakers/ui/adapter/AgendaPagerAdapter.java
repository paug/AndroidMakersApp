package fr.paug.androidmakers.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.paug.androidmakers.ui.view.AgendaView;

/**
 * Created by stan on 20/03/2017.
 */

public class AgendaPagerAdapter extends PagerAdapter {

    private AgendaView.AgendaClickListener mAgendaClickListener;
    private List<AgendaView.Items> mAgenda;

    public AgendaPagerAdapter(List<AgendaView.Items> agenda,
                              AgendaView.AgendaClickListener listener) {
        mAgenda = agenda;
        mAgendaClickListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        AgendaView agendaView = new AgendaView(collection.getContext());
        agendaView.setAgenda(getItems(position), mAgendaClickListener);
        collection.addView(agendaView);
        return agendaView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
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

    private AgendaView.Items getItems(int position) {
        return mAgenda.get(position);
    }

}
