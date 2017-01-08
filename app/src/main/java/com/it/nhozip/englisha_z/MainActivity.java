package com.it.nhozip.englisha_z;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.it.nhozip.englisha_z.View.FragmentMain;
import com.it.nhozip.englisha_z.View.FragmentMeo;
import com.it.nhozip.englisha_z.View.Fragment_Like;
import com.it.nhozip.englisha_z.config.Var;
import com.it.nhozip.englisha_z.widgets.AutoScrollPagerFragment;
import com.it.nhozip.englisha_z.widgets.TextFragment;
import com.thebluealliance.spectrum.SpectrumDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static final int ITEMS_PER_AD = 5;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ViewPager contentPager;
    private CircleImageView imgAvata;
    private TextView txtName;
    private CallbackManager callbackManager;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private static JSONObject profile_pic_data, profile_pic_url;
    private String key_food;
    public static int dem = 0;
    private LinearLayout bg_nav_header;
    private NavigationView navigationView;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_main);
        initView();


//        AdRequest  adRequest = new AdRequest.Builder().build();
//
//        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
//        adView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ads_click_theme));
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                loadInterstitialAd();
            }
        });
        loadInterstitialAd();
        setAuoNext();
        setInfor();
        setFragmentDefaut();




        //KeyHash();

    }

    private void setAuoNext() {
        contentPager.setOffscreenPageLimit(2);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        contentPager.setAdapter(adapter);
    }


    private void setInfor() {
        if (Var.loginNoFirst(this)) {
            try {
                JSONObject response = new JSONObject(Var.getDataOFFFB(this));
                txtName.setText(response.get("name").toString());
                profile_pic_data = new JSONObject(response.get("picture").toString());
                profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
                Glide.with(getApplicationContext()).load(profile_pic_url.getString("url"))
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgAvata);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setFragmentDefaut() {
        Fragment fragment = null;
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = FragmentMain.class.newInstance();
            fragmentManager.beginTransaction().replace(R.id.fContent, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadInterstitialAd() {
        if (mInterstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }

    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set Color default
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#444444")));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Var.getColor(this))));
        collapsingToolbarLayout.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Var.getColor(this))));

        contentPager = (ViewPager) findViewById(R.id.pager_hot);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        View hView = navigationView.getHeaderView(0);
        imgAvata = (CircleImageView) hView.findViewById(R.id.imgAvata);
        txtName = (TextView) hView.findViewById(R.id.txtName);
        bg_nav_header = (LinearLayout) hView.findViewById(R.id.bg_nav_header);
        imgAvata.setOnClickListener(this);
        //  navigationView.setItemIconTintList(null);
        bg_nav_header.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Var.getColor(this))));

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    protected void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onBackPressed() {

        setFragmentDefaut();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
        dem++;
        if (dem < 2) {
            Toast.makeText(this, "Click 2 lần để thoát", Toast.LENGTH_LONG).show();
        } else super.onBackPressed();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (id) {

            case R.id.nav_home:
                key_food = "Home";
                collapsingToolbarLayout.setVisibility(View.VISIBLE);
                fragmentClass = FragmentMain.class;
                break;

            case R.id.nav_theme:
                key_food = "Theme";
                mInterstitialAd.show();


                new SpectrumDialog.Builder(this)
                        .setColors(R.array.demo_colors)
                        .setSelectedColorRes(R.color.colorAccent)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {

                                    Var.setColor(getApplicationContext(), "#" + Integer.toHexString(color).toUpperCase());
                                    bg_nav_header.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(color).toUpperCase())));
                                    collapsingToolbarLayout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(color).toUpperCase())));
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(color).toUpperCase())));

                                } else {

                                    Toast.makeText(getApplicationContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).build().show(getSupportFragmentManager(), "dialog_demo_1");
                //  findViewById(R.id.collapsing_toolbar).setVisibility(View.GONE);

                break;

            case R.id.nav_meo:
                collapsingToolbarLayout.setVisibility(View.GONE);
                mInterstitialAd.show();
                key_food = "Mẹo";
                // findViewById(R.id.collapsing_toolbar).setVisibility(View.GONE);
                fragmentClass = FragmentMeo.class;
                break;
            case R.id.nav_like:
                collapsingToolbarLayout.setVisibility(View.GONE);
                key_food = "Yêu thích";
                // findViewById(R.id.collapsing_toolbar).setVisibility(View.GONE);
                fragmentClass = Fragment_Like.class;
                break;
            case R.id.nav_share:
                Var.shareFB(this, "http://play.google.com/store/apps/details?id=" + this.getPackageName());
                break;
            case R.id.nav_send:
                Var.yKien(this);
                break;
            case R.id.nav_rate:
                Var.rateApp(this);
                break;
            case R.id.nav_khac:
                Var.initFB();
                Var.inviteFriendFB(callbackManager,this);
                break;

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fContent, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(key_food);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void KeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.it.nhozip.englisha_z", PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View v) {
        if (Var.loginNoFirst(this)) {
            Toast.makeText(getApplicationContext(), "Đã đăng nhập", Toast.LENGTH_LONG).show();
        } else {
            FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {

                            // getUserInfo(loginResult, context, activity);

                            GraphRequest data_request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(
                                                JSONObject json_object,
                                                GraphResponse response) {
                                            try {
                                                txtName.setText(json_object.get("name").toString());
                                                profile_pic_data = new JSONObject(json_object.get("picture").toString());
                                                profile_pic_url = new JSONObject(profile_pic_data.getString("data"));

                                                Glide.with(getApplicationContext()).load(profile_pic_url.getString("url"))
                                                        .thumbnail(0.5f)
                                                        .crossFade()
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .into(imgAvata);
                                                Var.setdataOff(getApplicationContext(), json_object.toString());

                                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), "Lỗi đồng bộ", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            Bundle permission_param = new Bundle();
                            permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
                            data_request.setParameters(permission_param);
                            data_request.executeAsync();

                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(getApplicationContext(), "Login Cancel", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(FacebookException exception) {

                            Toast.makeText(getApplicationContext(), "Kết nối mạng để đăng nhập", Toast.LENGTH_LONG).show();
                            Log.e("ERR", exception.toString());
                        }
                    });

        }

    }
    public  class MyPagerAdapter extends FragmentPagerAdapter {
        private  int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Log.e("XXXXXXXXX1", position + "");
                return new AutoScrollPagerFragment();

            }
            Log.e("XXXXXXXXX2", position + "");
            return TextFragment.newInstance("Fragment " + position);
        }

    }
}
