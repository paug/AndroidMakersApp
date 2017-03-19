package fr.paug.androidmakers.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.paug.androidmakers.model.FirebaseDataConverted;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.model.Speaker;

/**
 * Created by stan on 18/03/2017.
 */

public class AgendaRepository {

    private final FirebaseDatabase mDatabase;
    private final DatabaseReference mDatabaseReference;

    private OnLoadListener mOnLoadListener;
    private boolean mLoaded;
    private FirebaseDataConverted mFirebaseDataConverted = new FirebaseDataConverted();

    private AgendaRepository() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFirebaseDataConverted.loadAllFromFirebase(dataSnapshot.getValue());
                if(mOnLoadListener != null) {
                    mOnLoadListener.onAgendaLoaded();
                }
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

    public void load(OnLoadListener listener) {
        if (mLoaded) {
            listener.onAgendaLoaded();
            return;
        }
        mOnLoadListener = listener;
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

    @NonNull
    public List<ScheduleSlot> getScheduleSlots() {
        return new ArrayList<>(mFirebaseDataConverted.getScheduleSlots());
    }

    public interface OnLoadListener {
        void onAgendaLoaded();
    }

    private static class SingletonHolder {
        private final static AgendaRepository instance = new AgendaRepository();
    }

}
