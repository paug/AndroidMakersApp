package fr.paug.androidmakers.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Session;

/**
 * Created by stan on 19/03/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String PARAM_SESSION_ID = "param_session_id";

    public static void startActivity(Context context, int sessionId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(PARAM_SESSION_ID, sessionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        TextView textView = (TextView) findViewById(R.id.title_textview);

        Session session = AgendaRepository.getInstance().getSession(
                getIntent().getIntExtra(PARAM_SESSION_ID, -1)
        );

        textView.setText(session == null ? "Session not found" : session.title);
    }
}
