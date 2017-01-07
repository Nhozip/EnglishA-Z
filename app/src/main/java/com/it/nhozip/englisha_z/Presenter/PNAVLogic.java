package com.it.nhozip.englisha_z.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.it.nhozip.englisha_z.Adapter.MoveAdapter;
import com.it.nhozip.englisha_z.MainActivity;
import com.it.nhozip.englisha_z.Model.Move;
import com.it.nhozip.englisha_z.View.viewMain.ViewMain;
import com.it.nhozip.englisha_z.database.DatabaseManager;

import java.util.ArrayList;

/**
 * Created by huyen on 12/25/2016.
 */

public class PNAVLogic implements MainPresenter {
    private ViewMain viewMain;
    private DatabaseReference mDatabase;
    private MoveAdapter moveAdapter;
    private ArrayList<Move> moves=new ArrayList<>();
    private ArrayList<Object> lisMove;
    private Context context;
    private RecyclerView recyclerView;
    // The Native Express ad height.
    private static final int NATIVE_EXPRESS_AD_HEIGHT = 100;

    // The Native Express ad unit ID.
    private static final String AD_UNIT_ID1 = "ca-app-pub-3082814971751832/6956410503";
    private static final String AD_UNIT_ID2 = "ca-app-pub-3082814971751832/9317460909";


    public PNAVLogic(RecyclerView recyclerView, Context context, ViewMain viewMain) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.viewMain = viewMain;
    }

    @Override
    public void loadDataInRecView(String key) {
        viewMain.showProgress();

        mDatabase = FirebaseDatabase.getInstance().getReference().child(key);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lisMove = new ArrayList<Object>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Move move;
                    move = snapshot.getValue(Move.class);
                    lisMove.add(move);
                }
                Log.e("dtaa", lisMove.toString());
                if (isConnectWifiOr3G()) {
                    addNativeExpressAds();
                    setUpAndLoadNativeExpressAds(AD_UNIT_ID1);
                }
                recyclerView.setHasFixedSize(true);
                moveAdapter = new MoveAdapter(lisMove, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(moveAdapter);
                viewMain.hideProgress();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void loadDataERR() {

    }
    @Override
    public void loadDataOff() {
        viewMain.showProgress();
        lisMove =new DatabaseManager(context).getListMove();
        recyclerView.setHasFixedSize(true);
        moveAdapter = new MoveAdapter(lisMove, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(moveAdapter);
        viewMain.hideProgress();

    }

    public void addNativeExpressAds() {
        for (int i = 0; i <= lisMove.size(); i += MainActivity.ITEMS_PER_AD) {
            final NativeExpressAdView adView = new NativeExpressAdView(context);
            lisMove.add(i, adView);
        }
    }

    public boolean isConnectWifiOr3G() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

          //For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
           //For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        if (is3g || isWifi) {
            return true;
        } else {
            return false;
        }
    }

    public void setUpAndLoadNativeExpressAds(final String id) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {

                final float density = context.getResources().getDisplayMetrics().density;


                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                for (int i = 0; i <= lisMove.size(); i += MainActivity.ITEMS_PER_AD) {
                    final NativeExpressAdView adView =
                            (NativeExpressAdView) lisMove.get(i);
                    AdSize adSize = new AdSize(
                            (int) (recyclerView.getWidth() / density) - 10,
                            NATIVE_EXPRESS_AD_HEIGHT);
                    adView.setAdSize(adSize);
                    adView.setAdUnitId(id);
                }

                // Load the first Native Express ad in the items list.
                loadNativeExpressAd(0);
            }
        });
    }

    private void loadNativeExpressAd(final int index) {

        if (index >= lisMove.size()) {
            return;
        }

        Object item = lisMove.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + MainActivity.ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + MainActivity.ITEMS_PER_AD);
            }
        });

        // Load the Native Express ad.
        adView.loadAd(new AdRequest.Builder().build());
    }
}
