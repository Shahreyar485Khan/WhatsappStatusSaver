package com.whatsapp.recovery.messages.status.saver.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsapp.recovery.messages.status.saver.BuildConfig;
import com.whatsapp.recovery.messages.status.saver.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whatsapp.recovery.messages.status.saver.Utils.Converter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

public class ItemPreviewActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout imgDownload,imgPlay,imgShare;
    ImageView imgPreview;
    TextView txtAction;

    String bitmapPath;
    String orignalPath;
    String check;
    String activity;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);

        imgDownload = findViewById(R.id.layout_download);
        imgPreview = findViewById(R.id.img_preview);
        imgPlay = findViewById(R.id.layout_play);
        imgShare = findViewById(R.id.layout_share);
        txtAction = findViewById(R.id.txt_action);

        bitmapPath = getIntent().getStringExtra("bitmap_uri");
        orignalPath = getIntent().getStringExtra("original_uri");
        check = getIntent().getStringExtra("check");
        activity = getIntent().getStringExtra("activity");


        Glide.with(this).load(bitmapPath).into(imgPreview);

        if (activity.equals("save_status")) {

            if (check.equals("image")) {

                imgPlay.setVisibility(View.GONE);
            } else {
                imgPlay.setVisibility(View.VISIBLE);
            }
        }

        if (activity.equals("save_photos"))
        {
            txtAction.setText("Save Photos");
            imgDownload.setVisibility(View.GONE);
            imgPlay.setVisibility(View.GONE);

        }else if (activity.equals("save_videos")){

            txtAction.setText("Save Videos");
            imgDownload.setVisibility(View.GONE);

        }

        imgDownload.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgPreview.setOnClickListener(this);

        BannerAd();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.layout_download:{

                LinearLayout linearLayout = findViewById(R.id.layout);

                if (check.equals("video")){

                    try {
                        Converter.copyFile(new File(orignalPath), new File(Converter.getVideoFilename()));
                        Converter.showSnack(this,linearLayout,"Video Downloaded!",Uri.parse(orignalPath));
                        Converter.addVideo(this,new File(orignalPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {

                    try {
                        Converter.copyFile(new File(Converter.getRealPathFromURI(this, bitmapPath)), new File(Converter.getImageFilename()));
                        Converter.showSnack(this,linearLayout,"Image Downloaded!",Uri.parse(bitmapPath));
                        Converter.addImage(this,new File(Converter.getRealPathFromURI(this, bitmapPath)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            }
            case R.id.layout_play:
            case R.id.img_preview: {

                if (check.equals("video")) {

                    Uri uri = FileProvider.getUriForFile(ItemPreviewActivity.this, getApplicationContext().getPackageName() + ".provider", new File(orignalPath));

                    Intent intent2 = new Intent();
                    intent2.setAction(Intent.ACTION_VIEW);
                    intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent2.setDataAndType(uri, "video/mp4");
                    startActivity(intent2);
                }

                break;
            }
            case R.id.layout_share:{

                if (check.equals("video")) {
                    shareVideo(orignalPath);
                }else {
                    shareImage(bitmapPath);
                }
                break;
            }

        }

    }



    public void shareImage (String path){
        File dir = new File(path);
        Uri uri = FileProvider.getUriForFile(ItemPreviewActivity.this, BuildConfig.APPLICATION_ID + ".provider",dir);
        Bitmap icon = Converter.getBitmapfromURI(this,uri);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri2 = FileProvider.getUriForFile(ItemPreviewActivity.this, BuildConfig.APPLICATION_ID + ".provider",f);
        //  share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        share.putExtra(Intent.EXTRA_STREAM, uri2);
        startActivity(Intent.createChooser(share, "Share Image"));

    }

    public  void shareVideo(String path){


        File videoFile = new File(path);
        Uri videoURI = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                ? FileProvider.getUriForFile(this, this.getPackageName(), videoFile)
                : Uri.fromFile(videoFile);
        ShareCompat.IntentBuilder.from(ItemPreviewActivity.this)
                .setStream(videoURI)
                .setType("video/mp4")
                .setChooserTitle("Share video...")
                .startChooser();


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
