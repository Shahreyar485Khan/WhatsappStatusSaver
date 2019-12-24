package com.whatsapp.recovery.messages.status.saver.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsapp.recovery.messages.status.saver.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static TableLayout tab;



    LinearLayout deletedMessage,statusSaver,savePhoto,saveVideo,rateUs;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;

    public static final String SAVE_PHOTO = "save_photo";
    public static final String SAVE_VIDEO = "save_video";
    public static final String SAVE_STATUS = "save_status";
    public static final String CHECK = "key_id";
//     static final String SAVE_VIDEO = "save_video";
    SharedPreferences preferences;

    InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("PREFSs", 0);
        String chk = preferences.getString("firsttime", "0");


        if (chk.equals("yes")) {

            MobileAds.initialize(this,getResources().getString(R.string.app_id));
            reqNewInterstitial();
            initViews();
            requestPermissions();

            viewClickListeners();

        }else{

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.activity_termsand_condition);
            dialog.setTitle("Title...");

            Button dialogButton = (Button) dialog.findViewById(R.id.agree);
            Button dialogButton2 = (Button) dialog.findViewById(R.id.disagree);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("firsttime", "yes");
                    editor.apply();
                    dialog.dismiss();

                    MobileAds.initialize(MainActivity.this,getResources().getString(R.string.app_id));
                    reqNewInterstitial();
                    initViews();
                    requestPermissions();
                    viewClickListeners();

                }
            });

            dialogButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   finishAffinity();
                    // finish();
                    //  startActivity(new Intent(MainActivity.this,MainActivity.class));

                }
            });


            dialog.show();
        }

    }


    private boolean isNotificationServiceRunning() {
        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
    }



    void initViews(){

        deletedMessage = findViewById(R.id.deletedMessage);
        statusSaver = findViewById(R.id.statusSaver);
        savePhoto = findViewById(R.id.savePhoto);
        saveVideo = findViewById(R.id.saveVideo);
        rateUs = findViewById(R.id.rateUs);


    }

    void viewClickListeners(){

        deletedMessage.setOnClickListener(this);
        statusSaver.setOnClickListener(this);
        saveVideo.setOnClickListener(this);
        savePhoto.setOnClickListener(this);
        rateUs.setOnClickListener(this);
    }


    public void createDialogue(Context context){

        //  final Context context = this;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

// set title
        alertDialogBuilder.setTitle("Rate us");

// set dialog message
        alertDialogBuilder
                .setMessage("Would you like to rate us? ")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" +  getApplicationContext().getPackageName())));
                        }

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        finishAffinity();
                    }
                });

// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

// show it
        alertDialog.show();


    }
    public void createDialogueNotification(Context context){

        //  final Context context = this;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

// set title
        alertDialogBuilder.setTitle("Notification Access");

// set dialog message
        alertDialogBuilder
                .setMessage("You have to give Notification Access ")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();

                    }
                });

// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

// show it
        alertDialog.show();


    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have write storage permissions", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "You need to enable the permission for External Storage Write" +
                            " to test out this library.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            }

            default:
        }
    }



    @Override
    public void onBackPressed() {

        createDialogue(getApplicationContext());

    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.deletedMessage:{

                boolean isNotificationServiceRunning = isNotificationServiceRunning();
                if(!isNotificationServiceRunning){

                    createDialogueNotification(this);
                }else {
                    Intent intent =new Intent(this, DeletedMessageActivity.class);
                    startActivity(intent);
                }


                break;
            }
            case R.id.statusSaver: {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    reqNewInterstitial();
                    Intent intent =new Intent(this, StatusSaverActivitiy.class);
                    intent.putExtra(CHECK,SAVE_STATUS);
                    startActivity(intent);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {

                        reqNewInterstitial();
                        Intent intent =new Intent(MainActivity.this, StatusSaverActivitiy.class);
                        intent.putExtra(CHECK,SAVE_STATUS);
                        startActivity(intent);

                    }
                });



                break;
            }
            case R.id.savePhoto: {



                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    reqNewInterstitial();
                    Intent intent =new Intent(this, StatusSaverActivitiy.class);
                    intent.putExtra(CHECK,SAVE_PHOTO);
                    startActivity(intent);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {

                        reqNewInterstitial();
                        Intent intent =new Intent(MainActivity.this, StatusSaverActivitiy.class);
                        intent.putExtra(CHECK,SAVE_PHOTO);
                        startActivity(intent);

                    }
                });


                break;
            }
            case R.id.saveVideo: {


                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    reqNewInterstitial();
                    Intent intent =new Intent(this, StatusSaverActivitiy.class);
                    intent.putExtra(CHECK,SAVE_VIDEO);
                    startActivity(intent);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {

                        reqNewInterstitial();
                        Intent intent =new Intent(MainActivity.this, StatusSaverActivitiy.class);
                        intent.putExtra(CHECK,SAVE_VIDEO);
                        startActivity(intent);

                    }
                });


                break;
            }
            case R.id.rateUs: {

                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" +  getApplicationContext().getPackageName())));
                }


                break;
            }
        }

    }


    public void reqNewInterstitial() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.Interstitial));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
