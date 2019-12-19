package com.whatsapp.recovery.messages.status.saver.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsapp.recovery.messages.status.saver.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whatsapp.recovery.messages.status.saver.DBConnect.DatabaseHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import static com.whatsapp.recovery.messages.status.saver.Activity.DeletedMessageActivity.CONTACT_NAME;

public class SingleChatActivity extends AppCompatActivity implements View.OnClickListener {




    DatabaseHelper dbManager;

    private ListView listView;
    TextView txtHeader;

    private SimpleCursorAdapter adapter;
    final String[] from = new String[] {
            DatabaseHelper.COL_3 , DatabaseHelper.COL_4};

    final int[] to = new int[] { R.id.chat_text ,R.id.chat_time};

    String contactName;
    TextView txtAction;
    LinearLayout imgBack,imgDelChat;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        txtAction = findViewById(R.id.chat_header);
        imgBack = findViewById((R.id.layout_chat_back));
        imgDelChat = findViewById((R.id.layout_chat_del));


        dbManager = new DatabaseHelper(this);

        contactName = getIntent().getStringExtra(CONTACT_NAME);

        txtAction.setText(contactName);

        Cursor cursor = dbManager.searchByName(contactName);

       int i = cursor.getCount();


        listView =  findViewById(R.id.chat_list_view);
//        listView.setEmptyView(findViewById(R.id.chat_empty));

        adapter = new SimpleCursorAdapter(this, R.layout.chat_list_view, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        imgBack.setOnClickListener(this);
        imgDelChat.setOnClickListener(this);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView textView =  view.findViewById(R.id.chat_text);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Message", textView.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(SingleChatActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


        BannerAd();


    }
    public void createDialogue(Context context){

        //  final Context context = this;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

// set title
        alertDialogBuilder.setTitle("Delete Chat");

// set dialog message
        alertDialogBuilder
                .setMessage("Are you sure? ")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dbManager.deleteDataByTitle(contactName);
                        onBackPressed();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();

                    }
                });

// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

// show it
        alertDialog.show();


    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.layout_chat_back:{
                onBackPressed();
                break;
            }case R.id.layout_chat_del:{
                createDialogue(this);
                break;
            }

        }

    }

    private void BannerAd() {
        adView = findViewById(R.id.bannerad);
        AdRequest adrequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adrequest);
        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {

                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                adView.setVisibility(View.GONE);
            }

        });
    }

}
