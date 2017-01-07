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
;import com.it.nhozip.englisha_z.Presenter.PNAVLogic;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.View.viewMain.ViewMain;

/**
 * Created by huyen on 12/24/2016.
 */

public class FragmentIElts extends Fragment implements ViewMain {
    private RecyclerView ryMove;
    private PNAVLogic pnavLogic;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_hot, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        ryMove = (RecyclerView) view.findViewById(R.id.rVMove);
        pnavLogic = new PNAVLogic(ryMove, getContext(), this);
        pnavLogic.loadDataInRecView("IELTS");
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

