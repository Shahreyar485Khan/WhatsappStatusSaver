package com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.ViewFlipper;


import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.BuildConfig;
import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.R;
import com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Utils.Converter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import static com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity.MyPictureActivity.CHECK_VIDEO;
import static com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity.MyPictureActivity.IS_VIDEO;
import static com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity.MyPictureActivity.VIDEO_FILE;
import static com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Activity.MyPictureActivity.paths;

public class SlideImages extends AppCompatActivity implements SurfaceHolder.Callback {

    ViewFlipper mviewPagers;
    VideoView mVideoView;
    private GestureDetector gesturesDetectors;

    Bitmap mBitmap;
    Uri uriImages;
    String optCheck;
    String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_images);


        mviewPagers = findViewById(R.id.viewPager);

        optCheck = getIntent().getStringExtra(CHECK_VIDEO);
        videoPath = getIntent().getStringExtra(VIDEO_FILE);

/*

        optCheck = getIntent().getStringExtra("check");


        if(optCheck.equals("img")){

            mviewPagers.setVisibility(View.VISIBLE);

            try {
                String ImagesUris = paths.toString();
                uriImages = Uri.fromFile(new File(ImagesUris));
                Bitmap fgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImages);
                ImageView mimageView = new ImageView(SlideImages.this);
                mimageView.setImageBitmap(fgBitmap);
                mviewPagers.addView(mimageView);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (optCheck.equals("vid")){

            mVideoView.setVisibility(View.VISIBLE);

            String ImagesUris = getIntent().getStringExtra("file");
            uriImages = Uri.parse(ImagesUris);

            mVideoView.setVideoPath(uriImages.toString());
            mVideoView.requestFocus();
            mVideoView.start();

        }
*/



        try {
            String ImagesUris = paths.toString();
            uriImages = Uri.fromFile(new File(ImagesUris));
            Bitmap fgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImages);
            ImageView mimageView = new ImageView(SlideImages.this);
            mimageView.setImageBitmap(fgBitmap);
            mviewPagers.addView(mimageView);

        } catch (IOException e) {
            e.printStackTrace();
        }

      //  CustomGestureDetector customGestureDetector = new CustomGestureDetector();
       // gesturesDetectors = new GestureDetector(this, customGestureDetector);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_images, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.share) {
            shareImage();
            return true;
        }
        if (id == R.id.st_play) {
            shareImage();
            return true;
        }
        if (id == R.id.st_download) {

            if (optCheck.equals(IS_VIDEO)){

                try {
                    Converter.copyFile(new File(videoPath), new File(Converter.getImageFilename()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {

                try {
                    Converter.copyFile(new File(Converter.getRealPathFromURI(this, uriImages.toString())), new File(Converter.getImageFilename()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // gesturesDetectors.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

   /* class CustomGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                mviewPagers.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                mviewPagers.showPrevious();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }*/

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MyPictureActivity.class));
        finish();
    }*/



   /* private Bitmap getScreenShot(View vi) {
//        _fbMenu.close(true);
//        _fbMenu.setVisibility(View.GONE);
        vi.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(vi.getDrawingCache());
        vi.setDrawingCacheEnabled(false);
        return bitmap;
    }*/

    @TargetApi(Build.VERSION_CODES.N)
    public void SharemyPhoto() {
        try {
            File dir = new File(paths.toString());
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), dir);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
    private Bitmap getBitmapfromURI(Uri uri){

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
    public void shareImage (){
        File dir = new File(paths.toString());
        Uri uri = FileProvider.getUriForFile(SlideImages.this, BuildConfig.APPLICATION_ID + ".provider",dir);
        Bitmap icon = getBitmapfromURI(uri);
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
        Uri uri2 = FileProvider.getUriForFile(SlideImages.this, BuildConfig.APPLICATION_ID + ".provider",f);
        //  share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        share.putExtra(Intent.EXTRA_STREAM, uri2);
        startActivity(Intent.createChooser(share, "Share Image"));

    }
}