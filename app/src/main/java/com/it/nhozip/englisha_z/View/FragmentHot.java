package com.it.nhozip.englisha_z.View;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.it.nhozip.englisha_z.MainActivity;
import com.it.nhozip.englisha_z.Presenter.PNAVLogic;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.View.viewMain.ViewMain;
import com.it.nhozip.englisha_z.config.Var;

/**
 * Created by huyen on 12/23/2016.
 */

public class FragmentHot extends Fragment implements ViewMain {
    private RecyclerView ryMove;
    private PNAVLogic pnavLogic;
    private ProgressBar progressBar;
    private static final int NATIVE_EXPRESS_AD_HEIGHT = 150;
    // The Native Express ad unit ID.
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1072772517";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_hot, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        progressBar.getIndeterminateDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        ryMove= (RecyclerView) view.findViewById(R.id.rVMove);
        pnavLogic=new PNAVLogic(ryMove,getContext(),this);
        if(Var.with==1)
        pnavLogic.loadDataInRecView("toeic");
        if(Var.with==2)  pnavLogic.loadDataInRecView("IELTS");
        if(Var.with==4) pnavLogic.loadDataInRecView("phatAm");
        MainActivity.dem=0;
        return view;
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
