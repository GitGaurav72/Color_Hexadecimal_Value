package dev.technologies.rgbtohexcolorconverter.telhexcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class Color_pick_wheel
        extends Fragment {
    SwitchCompat color_switch_strap;
    int color;
    private ColorPicker colorPicker;
    Handler handler;
    String hexColor;
    float[] hsv;
    String rgbString;
    View color_stripp_view;
    public static final String PREFS_NAME = "MyPrefsFile";
    private InterstitialAd mInterstitialAd;
    int totalClicks;
    LinearLayout my_ads_layout;


    private void requestNewInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.color_pick, viewGroup, false);
        colorPicker = (ColorPicker)view.findViewById(R.id.color_wheel);
        totalClicks = getContext().getSharedPreferences(PREFS_NAME,0).getInt("totalClicks", 0);
        final TextView textView = (TextView)view.findViewById(R.id.color_info1);
        final TextView textView2 = (TextView)view.findViewById(R.id.color_info2);
        final TextView textView3 = (TextView)view.findViewById(R.id.color_info3);
        final TextView textView5 = (TextView)view.findViewById(R.id.color_info4) ;
        color_switch_strap = (SwitchCompat)view.findViewById(R.id.color_button_strip);
        color_stripp_view = (View)view.findViewById(R.id.color_stripp_view);
        my_ads_layout = (LinearLayout)view.findViewById(R.id.my_ads_layout);
        AdView mAdView = (AdView) view.findViewById(R.id.my_adView);
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            my_ads_layout.setVisibility(View.VISIBLE);
            final AdRequest.Builder request = new AdRequest.Builder();
            mAdView.loadAd(request.build());
        }
        MobileAds.initialize(getContext(), (String)this.getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(this.getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){

            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        handler = new Handler();
        Runnable runnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             */
            public void run() {
                color = colorPicker.getColor();
                rgbString = "R: " + Color.red((int)color) + " G: " + Color.green((int)color) + " B: " + Color.blue((int)color);
                hsv = new float[3];
                Color.RGBToHSV((int)Color.red((int)color), (int)Color.green((int)color), (int)Color.blue((int)color), (float[])hsv);
                Color_pick_wheel colorpicker_fragment = Color_pick_wheel.this;
                Object[] arrobject = new Object[]{16777215 & color};
                colorpicker_fragment.hexColor = String.format((String)"#%06X", (Object[])arrobject);
                textView.setText((CharSequence)rgbString);
                textView2.setText("HEX Value : " + hexColor);
                TextView textView4 = textView3;
                StringBuilder stringBuilder = new StringBuilder().append(" H : ").append((int)hsv[0]).append(" S :");
                Object[] arrobject2 = new Object[]{Float.valueOf((float)(100.0f * hsv[1]))};
                StringBuilder stringBuilder2 = stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject2)).append("%").append(" V :");
                Object[] arrobject3 = new Object[]{Float.valueOf((float)(100.0f * hsv[2]))};
                textView4.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject3)).append("%").toString());
               // if (chbox_distext_color.isChecked()) {
                    textView.setTextColor(Color.parseColor((String)"#000000"));
                    textView2.setTextColor(Color.parseColor((String)"#000000"));
                    textView3.setTextColor(Color.parseColor((String)"#000000"));
                    textView5.setTextColor(Color.parseColor((String)"#000000"));

                    color_switch_strap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            totalClicks = 1 + totalClicks;
                            if (totalClicks % 2 == 0 && mInterstitialAd.isLoaded()) {
                                requestNewInterstitial();
                                mInterstitialAd.show();
                            }
                        }
                    });

                    if(color_switch_strap.isChecked())
                    {
                        color_stripp_view.setVisibility(View.VISIBLE);
                        color_stripp_view.setBackgroundColor(Color.parseColor(hexColor));

                    }
                    else
                    {
                        color_stripp_view.setVisibility(View.INVISIBLE);
                    }
                HashMap<String, String> color_namme = new HashMap<String, String>();
                color_namme.put("#000000", "Black");
                color_namme.put("#FFFFFF", "White");
                color_namme.put("#FF5B50", "Queen Red");
                color_namme.put("#FF393A", "Queen Dark Red");
                color_namme.put("#FF1322", "Dark Red");
                color_namme.put("#FF654E", "Fade Orange");
                color_namme.put("#FF2B52", "Baby Pink");
                color_namme.put("#FF75E0", "light pink");
                color_namme.put("#323AFF", "Dark Blue");
                color_namme.put("#39FF2D", "Baby Green");
                color_namme.put("#A7FF78", "Light Green");
                color_namme.put("#F7FF64", "Fade Yellow");
                color_namme.put("#FAFF4E", "Yellow");
                color_namme.put("#FFE73B", "Dark Yellow");
                color_namme.put("#EAFF39", "Lemon Lime");
                color_namme.put("#FEFF5F", "Fade Yellow");
                color_namme.put("#FF5EFA", "Dark Pink");
                color_namme.put("#FF2AF4", "Dark Dark Pink");
                color_namme.put("#FF4663", "Light Pink Red");
                color_namme.put("#46FF2A", "Light Dark Green");
                color_namme.put("#87FF68", "Green Holo");
                color_namme.put("#9EFFA0", "Tree Green");
                color_namme.put("#AEAEFF", "Light Purple");
                color_namme.put("#FFFE8B", "Holo Yellow");
                color_namme.put("#E3FF2C", "Lime Light Green");
                color_namme.put("#6AEBFF", "SkyBlue");
                for (Map.Entry<String, String> entry : color_namme.entrySet()) {
                    String key = (String) entry.getKey();
                    String thing = (String) entry.getValue();
                    if (hexColor.equals(key)) {
                        textView5.setText("Color Name : " + thing);
                        break;
                    }
                    else{
                        textView5.setText("Color Name : " + "NA");
                    }
                }

                handler.postDelayed((Runnable)this, 100L);
            }
        };
        this.handler.postDelayed(runnable, 100L);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onResume() {
        SharedPreferences sharedPreferences;
        int n;
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getContext().getSharedPreferences(PREFS_NAME,0).edit();
        editor.putInt("totalClicks", this.totalClicks);
        editor.commit();
    }

}
