package fr.paug.androidmakers.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
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
import fr.paug.androidmakers.ui.view.AgendaView;

public class AgendaActivity extends AppCompatActivity implements AgendaView.AgendaClickListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        AgendaRepository.getInstance().load(new AgendaLoadListener(this));
    }

    @Override
    public void onClick(AgendaView.Item agendaItem) {
        DetailActivity.startActivity(this, agendaItem);
    }

    private void onAgendaLoaded() {
        List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();

        SparseArray<AgendaView.Items> itemByDayOfTheYear = new SparseArray<>();

        Calendar calendar = Calendar.getInstance();
        for (ScheduleSlot scheduleSlot : scheduleSlots) {
            calendar.setTimeInMillis(scheduleSlot.startDate);
            int dayIndex = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR) * 1000;
            AgendaView.Items items = itemByDayOfTheYear.get(dayIndex);
            SparseArray<List<AgendaView.Item>> itemsSparseArray;
            if (items == null) {
                itemsSparseArray = new SparseArray<>();
                String title = DateFormat.getDateInstance().format(calendar.getTime());
                items = new AgendaView.Items(title, itemsSparseArray);
                itemByDayOfTheYear.put(dayIndex, items);
            } else {
                itemsSparseArray = items.getItems();
            }
            List<AgendaView.Item> agendaItems = itemsSparseArray.get(scheduleSlot.room);
            if (agendaItems == null) {
                agendaItems = new ArrayList<>();
                itemsSparseArray.put(scheduleSlot.room, agendaItems);
            }
            agendaItems.add(new AgendaView.Item(scheduleSlot, getTitle(scheduleSlot.sessionId)));
        }

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
        PagerAdapter adapter = new AgendaPagerAdapter(items, this);
        mViewPager.setAdapter(adapter);
    }

    private String getTitle(int sessionId) {
        Session session = AgendaRepository.getInstance().getSession(sessionId);
        return session == null ? "?" : session.title;
    }

    private static class AgendaLoadListener implements AgendaRepository.OnLoadListener {
        private WeakReference<AgendaActivity> mAgendaActivity;

        private AgendaLoadListener(AgendaActivity agendaActivity) {
            mAgendaActivity = new WeakReference<>(agendaActivity);
        }

        @Override
        public void onAgendaLoaded() {
            AgendaActivity agendaActivity = mAgendaActivity.get();
            if (agendaActivity == null) {
                return;
            }
            agendaActivity.onAgendaLoaded();
        }
    }

    private static class AgendaPagerAdapter extends PagerAdapter {

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
}
