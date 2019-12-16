package com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.BuildConfig;
import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.R;
import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Utils.Converter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static TableLayout tab;



    LinearLayout deletedMessage,statusSaver,savePhoto,saveVideo,rateUs;

    public static final String SAVE_PHOTO = "save_photo";
    public static final String SAVE_VIDEO = "save_video";
    public static final String SAVE_STATUS = "save_status";
    public static final String CHECK = "key_id";
//     static final String SAVE_VIDEO = "save_video";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        initViews();
       // LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

        viewClickListeners();

        String time = Converter.getReminingTime();
        Toast.makeText(this, "time  "+time, Toast.LENGTH_SHORT).show();


      /*  dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);*/


    }





    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {



            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");

            Toast.makeText(MainActivity.this, "test1    "+text, Toast.LENGTH_SHORT).show();
        //    Toast.makeText(MainActivity.this, "test2    "+title, Toast.LENGTH_SHORT).show();
        //    Toast.makeText(MainActivity.this, "test3    "+text, Toast.LENGTH_SHORT).show();


            if (pack.equals("com.whatsapp")) {

                TableRow tr = new TableRow(getApplicationContext());
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TextView textview = new TextView(getApplicationContext());
                textview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                textview.setTextSize(20);
                textview.setTextColor(Color.parseColor("#0B0719"));
                textview.setText(Html.fromHtml(pack + "<br><b>" + title + " : </b>" + text));
                tr.addView(textview);
                tab.addView(tr);


            }



        }
    };







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



    @Override
    public void onBackPressed() {

        createDialogue(getApplicationContext());

       /* if (doubleBackToExitPressedOnce) {

            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);*/
    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.deletedMessage:{

                Intent intent =new Intent(this,DeletedMessageActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.statusSaver: {

                Intent intent =new Intent(this,MyPictureActivity.class);
                intent.putExtra(CHECK,SAVE_STATUS);
                startActivity(intent);

                break;
            }
            case R.id.savePhoto: {

                Intent intent =new Intent(this,MyPictureActivity.class);
                intent.putExtra(CHECK,SAVE_PHOTO);
                startActivity(intent);

                break;
            }
            case R.id.saveVideo: {

                Intent intent =new Intent(this,MyPictureActivity.class);
                intent.putExtra(CHECK,SAVE_VIDEO);
                startActivity(intent);

                break;
            }
            case R.id.rateUs: {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    e.printStackTrace();
                }

                break;
            }
        }

    }
}
