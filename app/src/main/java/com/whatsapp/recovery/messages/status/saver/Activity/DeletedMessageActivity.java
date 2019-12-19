package com.whatsapp.recovery.messages.status.saver.Activity;

import android.content.Intent;
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


import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class DeletedMessageActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String CONTACT_NAME = "contact_title";

    private ListView listView;
    LinearLayout imgBack;
    LinearLayout imgRefresh;

    LinearLayout listAction;

    DatabaseHelper dbManager;

    ArrayList<Integer> ids = new ArrayList<>();

    private SimpleCursorAdapter adapter;

    private ArrayList<String> titleFirst = new ArrayList<>();

    final String[] from = new String[]{DatabaseHelper.COL_5,
            DatabaseHelper.COL_2, DatabaseHelper.COL_3};

    final int[] to = new int[]{R.id.letter, R.id.title, R.id.desc};


    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        imgBack = findViewById(R.id.layout_list_back);
        imgRefresh = findViewById(R.id.layout_refresh);
        listAction = findViewById(R.id.listAction);

        dbManager = new DatabaseHelper(this);

        final Cursor cursor = dbManager.getDistinctRecord();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
      //  listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        imgBack.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);

/*

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {

                listAction.setVisibility(View.GONE);
                final int checkedCount = listView.getCheckedItemCount();

                mode.setTitle(checkedCount + " Selected");

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.list_delete:

                        SparseBooleanArray checked = listView.getCheckedItemPositions();

                        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                            if (checked.get(i)) {
                               // TextView txt = listView.getSelectedView().getId();
                              //  String s = txt.getText().toString();
                              //  TextView txt = listView.getItemAtPosition(i);
                                // Do something



                            }
                        }

                        mode.finish();
                        return true;
                    case R.id.list_deselectall:
                        mode.finish();
                    case R.id.list_selectall:
                        mode.finish();
                    default:
                        return false;
                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.list_menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
               // listView.removeSelection();
                listAction.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });

*/











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

            }


        });


        BannerAd();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.layout_list_back:{
                onBackPressed();
                break;
            }
            case R.id.layout_refresh:{

             //   listView.setAdapter(null);
                dbManager = new DatabaseHelper(this);

                final Cursor cursor = dbManager.getDistinctRecord();

                listView = (ListView) findViewById(R.id.list_view);
                listView.setEmptyView(findViewById(R.id.empty));

                adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
                adapter.notifyDataSetChanged();

                listView.setAdapter(adapter);

                Toast.makeText(this, "List refresh!", Toast.LENGTH_SHORT).show();

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
