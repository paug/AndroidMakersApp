package fr.paug.androidmakers.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.text.SimpleDateFormat;
import java.util.Formatter;

import fr.paug.androidmakers.BuildConfig;
import fr.paug.androidmakers.R;
import fr.paug.androidmakers.databinding.ActivityDetailBinding;
import fr.paug.androidmakers.databinding.DetailViewSpeakerInfoElementBinding;
import fr.paug.androidmakers.databinding.SimpleTagBinding;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.model.SocialNetworkHandle;
import fr.paug.androidmakers.model.Speaker;
import fr.paug.androidmakers.ui.view.AgendaView;

/**
 * Created by stan on 19/03/2017.
 */

public class DetailActivity
        extends AppCompatActivity {

    private static final String PARAM_SESSION_ID = "param_session_id";
    private static final String PARAM_SESSION_START_DATE = "param_session_start_date";
    private static final String PARAM_SESSION_END_DATE = "param_session_end_date";
    private static final String PARAM_SESSION_ROOM = "param_session_room";

    private SimpleDateFormat sessionDateFormat;

    public static void startActivity(Context context, AgendaView.Item item) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(PARAM_SESSION_ID, item.getSessionId());
        intent.putExtra(PARAM_SESSION_START_DATE, item.getStartTimestamp());
        intent.putExtra(PARAM_SESSION_END_DATE, item.getEndTimestamp());
        intent.putExtra(PARAM_SESSION_ROOM, item.getRoomId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDetailBinding activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        final Session session = AgendaRepository.getInstance().getSession(
                getIntent().getIntExtra(PARAM_SESSION_ID, -1)
        );

        final long sessionStartDateInMillis = getIntent().getLongExtra(PARAM_SESSION_START_DATE, -1);
        final long sessionEndDateInMillis = getIntent().getLongExtra(PARAM_SESSION_END_DATE, -1);

        final Room sessionRoom = AgendaRepository.getInstance().getRoom(
                getIntent().getIntExtra(PARAM_SESSION_ROOM, -1)
        );

        if (session == null) {
            // We have a problem !
            activityDetailBinding.sessionInformations.setVisibility(View.GONE);
            activityDetailBinding.errorMessage.setVisibility(View.VISIBLE);
            return;
        }

        final String sessionDate = DateUtils.formatDateRange(this, new Formatter(getResources().getConfiguration().locale), sessionStartDateInMillis, sessionEndDateInMillis, DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY | DateUtils.FORMAT_SHOW_TIME, null).toString();
        final String sessionDateAndRoom = sessionRoom != null && !TextUtils.isEmpty(sessionRoom.name) ? getString(R.string.sessionDateWithRoomPlaceholder, sessionDate, sessionRoom.name) : sessionDate;

        activityDetailBinding.sessionTitle.setText(session.title);
        activityDetailBinding.sessionDateAndRoom.setText(sessionDateAndRoom);
        activityDetailBinding.sessionDescription.setText(Html.fromHtml(session.description));
        activityDetailBinding.sessionLanguage.setChipText(session.language);
        activityDetailBinding.sessionLanguage.setOnChipClickListener(new OnChipClickListener() {
            @Override
            public void onChipClick(View view) {
                if (BuildConfig.DEBUG) {
                    Log.d(DetailActivity.class.getName(), "User clicked on tag with content=" + session.language);
                }
                // TODO: Use this for future filter feature
            }
        });
        activityDetailBinding.sessionType.setChipText(session.subtype);
        activityDetailBinding.sessionType.setOnChipClickListener(new OnChipClickListener() {
            @Override
            public void onChipClick(View view) {
                if (BuildConfig.DEBUG) {
                    Log.d(DetailActivity.class.getName(), "User clicked on tag with content=" + session.subtype);
                }
                // TODO: Use this for future filter feature
            }
        });
//        activityDetailBinding.sessionType.setChipIcon();
//        activityDetailBinding.sessionType.setHasIcon(true);

        final ViewGroup sessionSpeakerLayout = (ViewGroup) findViewById(R.id.sessionSpeakerLayout);
        if (session.speakers != null && session.speakers.length > 0) {
            for (int speakerID : session.speakers) {
                final Speaker speaker = AgendaRepository.getInstance().getSpeaker(speakerID);
                if (speaker != null) {
                    final DetailViewSpeakerInfoElementBinding speakerInfoElementBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.detail_view_speaker_info_element, null, false);
                    speakerInfoElementBinding.setSpeaker(speaker);
                    if (speaker.socialNetworkHandles != null && speaker.socialNetworkHandles.size() > 0) {
                        for (final SocialNetworkHandle socialNetworkHandle : speaker.socialNetworkHandles) {
                            final SimpleTagBinding simpleTagElementBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.simple_tag, null, false);
                            //TODO: Change with icons
                            simpleTagElementBinding.simpleTag.setChipText(socialNetworkHandle.name);
                            simpleTagElementBinding.simpleTag.setOnChipClickListener(new OnChipClickListener() {
                                @Override
                                public void onChipClick(View view) {
                                    if (BuildConfig.DEBUG) {
                                        Log.d(DetailActivity.class.getName(), "User clicked on social handle with name=" + socialNetworkHandle.name);
                                    }
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(socialNetworkHandle.link)));
                                }
                            });
                            speakerInfoElementBinding.speakerSocialNetworkHandleLayout.addView(simpleTagElementBinding.getRoot());
                        }
                    } else {
                        speakerInfoElementBinding.speakerSocialNetworkHandleLayout.setVisibility(View.GONE);
                    }
                    sessionSpeakerLayout.addView(speakerInfoElementBinding.getRoot());
                }
            }
        }

        setActionBar(session);
    }

    private void setActionBar(Session session) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(session.title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
