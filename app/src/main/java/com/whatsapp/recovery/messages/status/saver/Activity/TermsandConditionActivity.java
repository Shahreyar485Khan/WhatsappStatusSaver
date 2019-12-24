package com.whatsapp.recovery.messages.status.saver.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.whatsapp.recovery.messages.status.saver.R;

import androidx.appcompat.app.AppCompatActivity;

public class TermsandConditionActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences preferences;
    ProgressDialog dialog;
    Button btnAgree,btnDicline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        preferences = getSharedPreferences("PREFSs", 0);
        String chk = preferences.getString("firsttime", "0");
        dialog = new ProgressDialog(this);
        dialog.setMessage("please wait...");

        if (chk.equals("yes")) {
            try {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            setContentView(R.layout.activity_termsand_condition);
            btnAgree = findViewById(R.id.agree);
            btnDicline = findViewById(R.id.disagree);

            btnAgree.setOnClickListener(this);
            btnDicline.setOnClickListener(this);

        }


    }

    public void acceptbtn() {

        try {

      //      dialog.show();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firsttime", "yes");
            editor.apply();
            //    dialog.dismiss();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


           /* final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    dialog.dismiss();
                }
            }, 3000);*/





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void denybtn() {
        finishAffinity();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.agree:{
                acceptbtn();
                break;
            }
            case R.id.disagree:{
                denybtn();
                break;
            }

        }


    }
}
