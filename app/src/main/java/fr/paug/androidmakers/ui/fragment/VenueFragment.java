package fr.paug.androidmakers.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.paug.androidmakers.R;

public class VenueFragment extends Fragment {

    Button locateOnMapButton;
    private static final String COORDINATES_URI = "geo:48.834851, 2.386445?q=" + Uri.encode("Les salons de l'aveyron");

    public VenueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_venue, container, false);

        locateOnMapButton = (Button) view.findViewById(R.id.locate_button);
        locateOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapsLocation();
            }
        });

        return view;
    }

    void openMapsLocation() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(COORDINATES_URI));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            View view = getView();
            if (view != null) {
                Snackbar.make(view, "Error", Snackbar.LENGTH_SHORT).show();
            }
        }
    }


}
