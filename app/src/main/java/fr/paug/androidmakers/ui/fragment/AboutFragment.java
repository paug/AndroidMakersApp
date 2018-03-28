package fr.paug.androidmakers.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.databinding.FragmentAboutBinding;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.PartnerGroup;
import fr.paug.androidmakers.model.Partners;
import fr.paug.androidmakers.util.CustomTabUtil;
import fr.paug.androidmakers.util.WifiUtil;

public class AboutFragment extends Fragment implements View.OnClickListener {

    private FragmentAboutBinding fragmentAboutBinding;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAboutBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_about, container, false);

        final Map<PartnerGroup.PartnerType, PartnerGroup> partners = AgendaRepository.getInstance().getPartners();

        if (partners != null) {
            for (PartnerGroup.PartnerType partnerType : PartnerGroup.PartnerType.values()) {
                PartnerGroup sponsorGroup = partners.get(partnerType);
                addPartnerTypeToView(sponsorGroup);
            }
        }

        // Listen to network state change
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getContext().registerReceiver(wifiStateChangedReceiver, intentFilter);

        fragmentAboutBinding.twitterUserButton.setOnClickListener(this);
        fragmentAboutBinding.twitterHashtagButton.setOnClickListener(this);
        fragmentAboutBinding.googlePlusButton.setOnClickListener(this);
        fragmentAboutBinding.facebookButton.setOnClickListener(this);
        fragmentAboutBinding.youtubeButton.setOnClickListener(this);
        fragmentAboutBinding.wifiConnectButton.setOnClickListener(this);

        return fragmentAboutBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == fragmentAboutBinding.twitterUserButton) {
            openTwitterUser();
        } else if (v == fragmentAboutBinding.twitterHashtagButton) {
            openTwitterHashtag();
        } else if (v == fragmentAboutBinding.googlePlusButton) {
            openGooglePlus();
        } else if (v == fragmentAboutBinding.facebookButton) {
            openFacebookEvent();
        } else if (v == fragmentAboutBinding.youtubeButton) {
            openYoutube();
        } else if (v == fragmentAboutBinding.wifiConnectButton) {
            connectToVenuesWifi();
        }
    }

    void openTwitterUser() {
        Intent twitterIntent;
        try {
            // get the Twitter app if possible
            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            twitterIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=" + getString(R.string.twitter_user_name)));
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            twitterIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/" + getString(R.string.twitter_user_name)));
        }
        startActivity(twitterIntent);
    }

    void openTwitterHashtag() {
        Intent twitterIntent;
        try {
            // get the Twitter app if possible
            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            twitterIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://search?query=%23" + getString(R.string.twitter_hashtag_for_query)));
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            twitterIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/search?q=%23" + getString(R.string.twitter_hashtag_for_query)));
        }
        startActivity(twitterIntent);
    }

    void openGooglePlus() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.gplus))));
    }

    void openFacebookEvent() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fbevent))));
    }

    void openYoutube() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.ytchannel))));
    }

    void connectToVenuesWifi() {
        final Context context = getContext();
        final int networkId = WifiUtil.getVenuesWifiNetworkId(context);
        if (networkId != -1) {
            if (WifiUtil.connectToWifi(context, networkId)) {
                fragmentAboutBinding.wifiConnectButton.setVisibility(View.GONE);
                fragmentAboutBinding.wifiAutoconnectProgress.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(wifiStateChangedReceiver);
    }

    private final BroadcastReceiver wifiStateChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onConnectivityChanged();
        }
    };

    private void onConnectivityChanged() {
        if (WifiUtil.isCurrentlyConnectedToVenuesWifi(getContext())) {
            fragmentAboutBinding.wifiConnectButton.setVisibility(View.INVISIBLE);
            fragmentAboutBinding.wifiAutoconnectProgress.setVisibility(View.GONE);
        } else if (fragmentAboutBinding.wifiAutoconnectProgress.getVisibility() == View.GONE) {
            // case where we are not currently trying to connect to the venue's wifi
            fragmentAboutBinding.wifiConnectButton.setVisibility(View.VISIBLE);
            fragmentAboutBinding.wifiAutoconnectProgress.setVisibility(View.GONE);
        } else {
            fragmentAboutBinding.wifiConnectButton.setVisibility(View.GONE);
            fragmentAboutBinding.wifiAutoconnectProgress.setVisibility(View.VISIBLE);
        }
    }

    private void addPartnerTypeToView(final PartnerGroup partnerGroup) {
        if (partnerGroup == null) {
            return;
        }

        final List<Partners> partnersList = partnerGroup.getPartnersList();
        if (partnersList != null && partnersList.size() > 0) {
            final LinearLayout partnersGroupLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.partners_group, null);
            final TextView partnerGroupHeader = partnersGroupLinearLayout.findViewById(R.id.partners_title);
            partnerGroupHeader.setText(partnerGroup.getPartnerType().getName());

            final LinearLayout partnerLogoLayout = partnersGroupLinearLayout.findViewById(R.id.partners_layout);
            final int partnerLogoSizePriority = partnerGroup.getPartnerType().getPartnerLogoSizePriority();
            for (int index = 0; index < partnersList.size(); index += partnerLogoSizePriority) {
                final LinearLayout partnerRow = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.partner_row, null);
                partnerRow.setWeightSum(partnerLogoSizePriority);

                if (partnerLogoSizePriority > 0) {
                    final ImageView partner1 = partnerRow.findViewById(R.id.partner1);
                    setLogoInfo(partner1, partnersList.get(index));
                    partner1.setContentDescription(partnersList.get(index).getName());
                }

                if (partnerLogoSizePriority > 1 && partnersList.size() > index + 1) {
                    final ImageView partner2 = partnerRow.findViewById(R.id.partner2);
                    setLogoInfo(partner2, partnersList.get(index + 1));
                    partner2.setContentDescription(partnersList.get(index).getName());
                }

                if (partnerLogoSizePriority > 2 && partnersList.size() > index + 2) {
                    final ImageView partner3 = partnerRow.findViewById(R.id.partner3);
                    setLogoInfo(partner3, partnersList.get(index + 2));
                    partner3.setContentDescription(partnersList.get(index).getName());
                }

                partnerLogoLayout.addView(partnerRow);
            }

            fragmentAboutBinding.sponsorsLayout.addView(partnersGroupLinearLayout);
        }
    }

    private void setLogoInfo(final ImageView partnerLogo, final Partners partner) {
        partnerLogo.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load("http://androidmakers.fr/img/partners/" + partner.getImageUrl())
                .into(partnerLogo);
        partnerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabUtil.openChromeTab(getContext(), partner.getLink());
            }
        });
    }

}