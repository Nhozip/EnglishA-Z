package com.it.nhozip.englisha_z.config;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.it.nhozip.englisha_z.MainActivity;
import com.it.nhozip.englisha_z.R;

import java.util.List;

/**
 * Created by huyen on 9/22/2016.
 */
public class Var {
    public static final String DATA_FB = "fb";
    public static final String LOGIN = "login";
    private static SharedPreferences pre;
    public  static  int with=0;
    public  static  FacebookCallback<AppInviteDialog.Result> appInviteDialogResult;

    public static void inviteFriendFB(CallbackManager callbackManager,MainActivity mainActivity) {
        //App link là đường dẫn của app trên googlePlay
       // String AppURl = mainActivity.getString(R.string.appLink);
        String AppURl = "http://play.google.com/store/apps/details?id=com.it.nhozip.englisha_z";
      //  String previewImageUrl = Constant.AppImageName;        // size recommend is 1,200 x 628 pixels
        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(AppURl)
                   // .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog appInviteDialog = new AppInviteDialog(mainActivity);
            appInviteDialog.registerCallback(callbackManager, appInviteDialogResult);
            appInviteDialog.show(content);
        }
    }

    public static void initFB() {
        appInviteDialogResult = new FacebookCallback<AppInviteDialog.Result>() {
            @Override
            public void onSuccess(AppInviteDialog.Result result) {
               Log.e("Invitive","thành công");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        };
    }

    public static void yKien(Context context) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String[] recipients = new String[]{"huyenchien1996@gmail.com"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Góp ý về ứng dụng ");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Ý kiến:");
            emailIntent.setType("plain/text");
            final PackageManager pm = context.getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") ||
                        info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
            if (best != null)
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            context.startActivity(emailIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Application not found", Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareFB(Context context,String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/plain");

        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().contains("facebook")) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }

        context.startActivity(intent);
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }
    public static void setdataOff(Context context, String json_object) {
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
        //editor.clear();
        editor.putBoolean(LOGIN, true);
        editor.putString("data", json_object);
        editor.commit();
    }

    public static String getDataOFFFB(Context context){
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        return pre.getString("data","");
    }
    public static void updateTrangThai(Context context) {
         pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
      //  editor.clear();
        editor.putBoolean(LOGIN, false);
        editor.commit();
    }

    public static boolean loginNoFirst(Context context) {
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        return pre.getBoolean(LOGIN, false);
    }
    public static void setColor(Context context, String color) {
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        SharedPreferences.Editor editor = pre.edit();
        //editor.clear();
        editor.putString("color", color);
        editor.commit();
    }
    public static String getColor(Context context){
        pre = context.getSharedPreferences
                (DATA_FB, 0);
        return pre.getString("color","#0aadf9");
    }
}
