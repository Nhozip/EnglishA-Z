package com.it.nhozip.englisha_z.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.it.nhozip.englisha_z.R;

/**
 * Created by huyen on 12/29/2016.
 */
public class FragmentMeo extends Fragment {

   private WebView mWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fm_meo_thi, container, false);
        AdRequest adRequest = new AdRequest.Builder().build();

        NativeExpressAdView adView = (NativeExpressAdView)view. findViewById(R.id.adView);
        adView.loadAd(adRequest);
        mWebView = (WebView) view.findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/meo.html");
        return view;
    }
}
