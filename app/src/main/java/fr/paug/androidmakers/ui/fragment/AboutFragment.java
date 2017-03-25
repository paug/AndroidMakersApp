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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    private Unbinder unbinder;

    @BindView(R.id.wifi_autoconnect_progress)
    View wifiConnectionProgress;

    @BindView(R.id.wifi_autoconnect_view)
    View wifiConnectionView;

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

        Map<PartnerGroup.PartnerType, PartnerGroup> partners = AgendaRepository.getInstance().getPartners();
        for (PartnerGroup.PartnerType partnerType : partners.keySet()) {
            Log.d("AboutFragment", partnerType.toString() + ", " + partners.get(partnerType).getPartnersList().toString());

            LinearLayout partnersGroupLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.partners_group, null);
            TextView textView = (TextView) partnersGroupLinearLayout.findViewById(R.id.partners_title);
            textView.setText(partnerType.name());
            aboutLayout.addView(partnersGroupLinearLayout);

            for (final Partners partner : partners.get(partnerType).getPartnersList()) {
                ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.partner, null);
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

        // listen to network state change
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getContext().registerReceiver(wifiStateChangedReceiver, intentFilter);

        return view;
    }

    private void onConnectivityChanged() {
        if (WifiUtil.isCurrentlyConnectedToVenuesWifi(getContext())) {
            wifiConnectionView.setVisibility(View.INVISIBLE);
            wifiConnectionProgress.setVisibility(View.GONE);
        } else if (wifiConnectionProgress.getVisibility() == View.GONE) {
            // case where we are not currently trying to connect to the venue's wifi
            wifiConnectionView.setVisibility(View.VISIBLE);
            wifiConnectionProgress.setVisibility(View.GONE);
        } else {
            wifiConnectionView.setVisibility(View.GONE);
            wifiConnectionProgress.setVisibility(View.VISIBLE);
        }
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

    @OnClick({R.id.wifi_connect_img, R.id.wifi_connect_text})
    void connectToVenuesWifi() {
        final Context context = getContext();
        final int networkId = WifiUtil.getVenuesWifiNetworkId(context);
        if (networkId != -1) {
            wifiConnectionView.setVisibility(View.GONE);
            wifiConnectionProgress.setVisibility(View.VISIBLE);
            WifiUtil.connectToWifi(context, networkId);
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
}