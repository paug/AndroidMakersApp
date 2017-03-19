package fr.paug.androidmakers.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.AgendaItem;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.ui.view.AgendaView;

public class AgendaActivity extends AppCompatActivity implements AgendaView.AgendaClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        AgendaRepository.getInstance().load(new AgendaLoadListener(this));
    }

    @Override
    public void onClick(AgendaItem agendaItem) {
        DetailActivity.startActivity(this, agendaItem.getSessionId());
    }

    private void onAgendaLoaded() {

        AgendaView agendaView = (AgendaView) findViewById(R.id.agenda_view);

        List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();

        SparseArray<List<AgendaItem>> agenda = new SparseArray<>();
        for (ScheduleSlot scheduleSlot : scheduleSlots) {
            List<AgendaItem> agendaItems = agenda.get(scheduleSlot.room);
            if (agendaItems == null) {
                agendaItems = new ArrayList<>();
                agenda.put(scheduleSlot.room, agendaItems);
            }
            agendaItems.add(new AgendaItem(scheduleSlot, getTitle(scheduleSlot.sessionId)));
        }
        agendaView.setAgenda(agenda, this);
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
}
