package com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.DBConnect.DatabaseHelper;
import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.R;

import static com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity.DeletedMessageActivity.CONTACT_NAME;

public class SingleChatActivity extends AppCompatActivity implements View.OnClickListener {




    DatabaseHelper dbManager;

    private ListView listView;
    TextView txtHeader;

    private SimpleCursorAdapter adapter;
    final String[] from = new String[] {
            DatabaseHelper.COL_3 ,DatabaseHelper.COL_4};

    final int[] to = new int[] { R.id.chat_text ,R.id.chat_time};

    String contactName;
    TextView txtAction;
    ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        txtAction = findViewById(R.id.chat_header);


        dbManager = new DatabaseHelper(this);

        contactName = getIntent().getStringExtra(CONTACT_NAME);

        txtAction.setText(contactName+" Deleted Messages");

        Cursor cursor = dbManager.searchByName(contactName);

        listView = (ListView) findViewById(R.id.chat_list_view);
//        listView.setEmptyView(findViewById(R.id.chat_empty));

        adapter = new SimpleCursorAdapter(this, R.layout.chat_list_view, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.chat_back:{

                finish();

                break;
            }

        }

    }
}
