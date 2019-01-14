package fr.paug.androidmakers.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.ui.activity.SessionDetailActivity;
import fr.paug.androidmakers.ui.adapter.ScheduleSession;

/**
 * Created by Jade on 20/03/2018
 */

public class MakerDroidFragment extends Fragment implements AIListener {

    private static final String TAG = MakerDroidFragment.class.getSimpleName();
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private AIConfiguration config;
    AIDataService aiDataService;
    ScrollView scroll;
    LinearLayout botLayout;
    ImageButton botMicro;
    ImageButton botListening;
    ImageButton botSend;
    ProgressBar botTreatingProgressBar;
    EditText editTextAsk;
    View separator;
    private AIService aiService;
    // Requesting permission to RECORD_AUDIO
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private int defaultPadding;
    private int largePadding;
    private LinearLayout.LayoutParams paramsQuestion;
    private LinearLayout.LayoutParams paramsAnswer;

    private List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);

        if (Locale.getDefault().getLanguage().equals(new Locale("fr").getLanguage())) {
            config = new AIConfiguration("97ef1441bcd540038c4add623c6f9610",
                    AIConfiguration.SupportedLanguages.French,
                    AIConfiguration.RecognitionEngine.System);
        } else {
            config = new AIConfiguration("97ef1441bcd540038c4add623c6f9610",
                    AIConfiguration.SupportedLanguages.English,
                    AIConfiguration.RecognitionEngine.System);
        }
        aiDataService = new AIDataService(config);
        aiService = AIService.getService(this.getContext(), config);
        aiService.setListener(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makerdroid, container, false);
        setHasOptionsMenu(true);

        scroll = view.findViewById(R.id.scroll);
        botLayout = view.findViewById(R.id.bot_layout);
        botMicro = view.findViewById(R.id.bot_button);
        botListening = view.findViewById(R.id.bot_listening);
        botSend = view.findViewById(R.id.bot_send);
        botTreatingProgressBar = view.findViewById(R.id.bot_treating);
        editTextAsk = view.findViewById(R.id.edit_text_ask);
        separator = view.findViewById(R.id.view_separator);

        ActivityCompat.requestPermissions(this.getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        botMicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                aiService.startListening();
            }
        });

        botListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiService.stopListening();
            }
        });

        botTreatingProgressBar.animate();

        botSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQuestion(editTextAsk.getText().toString());
            }
        });

        defaultPadding = (int) getResources().getDimension(R.dimen.default_padding);
        largePadding = (int) getResources().getDimension(R.dimen.large_padding);

        // layout params for the question bubble
        paramsQuestion = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsQuestion.weight = 1.0f;
        paramsQuestion.gravity = Gravity.RIGHT;
        paramsQuestion.setMargins(largePadding, 0, largePadding, largePadding);

        // layout params for the answer bubble
        paramsAnswer = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsAnswer.weight = 1.0f;
        paramsAnswer.gravity = Gravity.LEFT;
        paramsAnswer.setMargins(largePadding, 0, largePadding, largePadding);

        setEditTextActions();

        return view;
    }

    private void setEditTextActions() {
        // click on send
        editTextAsk.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendQuestion(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });

        editTextAsk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    // change icon to mic
                    displayButton(ButtonType.MIC);
                } else {
                    // change icon to send
                    displayButton(ButtonType.SEND);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final Handler handler = new Handler();
        editTextAsk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollToBottom();
                        }

                    }, 500);
                }
                return false;
            }
        });

        // for emulator
        editTextAsk.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            sendQuestion(editTextAsk.getText().toString());
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    private void sendQuestion(String text) {
        if (!text.isEmpty()) {
            hideKeyboard();
            editTextAsk.setText("");
            displayButton(ButtonType.TREATING);
            final AIRequest aiRequest = new AIRequest();
            aiRequest.setQuery(text);
            new AiTask().execute(aiRequest);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        aiService.setListener(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResult(AIResponse aiResponse) {
        processAiResponse(aiResponse);
    }

    @Override
    public void onError(AIError aiError) {
        displayButton(ButtonType.MIC);
        try {
            Log.i(TAG, "Result Error : " + aiError.getMessage());
            Toast.makeText(this.getContext(), "An error occurred (" + aiError.getMessage() + ").\nPlease try again.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
            Toast.makeText(this.getContext(), "An error occurred.\nPlease try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        // hide virtual keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(editTextAsk.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onAudioLevel(float v) {
    }

    @Override
    public void onListeningStarted() {
        Log.d(TAG, "onListeningStarted");
        displayButton(ButtonType.LISTENING);
    }

    @Override
    public void onListeningCanceled() {
        Log.d(TAG, "onListeningCanceled");
        displayButton(ButtonType.MIC);
    }

    @Override
    public void onListeningFinished() {
        Log.d(TAG, "onListeningFinished");
        displayButton(ButtonType.TREATING);
    }

    private void displayButton(ButtonType button) {
        botMicro.setVisibility((button == ButtonType.MIC) ? View.VISIBLE : View.GONE);
        botListening.setVisibility((button == ButtonType.LISTENING) ? View.VISIBLE : View.GONE);
        botTreatingProgressBar.setVisibility((button == ButtonType.TREATING) ? View.VISIBLE : View.GONE);
        botSend.setVisibility((button == ButtonType.SEND) ? View.VISIBLE : View.GONE);
    }

    private void scrollToBottom() {
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void addQuestionView(String text) {
        TextView tvQuestion = new TextView(this.getContext());
        tvQuestion.setText(text);
        tvQuestion.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_bot_question));
        tvQuestion.setLayoutParams(paramsQuestion);
        tvQuestion.setPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        botLayout.addView(tvQuestion);
        scrollToBottom();
    }

    private void addAnswerView(String text) {
        TextView tvAnswer = new TextView(this.getContext());
        tvAnswer.setText(text);
        tvAnswer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_bot_answer));
        tvAnswer.setLayoutParams(paramsAnswer);
        tvAnswer.setPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        botLayout.addView(tvAnswer);
        separator.requestFocus();
        scrollToBottom();
    }

    private void treatQuestion(JsonArray sessionIds) {

        List<ScheduleSlot> resultSlots = new ArrayList<>();
        List<JsonElement> ids = new ArrayList<>();
        Log.d(TAG, sessionIds.toString());

        for (ScheduleSlot slot : scheduleSlots) {
            for (JsonElement id : sessionIds) {
                if (slot.sessionId == id.getAsInt() && !ids.contains(id)) {
                    resultSlots.add(slot);
                    ids.add(id);
                }
            }
        }

        addAnswerView(getString(R.string.bot_answer_intro_sessions));
        addListView(resultSlots);
    }

    private void processAiResponse(AIResponse aiResponse) {
        try {

            displayButton(ButtonType.MIC);

            final Status status = aiResponse.getStatus();
            Log.i(TAG, "Status: " + status.getCode() + " ErrorType: " + status.getErrorType());

            final Result result = aiResponse.getResult();
            Log.i(TAG, "Action: " + aiResponse.getResult().getAction());
            Log.i(TAG, "Resolved query: " + result.getResolvedQuery());
            Log.i(TAG, "Params: " + result.getParameters());


            String speech = result.getFulfillment().getSpeech();
            Log.i(TAG, "Speech: " + speech);
            if (speech.isEmpty()) {
                speech = getString(R.string.bot_answer_empty);
            }

            final Metadata metadata = result.getMetadata();
            if (metadata != null) {
                Log.i(TAG, "Intent: id> " + metadata.getIntentId() + " name> " + metadata.getIntentName());
            }

            addQuestionView(result.getResolvedQuery());
            Map<String, JsonElement> data = result.getFulfillment().getData();

            try {
                assert metadata != null;
                if (metadata.getIntentName().equalsIgnoreCase("sessions.byfilter")) {
                    if (data.get("makerdroid") != null) {
                        JsonObject makerdroidData = data.get("makerdroid").getAsJsonObject();
                        JsonArray sessionsIds = makerdroidData.get("session_ids").getAsJsonArray();
                        if (sessionsIds.size() == 0) {
                            Log.d(TAG, "no sessions ids retrieved");
                            addAnswerView(speech);
                        } else {
                            treatQuestion(sessionsIds);
                        }
                    } else {
                        addAnswerView(speech);
                    }
                } else {
                    addAnswerView(speech);
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception getting data " + e.getMessage());
                addAnswerView(speech);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }
    }

    private void addCarouselView(List<ScheduleSlot> slots) {

        LinearLayout.LayoutParams defaultWrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

        HorizontalScrollView hScrollView = new HorizontalScrollView(getContext());
        hScrollView.setLayoutParams(defaultWrapParams);
        hScrollView.setPadding(0, 0, 0, largePadding);

        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(defaultWrapParams);
        ll.setPadding(largePadding, 0, largePadding, 0);


        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        viewParams.setMargins(defaultPadding, 0, defaultPadding, 0);


        for (ScheduleSlot slot : slots) {
            Session session = AgendaRepository.getInstance().getSession(slot.sessionId);
            TextView tvSession = new TextView(this.getContext());
            tvSession.setText(session != null ? session.title : "No session");
            tvSession.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_bot_answer));
            tvSession.setLayoutParams(viewParams);
            ll.addView(tvSession);
        }

        hScrollView.addView(ll);
        botLayout.addView(hScrollView);

    }

    @SuppressLint("SetTextI18n")
    private void addListView(List<ScheduleSlot> slots) {

        LinearLayout.LayoutParams defaultWrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);


        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(defaultWrapParams);
        ll.setPadding(largePadding, 0, largePadding, largePadding);


        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

        for (final ScheduleSlot slot : slots) {
            final Session session = AgendaRepository.getInstance().getSession(slot.sessionId);
            final Room sessionRoom = AgendaRepository.getInstance().getRoom(slot.room);

            TextView tvSession = new TextView(this.getContext());

            final String sessionDate = DateUtils.formatDateRange(
                getContext(),
                new Formatter(getResources().getConfiguration().locale),
                slot.startDate,
                slot.endDate,
                DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY | DateUtils.FORMAT_SHOW_TIME,
                null).toString();

            if (session != null) {
                tvSession.setText(
                    (session.title != null ? getString(R.string.bot_response_title) + " " + session.title : "") +
                        (session.experience != null && !session.experience.isEmpty() ? "\n" + getString(R.string.bot_answer_level) + " " + session.experience : "") +
                        (session.language != null && !session.language.isEmpty() ? "\n" + getString(R.string.bot_answer_lang) + " " + session.language : "") +
                        (sessionRoom != null && sessionRoom.name != null && !sessionRoom.name.isEmpty() ? "\n" + sessionRoom.name : "") +
                        (sessionDate != null && sessionRoom != null && sessionRoom.name != null && !sessionRoom.name.isEmpty() ? "\n" + getString(R.string.bot_answer_date) + " " + sessionDate : "")
                );
            }


            tvSession.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_bot_list));
            tvSession.setLayoutParams(contentParams);
            tvSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScheduleSession scheduleSession = new ScheduleSession(slot, session.title, session.language);
                    SessionDetailActivity.startActivity(getContext(), scheduleSession);
                }
            });
            ll.addView(tvSession);
        }

        botLayout.addView(ll);

    }

    private enum ButtonType {
        MIC, LISTENING, TREATING, SEND
    }

    private class AiTask extends AsyncTask<AIRequest, Void, AIResponse> {
        @Override
        protected AIResponse doInBackground(AIRequest... requests) {
            final AIRequest request = requests[0];
            try {
                return aiDataService.request(request);
            } catch (AIServiceException e) {
                Log.e(TAG, "Exception " + e.getMessage());
                Toast.makeText(MakerDroidFragment.this.getContext(), "An error occurred.\nPlease try again.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(AIResponse aiResponse) {
            if (aiResponse != null) {
                processAiResponse(aiResponse);
            }
        }
    }
}
