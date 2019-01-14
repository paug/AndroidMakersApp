package fr.paug.androidmakers.ui.fragment;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.databinding.VenueItemFragmentBinding;
import fr.paug.androidmakers.model.Venue;
import fr.paug.androidmakers.util.CustomTabUtil;

abstract class AbstractVenueFragment extends Fragment implements View.OnClickListener {

    private VenueItemFragmentBinding venueItemFragmentBinding;

    public AbstractVenueFragment() {
        // Required empty public constructor

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        venueItemFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.venue_item_fragment, container, false);
        venueItemFragmentBinding.setVenue(getVenueInformation());
        venueItemFragmentBinding.venueDirections.setText(Html.fromHtml(getVenueDescription()));
        venueItemFragmentBinding.venueLocateButton.setOnClickListener(this);
        return venueItemFragmentBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == venueItemFragmentBinding.venueLocateButton) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, getVenueCoordinatesUri());
                startActivity(intent);
            } catch (Exception e) {
                View view = getView();
                if (view != null) {
                    Snackbar.make(view, R.string.no_maps_app_found, Snackbar.LENGTH_SHORT).show();
                }

                // Open in Webview
                CustomTabUtil.openChromeTab(getContext(), "https://www.google.com/maps/?q="
                        + getVenueInformation().coordinates.replace(" ", ""));
            }
        }
    }

    protected abstract Uri getVenueCoordinatesUri();

    protected abstract Venue getVenueInformation();

    protected String getVenueDescription() {
        Locale fr = Locale.FRENCH;
        if (Locale.getDefault().getLanguage() == fr.getLanguage()) {
            return getVenueInformation().descriptionFr;
        } else {
            return getVenueInformation().description;
        }
    }

}