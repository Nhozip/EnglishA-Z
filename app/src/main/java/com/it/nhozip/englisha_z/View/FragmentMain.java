package com.it.nhozip.englisha_z.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.config.Var;

/**
 * Created by huyen on 12/26/2016.
 */
public class FragmentMain extends Fragment implements View.OnClickListener{
    private AdRequest adRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fm_home, container, false);
        adRequest = new AdRequest.Builder().build();


        NativeExpressAdView adView1 = (NativeExpressAdView)view. findViewById(R.id.adView1);
        adView1.loadAd(adRequest);
        view.findViewById(R.id.toeic).setOnClickListener(this);
        view.findViewById(R.id.ielts).setOnClickListener(this);
        view.findViewById(R.id.luyenphatAm).setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()){
            case R.id.toeic:
                Var.with=1;
                break;
            case R.id.ielts:
                Var.with=2;
                break;
            case  R.id.luyenphatAm:
                Var.with=4;
                break;
        }
        try {
            FragmentManager fragmentManager =  getFragmentManager();
            fragment = FragmentHot.class.newInstance();
            fragmentManager.beginTransaction().replace(R.id.fContent, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
