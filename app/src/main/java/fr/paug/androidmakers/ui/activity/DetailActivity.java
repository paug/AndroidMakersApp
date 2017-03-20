package fr.paug.androidmakers.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.model.Speaker;
import fr.paug.androidmakers.ui.view.AgendaView;

/**
 * Created by stan on 19/03/2017.
 */

public class DetailActivity
    extends AppCompatActivity
{

  private static final String PARAM_SESSION_ID = "param_session_id";
  private static final String PARAM_SESSION_START_DATE = "param_session_start_date";
  private static final String PARAM_SESSION_END_DATE = "param_session_end_date";

  public static void startActivity(Context context, AgendaView.Item item)
  {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(PARAM_SESSION_ID, item.getSessionId());
    intent.putExtra(PARAM_SESSION_START_DATE, item.getStartTimestamp());
    intent.putExtra(PARAM_SESSION_END_DATE, item.getEndTimestamp());
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    final Session session = AgendaRepository.getInstance().getSession(
        getIntent().getIntExtra(PARAM_SESSION_ID, -1)
    );

    if (session == null)
    {
      // We have a problem !
      final TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
      final View sessionInformations = findViewById(R.id.sessionInformations);
      sessionInformations.setVisibility(View.GONE);
      errorMessage.setVisibility(View.VISIBLE);
      return;
    }

    final TextView sessionTitle = (TextView) findViewById(R.id.sessionTitle);
    final TextView sessionDescription = (TextView) findViewById(R.id.sessionDescription);
    final TextView sessionLanguage = (TextView) findViewById(R.id.sessionLanguage);
    final TextView sessionType = (TextView) findViewById(R.id.sessionType);

    sessionTitle.setText(session.title);
    sessionDescription.setText(Html.fromHtml(session.description));
    sessionLanguage.setText(session.language);
    sessionType.setText(session.subtype);

    final ViewGroup sessionSpeakerLayout = (ViewGroup) findViewById(R.id.sessionSpeakerLayout);
    if (session.speakers != null && session.speakers.length > 0)
    {
      for (int speakerID : session.speakers)
      {
        final Speaker speaker = AgendaRepository.getInstance().getSpeaker(speakerID);
        if (speaker != null)
        {
          final View speakerView = getLayoutInflater().inflate(R.layout.detail_view_speaker_info_element, null, false);
          final ImageView speakerAvatar = (ImageView) speakerView.findViewById(R.id.speakerAvatar);
          final TextView speakerNameAndCompany = (TextView) speakerView.findViewById(R.id.speakerName);
          final TextView speakerBio = (TextView) speakerView.findViewById(R.id.speakerBio);

          speakerNameAndCompany.setText(speaker.name + " " + speaker.surname + ", " + speaker.company);
          speakerBio.setText(Html.fromHtml(speaker.bio));
          sessionSpeakerLayout.addView(speakerView);
        }
      }
    }
  }
}
