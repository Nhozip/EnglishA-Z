package com.it.nhozip.englisha_z.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.it.nhozip.englisha_z.Adapter.MoveAdapter;
import com.it.nhozip.englisha_z.Model.Move;
import com.it.nhozip.englisha_z.Model.Note;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.config.Var;
import com.it.nhozip.englisha_z.database.DatabaseManager;

/**
 * Created by huyen on 12/24/2016.
 */

public class ActivityDetailMove extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,View.OnClickListener{


    private static final int RECOVERY_REQUEST = 1;
    private Move move;
    private TextView txtName, txtActor;
    private YouTubePlayerView youTubeView;
    public static final String YOUTUBE_API_KEY = "AIzaSyApPFbXkbX-FCNciKqVgdJF06zol8bpV-0";
    AdRequest adRequest;
    private FloatingActionButton fbLike,fbShare;
    private DatabaseManager databaseManager;
    private EditText edtContent;
    private Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        databaseManager=new DatabaseManager(this);
        move = (Move)getIntent().getSerializableExtra(MoveAdapter.MOVE_OBJ);

        setInfor();


        adRequest = new AdRequest.Builder().build();

        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);
        adView.loadAd(adRequest);

        try{
            youTubeView.initialize(YOUTUBE_API_KEY, this);
        }catch (Exception e){

        }
        fbLike.setOnClickListener(this);
        fbShare.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    private void setInfor() {
        txtActor.setText(move.getActor());
        txtName.setText(move.getName());
        edtContent.setText(databaseManager.getNote(move.getThumbi_move()));
    }

    private void initView() {

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        txtName = (TextView) findViewById(R.id.txtName);
        txtActor = (TextView) findViewById(R.id.txtActor);
        fbLike= (FloatingActionButton) findViewById(R.id.fablike);
        fbShare= (FloatingActionButton) findViewById(R.id.fabshare);
        edtContent= (EditText) findViewById(R.id.edtContent);
        btnOk= (Button) findViewById(R.id.btnOK);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(move.getThumbi_move());
        }

    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            Toast.makeText(this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.fablike:
                if(move.getThumbi_move().compareTo(databaseManager.getNotIMG(move.getThumbi_move()))!=0){

                    databaseManager.insertMovve(move);
                    Toast.makeText(getApplicationContext(),"Đã Thêm vào danh sách thích",Toast.LENGTH_LONG).show();
                    Log.e("Data",databaseManager.getListMove().toString());
                }else {
                    databaseManager.dellNote(move.getThumbi_move());
                    Toast.makeText(getApplicationContext(),"Đã xóa khỏi danh sách thích",Toast.LENGTH_LONG).show();
                }
                break;
            case  R.id.fabshare:
                Var.shareFB(this,"https://www.youtube.com/watch?v="+move.getThumbi_move()+"     " +
                        "\n Chia sẻ từ ứng dụng English E-Z " +
                        "\n Link app " +
                        "\n"+"http://play.google.com/store/apps/details?id=" + this.getPackageName());
                break;
            case  R.id.btnOK:
                try{
                    databaseManager.dellNotee(move.getThumbi_move());

                    databaseManager.insertNote(new Note(move.getThumbi_move(),edtContent.getText().toString().trim()));

                    Toast.makeText(this,"Đã lưu !",Toast.LENGTH_LONG).show();
                    Log.e("Data",databaseManager.getListNote().toString());
                }catch (Exception e){
                    Toast.makeText(this,"Có lỗi !",Toast.LENGTH_LONG).show();
                }

                break;


        }
    }
}
