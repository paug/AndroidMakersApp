package fr.paug.androidmakers.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;

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
import fr.paug.androidmakers.model.Session;

/**
 * Created by Jade on 20/03/2018
 */

public class MakerDroidFragment extends Fragment implements AIListener{

    private static final String TAG = MakerDroidFragment.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.bot_layout)
    LinearLayout botLayout;

    @BindView(R.id.bot_button)
    Button botButton;

    private final AIConfiguration config = new AIConfiguration("97ef1441bcd540038c4add623c6f9610",
        AIConfiguration.SupportedLanguages.English,
        AIConfiguration.RecognitionEngine.System);

    private AIService aiService;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


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
        unbinder = ButterKnife.bind(this, view);

//        Session session = AgendaRepository.getInstance().getSession(47);
//
//        if (session != null) {
//            TextView tv = new TextView(this.getContext());
//            tv.setText(session.title);
//            botLayout.addView(tv);
//        }

        ActivityCompat.requestPermissions(this.getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        botButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiService.startListening();
            }
        });

//        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }


    @Override
    public void onResult(AIResponse aiResponse) {
        try {


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

            // Get parameters
            String parameterString = "";
            if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                    parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
                }
            }


            TextView tv = new TextView(this.getContext());
            tv.setText("Query:" + result.getResolvedQuery() +
                "\nAction: " + result.getAction() +
                "\nParameters: " + parameterString);

            botLayout.addView(tv);

            // Show results in TextView.




        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }


    }

    @Override
    public void onError(AIError aiError) {
        try {
            Log.i(TAG, "Result: " + aiError.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }
    }

    @Override
    public void onAudioLevel(float v) {

    }

    @Override
    public void onListeningStarted() {
        Log.d(TAG, "onListeningStarted");
    }

    @Override
    public void onListeningCanceled() {
        Log.d(TAG, "onListeningCanceled");
    }

    @Override
    public void onListeningFinished() {
        Log.d(TAG, "onListeningFinished");
    }
}
