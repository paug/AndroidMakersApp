package fr.paug.androidmakers.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.PartnerGroup;
import fr.paug.androidmakers.model.Partners;
import fr.paug.androidmakers.util.WifiUtil;

public class AboutFragment extends Fragment {

    @BindView(R.id.about_layout) LinearLayout aboutLayout;
    @BindView(R.id.wifi_autoconnect_progress) View wifiConnectionProgress;
    @BindView(R.id.wifi_connect_button) AppCompatButton wifiConnectButton;
    private Unbinder unbinder;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);

        final Map<PartnerGroup.PartnerType, PartnerGroup> partners = AgendaRepository.getInstance().getPartners();

        if (partners != null) {
            final PartnerGroup goldSponsorGroup = partners.get(PartnerGroup.PartnerType.GoldSponsor);
            addPartnerTypeToView(goldSponsorGroup);
            final PartnerGroup silverSponsorGroup = partners.get(PartnerGroup.PartnerType.SilverSponsor);
            addPartnerTypeToView(silverSponsorGroup);
            final PartnerGroup otherSponsorGroup = partners.get(PartnerGroup.PartnerType.OtherSponsor);
            addPartnerTypeToView(otherSponsorGroup);
            final PartnerGroup mediaSponsorGroup = partners.get(PartnerGroup.PartnerType.Media);
            addPartnerTypeToView(mediaSponsorGroup);
            final PartnerGroup locationGroup = partners.get(PartnerGroup.PartnerType.Location);
            addPartnerTypeToView(locationGroup);
        }

        // listen to network state change
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getContext().registerReceiver(wifiStateChangedReceiver, intentFilter);

        return view;
    }

    @OnClick(R.id.twitter_user_button)
    void openTwitterUser() {
        Intent twitterIntent;
        try {
            // get the Twitter app if possible
            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=AndroidMakersFR"));
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/AndroidMakersFR"));
        }
        startActivity(twitterIntent);
    }

    @OnClick(R.id.twitter_hashtag_button)
    void openTwitterHashtag() {
        Intent twitterIntent;
        try {
            // get the Twitter app if possible
            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://search?query=%23AndroidMakersFR"));
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/search?q=%23AndroidMakersFR"));
        }
        startActivity(twitterIntent);
    }

    @OnClick(R.id.google_plus_button)
    void openGPlus() {
        Intent gplusIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.gplus)));
        startActivity(gplusIntent);
    }

    @OnClick(R.id.facebook_button)
    void openFacebookEvent() {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fbevent)));
        startActivity(facebookIntent);
    }

    @OnClick(R.id.youtube_button)
    void openYoutube() {
        Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.ytchannel)));
        startActivity(ytIntent);
    }

    @OnClick(R.id.wifi_connect_button)
    void connectToVenuesWifi() {
        final Context context = getContext();
        final int networkId = WifiUtil.getVenuesWifiNetworkId(context);
        if (networkId != -1) {
            if (WifiUtil.connectToWifi(context, networkId)) {
                wifiConnectButton.setVisibility(View.GONE);
                wifiConnectionProgress.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
            wifiConnectButton.setVisibility(View.INVISIBLE);
            wifiConnectionProgress.setVisibility(View.GONE);
        } else if (wifiConnectionProgress.getVisibility() == View.GONE) {
            // case where we are not currently trying to connect to the venue's wifi
            wifiConnectButton.setVisibility(View.VISIBLE);
            wifiConnectionProgress.setVisibility(View.GONE);
        } else {
            wifiConnectButton.setVisibility(View.GONE);
            wifiConnectionProgress.setVisibility(View.VISIBLE);
        }
    }

    private void addPartnerTypeToView(final PartnerGroup partnerGroup) {
        if (partnerGroup == null) {
            return;
        }
        final List<Partners> partnersList = partnerGroup.getPartnersList();
        if (partnersList != null && partnersList.size() > 0) {
            final LinearLayout partnersGroupLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.partners_group, null);
            final TextView partnerGroupHeader = (TextView) partnersGroupLinearLayout.findViewById(R.id.partners_title);
            partnerGroupHeader.setText(partnerGroup.getPartnerType().getName());
            aboutLayout.addView(partnersGroupLinearLayout);

            for (final Partners partner : partnersList) {
                final ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.partner, null);
                Glide.with(getContext())
                        .load("http://androidmakers.fr/img/partners/" + partner.getImageUrl())
                        .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        builder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(getContext(), Uri.parse(partner.getLink()));
                    }
                });
                partnersGroupLinearLayout.addView(imageView);
            }
        }
    }

}