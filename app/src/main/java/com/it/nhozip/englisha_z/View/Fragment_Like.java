package com.it.nhozip.englisha_z.View;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.it.nhozip.englisha_z.Adapter.MoveAdapter;
import com.it.nhozip.englisha_z.Model.Move;
import com.it.nhozip.englisha_z.Presenter.PNAVLogic;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.View.viewMain.ViewMain;
import com.it.nhozip.englisha_z.database.DatabaseManager;

import java.util.ArrayList;


/**
 * Created by huyen on 9/17/2016.
 */
public class Fragment_Like extends Fragment implements ViewMain{

    private RecyclerView ryMove;
    private PNAVLogic pnavLogic;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_like, container, false);
        AdRequest adRequest = new AdRequest.Builder().build();

        NativeExpressAdView adView = (NativeExpressAdView)view. findViewById(R.id.adView);
        adView.loadAd(adRequest);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        ryMove= (RecyclerView) view.findViewById(R.id.rVMove);
        pnavLogic=new PNAVLogic(ryMove,getContext(),this);
        pnavLogic.loadDataOff();


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        pnavLogic.loadDataOff();
    }

    @Override
    public void onStart() {
        super.onStart();
    }




    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
