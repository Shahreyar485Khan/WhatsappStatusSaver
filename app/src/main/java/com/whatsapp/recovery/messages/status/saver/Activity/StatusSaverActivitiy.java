package com.whatsapp.recovery.messages.status.saver.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.whatsapp.recovery.messages.status.saver.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whatsapp.recovery.messages.status.saver.Adapter.DataAdapter;
import com.whatsapp.recovery.messages.status.saver.Adapter.ImageUrl;
import com.whatsapp.recovery.messages.status.saver.Utils.Converter;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.CHECK;
import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.SAVE_PHOTO;
import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.SAVE_STATUS;
import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.SAVE_VIDEO;

public class StatusSaverActivitiy extends AppCompatActivity {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    String optCheck;
    public static ArrayList<File> allFileList = new ArrayList<>();
    ArrayList<String> statusFileList = new ArrayList<>();
    public static ArrayList<String> check = new ArrayList<>();

    public static String checkActivity;

    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_saver_activitiy);

        recyclerView = findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        optCheck = getIntent().getStringExtra(CHECK);

        String ExternalStorageDirectoryPath = null;

        if (optCheck != null) {

            if(optCheck.equals(SAVE_PHOTO)){

                ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Status_Saver/Saved_Images";
                checkActivity = "save_photos";
            }else if (optCheck.equals(SAVE_VIDEO)){

                ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Status_Saver/Saved_Videos";
                checkActivity = "save_videos";

            } else if (optCheck.equals(SAVE_STATUS)) {

                ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
                checkActivity = "save_status";
            }

        }


        File targetDirectories = new File(ExternalStorageDirectoryPath);

        File[] files = targetDirectories.listFiles();
        allFileList.clear();
        check.clear();

        for (File file : files) {
            if (file.exists()) {

                allFileList.add(file);

              //  Log.d("asdfg", ""+file.getAbsolutePath());

                if (file.getAbsolutePath().contains(".mp4")) {

                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), 0); //Creation of Thumbnail of video

                    Resources r = getResources();
                    Drawable[] layers = new Drawable[2];
                    layers[0] = new BitmapDrawable(r, bitmap);
                    layers[1] = r.getDrawable(R.drawable.ic_play_arrow_black_24dp);
                    LayerDrawable layerDrawable = new LayerDrawable(layers);

                    Bitmap bit = drawableToBitmap(geSingleDrawable(layerDrawable));

                    Uri uri = Converter.bitmapToUriConverter(this, bit);
                    File pathFile = new File(Converter.getRealPathFromURI(this, uri.toString()));

                    statusFileList.add(pathFile.getAbsolutePath());
                    check.add("0");

                    // myImageAdapters.add(pathFile.getAbsolutePath());
                } else {
                    //  myImageAdapters.add(file.getAbsolutePath());
                    statusFileList.add(file.getAbsolutePath());
                    check.add("1");
                }

                //  mgridview.setAdapter(myImageAdapters);

            } else {
                Toast.makeText(getApplicationContext(), "No File Found", Toast.LENGTH_SHORT).show();
            }
        }


        ArrayList imageUrlList = prepareData(statusFileList);
        DataAdapter dataAdapter = new DataAdapter(getApplicationContext(), imageUrlList);
        recyclerView.setAdapter(dataAdapter);

        BannerAd();
    }

    private ArrayList prepareData(ArrayList str) {


        ArrayList imageUrlList = new ArrayList<>();
        for (int i = 0; i < str.size(); i++) {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImageUrl(str.get(i).toString());
            imageUrlList.add(imageUrl);
        }
        Log.d("MainActivity", "List count: " + imageUrlList.size());
        return imageUrlList;
    }


    public Drawable geSingleDrawable(LayerDrawable layerDrawable) {
        int resourceBitmapHeight = 136, resourceBitmapWidth = 153;
        float widthInInches = 0.9f;
        int widthInPixels = (int) (widthInInches * getApplication().getResources().getDisplayMetrics().densityDpi);
        int heightInPixels = widthInPixels * resourceBitmapHeight / resourceBitmapWidth;
        int insetLeft = 10, insetTop = 10, insetRight = 10, insetBottom = 10;
        layerDrawable.setLayerInset(1, insetLeft, insetTop, insetRight, insetBottom);
        Bitmap bitmap = Bitmap.createBitmap(widthInPixels, heightInPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layerDrawable.setBounds(0, 0, widthInPixels, heightInPixels);
        layerDrawable.draw(canvas);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplication().getResources(), bitmap);
        bitmapDrawable.setBounds(0, 0, widthInPixels, heightInPixels);
        return bitmapDrawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
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
