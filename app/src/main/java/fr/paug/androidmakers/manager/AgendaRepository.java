package fr.paug.androidmakers.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.paug.androidmakers.model.FirebaseDataConverted;
import fr.paug.androidmakers.model.PartnerGroup;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.model.Speaker;
import fr.paug.androidmakers.model.Venue;

/**
 * Created by stan on 18/03/2017.
 */
public class AgendaRepository {
    private static final String TAG = "AgendaRepository";

    private final FirebaseDatabase mDatabase;
    private final DatabaseReference mDatabaseReference;

    @NonNull
    private final List<OnLoadListener> mOnLoadListeners;
    private boolean mLoaded;
    private FirebaseDataConverted mFirebaseDataConverted = new FirebaseDataConverted();

    private AgendaRepository() {
        Log.e(TAG, "AgendaRepo created");
        mOnLoadListeners = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFirebaseDataConverted.loadAllFromFirebase(dataSnapshot.getValue());
                List<OnLoadListener> listenersCpy = new ArrayList<>(mOnLoadListeners);
                for (OnLoadListener listener : listenersCpy) {
                    listener.onAgendaLoaded();
                }
                mLoaded = true;
                Log.e(TAG, "AgendaRepo loaded");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // nothing to do
            }
        });
    }

    public static AgendaRepository getInstance() {
        return SingletonHolder.instance;
    }

    public boolean isLoaded() {
        return mLoaded;
    }

    public void load(OnLoadListener listener) {
        if (mLoaded) {
            listener.onAgendaLoaded();
            return;
        }
        mOnLoadListeners.add(listener);
    }

    @Nullable
    public Room getRoom(int id) {
        return mFirebaseDataConverted.getRooms().get(id);
    }

    @Nullable
    public Session getSession(int id) {
        return mFirebaseDataConverted.getSessions().get(id);
    }

    @Nullable
    public Speaker getSpeaker(int id) {
        return mFirebaseDataConverted.getSpeakers().get(id);
    }

    @Nullable
    public Venue getVenue(int id) {
        return mFirebaseDataConverted.getVenues().get(id);
    }

    @NonNull
    public List<ScheduleSlot> getScheduleSlots() {
        return new ArrayList<>(mFirebaseDataConverted.getScheduleSlots());
    }

    @Nullable
    public ScheduleSlot getScheduleSlot(int id) {
        for (ScheduleSlot slot : mFirebaseDataConverted.getScheduleSlots()) {
            if (slot.sessionId == id) {
                return slot;
            }
        }
        return null;
    }

    @Nullable
    public ScheduleSlot getScheduleSlot(@NonNull String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            return getScheduleSlot(idAsInt);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Cannot format " + id + "into an int: ", e);
        }
        return null;
    }

    public Map<PartnerGroup.PartnerType, PartnerGroup> getPartners() {
        return mFirebaseDataConverted.getPartners();
    }

    public Set<String> getAllLanguages() {
        return mFirebaseDataConverted.getAllLanguages();
    }

    public void removeListener(@NonNull OnLoadListener listener) {
        mOnLoadListeners.remove(listener);
    }

    public interface OnLoadListener {
        void onAgendaLoaded();
    }

    private static class SingletonHolder {
        private final static AgendaRepository instance = new AgendaRepository();
    }

}
