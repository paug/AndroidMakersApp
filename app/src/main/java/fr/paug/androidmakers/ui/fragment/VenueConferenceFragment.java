package fr.paug.androidmakers.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.paug.androidmakers.R;

public class VenueConferenceFragment extends Fragment {

    Unbinder unbinder;
    private static final String COORDINATES_URI = "geo:48.834851, 2.386445?q=" + Uri.encode("Les Salons De L'aveyron");

    public VenueConferenceFragment() {
        // Required empty public constructor

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.venue_conference, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.venue_conference_locate)
    void openMapsLocation() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(COORDINATES_URI));
            startActivity(intent);
        } catch (Exception e) {
            View view = getView();
            if (view != null) {
                Snackbar.make(view, "Error opening maps", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}