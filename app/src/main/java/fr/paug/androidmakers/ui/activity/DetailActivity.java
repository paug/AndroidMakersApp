package fr.paug.androidmakers.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import fr.paug.androidmakers.BuildConfig;
import fr.paug.androidmakers.R;
import fr.paug.androidmakers.databinding.ActivityDetailBinding;
import fr.paug.androidmakers.databinding.DetailViewSpeakerInfoElementBinding;
import fr.paug.androidmakers.databinding.SmallRibbonImageBinding;
import fr.paug.androidmakers.databinding.SmallSocialImageBinding;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Ribbon;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.model.SocialNetworkHandle;
import fr.paug.androidmakers.model.Speaker;
import fr.paug.androidmakers.service.SessionAlarmService;
import fr.paug.androidmakers.ui.adapter.ScheduleSession;
import fr.paug.androidmakers.util.SessionSelector;

/**
 * Details of a session
 */
public class DetailActivity extends BaseActivity {

    private static final String PARAM_SESSION_ID = "param_session_id";
    private static final String PARAM_SESSION_START_DATE = "param_session_start_date";
    private static final String PARAM_SESSION_END_DATE = "param_session_end_date";
    private static final String PARAM_SESSION_ROOM = "param_session_room";

    private int sessionId;
    private long sessionStartDateInMillis;
    private long sessionEndDateInMillis;

    private Session session;
    private String sessionDateAndRoom;
    private List<String> speakersList = new ArrayList<>();

    public static void startActivity(Context context, ScheduleSession scheduleSession) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(PARAM_SESSION_ID, scheduleSession.getSessionId());
        intent.putExtra(PARAM_SESSION_START_DATE, scheduleSession.getStartTimestamp());
        intent.putExtra(PARAM_SESSION_END_DATE, scheduleSession.getEndTimestamp());
        intent.putExtra(PARAM_SESSION_ROOM, scheduleSession.getRoomId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDetailBinding activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        sessionId = getIntent().getIntExtra(PARAM_SESSION_ID, -1);
        session = AgendaRepository.getInstance().getSession(sessionId);

        sessionStartDateInMillis = getIntent().getLongExtra(PARAM_SESSION_START_DATE, -1);
        sessionEndDateInMillis = getIntent().getLongExtra(PARAM_SESSION_END_DATE, -1);

        final Room sessionRoom = AgendaRepository.getInstance().getRoom(
                getIntent().getIntExtra(PARAM_SESSION_ROOM, -1)
        );

        if (session == null) {
            // We have a problem !
            activityDetailBinding.sessionInformationsScrollView.setVisibility(View.GONE);
            activityDetailBinding.errorMessageTextView.setVisibility(View.VISIBLE);
            return;
        }

        final String sessionDate = DateUtils.formatDateRange(this, new Formatter(getResources().getConfiguration().locale), sessionStartDateInMillis, sessionEndDateInMillis, DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY | DateUtils.FORMAT_SHOW_TIME, null).toString();
        sessionDateAndRoom = sessionRoom != null && !TextUtils.isEmpty(sessionRoom.name) ? getString(R.string.sessionDateWithRoomPlaceholder, sessionDate, sessionRoom.name) : sessionDate;

        activityDetailBinding.sessionTitleTextView.setText(session.title);
        activityDetailBinding.sessionDateAndRoomTextView.setText(sessionDateAndRoom);
        activityDetailBinding.sessionDescriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
        activityDetailBinding.sessionDescriptionTextView.setText(session.description != null ?
                Html.fromHtml(session.description) : "");

        final int languageFullNameRes = session.getLanguageName();
        if (languageFullNameRes != 0) {
            activityDetailBinding.sessionLanguageChip.setChipText(getString(languageFullNameRes));
            activityDetailBinding.sessionLanguageChip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View view) {
                    if (BuildConfig.DEBUG) {
                        Log.d(DetailActivity.class.getName(), "User clicked on tag with content=" + session.language);
                    }
                }
            });
        } else {
            activityDetailBinding.sessionLanguageChip.setVisibility(View.GONE);
        }

        //TODO type of session
        activityDetailBinding.sessionTypeChip.setChipText(session.subtype);
        activityDetailBinding.sessionTypeChip.setOnChipClickListener(new OnChipClickListener() {
            @Override
            public void onChipClick(View view) {
                if (BuildConfig.DEBUG) {
                    Log.d(DetailActivity.class.getName(), "User clicked on tag with content=" + session.subtype);
                }
                // TODO: Use this for future filter feature
            }
        });

        final ViewGroup sessionSpeakerLayout = findViewById(R.id.sessionSpeakerLayout);
        if (session.speakers != null && session.speakers.length > 0) {
            activityDetailBinding.speakersTitleTextView.setText(getResources().getQuantityString(R.plurals.session_details_speakers, session.speakers.length));
            for (final int speakerID : session.speakers) {
                final Speaker speaker = AgendaRepository.getInstance().getSpeaker(speakerID);
                speakersList.add(speaker.getFullNameAndCompany());

                if (speaker == null) {
                    continue;
                }

                final DetailViewSpeakerInfoElementBinding speakerInfoElementBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.detail_view_speaker_info_element, null, false);
                speakerInfoElementBinding.speakerBio.setMovementMethod(LinkMovementMethod.getInstance());
                speakerInfoElementBinding.setSpeaker(speaker);

                setSpeakerSocialNetworkHandle(speaker, speakerInfoElementBinding);
                setSpeakerRibbons(speaker, speakerInfoElementBinding);

                sessionSpeakerLayout.addView(speakerInfoElementBinding.getRoot());
            }
        }

        setActionBar(session);
    }

    private void setSpeakerSocialNetworkHandle(Speaker speaker, DetailViewSpeakerInfoElementBinding speakerInfoElementBinding) {
        if (speaker.socialNetworkHandles != null && speaker.socialNetworkHandles.size() > 0) {
            for (final SocialNetworkHandle socialNetworkHandle : speaker.socialNetworkHandles) {
                if (socialNetworkHandle.networkType != SocialNetworkHandle.SocialNetworkType.Unknown) {
                    final SmallSocialImageBinding smallSocialImageBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.small_social_image, null, false);
                    smallSocialImageBinding.setSocialHandle(socialNetworkHandle);
                    smallSocialImageBinding.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (BuildConfig.DEBUG) {
                                Log.d(DetailActivity.class.getName(), "User clicked on social handle with name=" + socialNetworkHandle.networkType.name());
                            }
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(socialNetworkHandle.link)));
                        }
                    });
                    speakerInfoElementBinding.speakerSocialNetworkHandleLayout.addView(smallSocialImageBinding.getRoot());
                }
            }
        } else {
            speakerInfoElementBinding.speakerSocialNetworkHandleLayout.setVisibility(View.GONE);
        }
    }

    private void setSpeakerRibbons(Speaker speaker, DetailViewSpeakerInfoElementBinding speakerInfoElementBinding) {
        if (speaker.ribbonList != null && speaker.ribbonList.size() > 0) {
            for (final Ribbon ribbon : speaker.ribbonList) {
                if (ribbon.ribbonType != Ribbon.RibbonType.NONE) {
                    final SmallRibbonImageBinding smallRibbonImageBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.small_ribbon_image, null, false);
                    smallRibbonImageBinding.setRibbon(ribbon);
                    smallRibbonImageBinding.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (BuildConfig.DEBUG) {
                                Log.d(DetailActivity.class.getName(), "User clicked on ribbon with name=" + ribbon.ribbonType.name());
                            }
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ribbon.link)));
                        }
                    });
                    speakerInfoElementBinding.speakerRibbonLayout.addView(smallRibbonImageBinding.getRoot());
                }
            }
        } else {
            speakerInfoElementBinding.speakerRibbonLayout.setVisibility(View.GONE);
        }
    }

    private void setActionBar(Session session) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(session.title);
        }
    }

    private void changeSessionSelection(boolean select) {
        SessionSelector.getInstance().setSessionSelected(sessionId, select);
        invalidateOptionsMenu();

        if (select) {
            Toast.makeText(this, R.string.session_selected, Toast.LENGTH_SHORT).show();
            scheduleStarredSession();
        } else {
            Toast.makeText(this, R.string.session_deselected, Toast.LENGTH_SHORT).show();
            unScheduleSession();
        }
    }

    private void scheduleStarredSession() {
        Log.d("Detail", "Scheduling notification about session start. " +
                "start time : " + sessionStartDateInMillis + ", " +
                "end time : " + sessionEndDateInMillis);
        final Intent scheduleIntent = new Intent(
                SessionAlarmService.ACTION_SCHEDULE_STARRED_BLOCK,
                null, this, SessionAlarmService.class);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_START, sessionStartDateInMillis);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_END, sessionEndDateInMillis);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_ID, sessionId);
        startService(scheduleIntent);
    }

    private void unScheduleSession() {
        Log.d("Detail", "Unscheduling notification about session start. " +
                "start time : " + sessionStartDateInMillis + ", " +
                "end time : " + sessionEndDateInMillis);
        final Intent scheduleIntent = new Intent(
                SessionAlarmService.ACTION_UNSCHEDULE_UNSTARRED_BLOCK,
                null, this, SessionAlarmService.class);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_ID, sessionId);
        startService(scheduleIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        final MenuItem selectItem = menu.findItem(R.id.select);
        final MenuItem unSelectItem = menu.findItem(R.id.unSelect);
        if (selectItem != null && unSelectItem != null) {
            if (SessionSelector.getInstance().isSelected(sessionId)) {
                selectItem.setVisible(false);
                unSelectItem.setVisible(true);
            } else {
                unSelectItem.setVisible(false);
                selectItem.setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.select:
                changeSessionSelection(true);
                return true;
            case R.id.unSelect:
                changeSessionSelection(false);
                return true;
            case R.id.share:
                shareSession();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareSession() {
        String speakers = TextUtils.join(", ", speakersList);

        Intent shareSessionIntent = new Intent(Intent.ACTION_SEND);
        shareSessionIntent.putExtra(Intent.EXTRA_SUBJECT, session.title);
        shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
                String.format("%s (%s, %s, %s)", session.title, speakers, sessionDateAndRoom, getString(session.getLanguageName())));
        shareSessionIntent.setType("text/plain");
        startActivity(shareSessionIntent);
    }

}