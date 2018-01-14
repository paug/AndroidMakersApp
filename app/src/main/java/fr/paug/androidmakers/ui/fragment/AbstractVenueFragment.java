package fr.paug.androidmakers.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.databinding.VenueItemFragmentBinding;
import fr.paug.androidmakers.model.VenueInformations;

/**
 * @author Adrien Vitti
 * @since 2018.01.14
 */

abstract class AbstractVenueFragment extends Fragment implements View.OnClickListener {

    private VenueItemFragmentBinding venueItemFragmentBinding;

    public AbstractVenueFragment() {
        // Required empty public constructor

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        venueItemFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.venue_item_fragment, container, false);
        venueItemFragmentBinding.setVenue(getVenueInformations());
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
                    Snackbar.make(view, "Error opening maps", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected abstract Uri getVenueCoordinatesUri();

    protected abstract VenueInformations getVenueInformations();
}
