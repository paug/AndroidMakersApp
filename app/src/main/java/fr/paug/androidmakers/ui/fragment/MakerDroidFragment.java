package fr.paug.androidmakers.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;

/**
 * Created by Jade on 20/03/2018
 */

public class MakerDroidFragment extends Fragment implements AIListener {

    private static final String TAG = MakerDroidFragment.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.bot_layout)
    LinearLayout botLayout;

    @BindView(R.id.bot_button)
    ImageButton botButton;

    @BindView(R.id.bot_listening)
    ImageButton botListening;

    @BindView(R.id.bot_treating)
    ProgressBar botTreatingProgressBar;


    private final AIConfiguration config = new AIConfiguration("97ef1441bcd540038c4add623c6f9610",
        AIConfiguration.SupportedLanguages.English,
        AIConfiguration.RecognitionEngine.System);

    private AIService aiService;

    // Requesting permission to RECORD_AUDIO
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private int paddingDp;
    private LinearLayout.LayoutParams paramsQuestion;
    private LinearLayout.LayoutParams paramsAnswer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aiService = AIService.getService(this.getContext(), config);
        aiService.setListener(this);
        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makerdroid, container, false);
        setHasOptionsMenu(true);

        unbinder = ButterKnife.bind(this, view);

        ActivityCompat.requestPermissions(this.getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        botButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiService.startListening();
            }
        });

        botListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiService.stopListening();
            }
        });


        paddingDp = (int) getResources().getDimension(R.dimen.padding);

        paramsQuestion = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsQuestion.weight = 1.0f;
        paramsQuestion.gravity = Gravity.RIGHT;
        paramsQuestion.setMargins(0, paddingDp, 0, 0);

        paramsAnswer = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsAnswer.weight = 1.0f;
        paramsAnswer.gravity = Gravity.LEFT;
        paramsAnswer.setMargins(0, paddingDp, 0, 0);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        aiService.setListener(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResult(AIResponse aiResponse) {
        try {

            botButton.setVisibility(View.VISIBLE);
            botListening.setVisibility(View.GONE);
            botTreatingProgressBar.setVisibility(View.GONE);


            final Status status = aiResponse.getStatus();
            Log.i(TAG, "Status code: " + status.getCode());
            Log.i(TAG, "Status type: " + status.getErrorType());

            final Result result = aiResponse.getResult();
            Log.i(TAG, "Action: " + aiResponse.getResult().getAction());
            Log.i(TAG, "Resolved query: " + result.getResolvedQuery());

            final String speech = result.getFulfillment().getSpeech();
            Log.i(TAG, "Speech: " + speech);


            final Metadata metadata = result.getMetadata();
            if (metadata != null) {
                Log.i(TAG, "Intent id: " + metadata.getIntentId());
                Log.i(TAG, "Intent name: " + metadata.getIntentName());
            }


            addQuestionView(result.getResolvedQuery());

            // TODO all cases + clean up + export Strings
            if (aiResponse.getResult().getAction().equalsIgnoreCase("action.sessions.byfilter")) {
                // Get parameters
                String parameterString = "";

                if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                    for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                        parameterString += "[" + entry.getKey() + ": " + entry.getValue() + "] ";
                    }

                    addAnswerView("I got that!\n" + parameterString);
                    addAnswerView(treatQuestion(result.getParameters()));
                } else {
                    addAnswerView("I got that, but no params... Try again");
                }

            } else {
                addAnswerView("Bummer, I did not get that. Can you repeat please?");
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }
    }

    @Override
    public void onError(AIError aiError) {
        try {
            Log.i(TAG, "Result Error : " + aiError.getMessage());
            botButton.setVisibility(View.VISIBLE);
            botListening.setVisibility(View.GONE);
            botTreatingProgressBar.setVisibility(View.GONE);
            Toast.makeText(this.getContext(), "An error occured (" + aiError.getMessage() + ").\nPlease try again.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
            Toast.makeText(this.getContext(), "An error occured.\nPlease try again.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onAudioLevel(float v) {

    }

    @Override
    public void onListeningStarted() {
        Log.d(TAG, "onListeningStarted");
        botButton.setVisibility(View.GONE);
        botListening.setVisibility(View.VISIBLE);
        botTreatingProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onListeningCanceled() {
        Log.d(TAG, "onListeningCanceled");
        botButton.setVisibility(View.VISIBLE);
        botListening.setVisibility(View.GONE);
        botTreatingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onListeningFinished() {
        Log.d(TAG, "onListeningFinished");
        botButton.setVisibility(View.GONE);
        botListening.setVisibility(View.GONE);
        botTreatingProgressBar.setVisibility(View.VISIBLE);
        botTreatingProgressBar.animate();
    }


    private void addQuestionView(String text) {
        TextView tvQuestion = new TextView(this.getContext());
        tvQuestion.setText(text);
        tvQuestion.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_bot_question));
        tvQuestion.setLayoutParams(paramsQuestion);
        tvQuestion.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        botLayout.addView(tvQuestion);
    }

    private void addAnswerView(String text) {
        TextView tvAnswer = new TextView(this.getContext());
        tvAnswer.setText(text);
        tvAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_bot_answer));
        tvAnswer.setLayoutParams(paramsAnswer);
        tvAnswer.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        botLayout.addView(tvAnswer);
    }

    private String treatQuestion(HashMap<String, JsonElement> parameters) {

        List<Session> resultSessions = new ArrayList<>();

        try {
            if (parameters.get("language") != null) {
                String userLang = parameters.get("language").getAsString();

                for (ScheduleSlot slot : AgendaRepository.getInstance().getScheduleSlots()) {
                    Session session = AgendaRepository.getInstance().getSession(slot.sessionId);
                    if (getResources().getString(Session.getLanguageFullName(session.language)).equalsIgnoreCase(userLang)) {
                        resultSessions.add(session);
                    }
                }

                String returnedMsg = "I have " + resultSessions.size() + "/" + AgendaRepository.getInstance().getScheduleSlots().size() + " sessions " +
                    "in " + userLang;

                Log.d(TAG, returnedMsg);
                return returnedMsg;
            }

            return "I can only filter by language for now";

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return "An error occurred, please try again.";
        }
    }
}
