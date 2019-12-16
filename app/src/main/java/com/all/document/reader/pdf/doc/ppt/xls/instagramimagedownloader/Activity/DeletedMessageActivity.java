package com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.DBConnect.DatabaseHelper;
import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.R;

import java.util.ArrayList;

public class DeletedMessageActivity extends AppCompatActivity {


    public static final String CONTACT_NAME = "contact_title";

    private ListView listView;

    DatabaseHelper dbManager;

    private SimpleCursorAdapter adapter;

    private ArrayList<String> titleFirst = new ArrayList<>();

    final String[] from = new String[]{DatabaseHelper.COL_5,
            DatabaseHelper.COL_2, DatabaseHelper.COL_3};

    final int[] to = new int[]{R.id.letter, R.id.title, R.id.desc};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);


        dbManager = new DatabaseHelper(this);

        Cursor cursor = dbManager.getDistinctRecord();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                TextView tv = (TextView) arg1.findViewById(R.id.title);
                TextView tv2 = (TextView) arg1.findViewById(R.id.desc);

                // tv2.setMaxLines(12);
                String title = tv.getText().toString();

                Intent intent = new Intent(DeletedMessageActivity.this, SingleChatActivity.class);
                intent.putExtra(CONTACT_NAME, title);
                startActivity(intent);

               /* txtHeader.setVisibility(View.VISIBLE);
                txtHeader.setText(title);

               Toast.makeText(DeletedMessageActivity.this, "clicked" +title, Toast.LENGTH_SHORT).show();

               Cursor cursor = dbManager.searchByName(title);

                adapter = new SimpleCursorAdapter(DeletedMessageActivity.this, R.layout.chat_list_view, cursor, fromChat, toChat, 0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);*/


            }
        });


    }
}
