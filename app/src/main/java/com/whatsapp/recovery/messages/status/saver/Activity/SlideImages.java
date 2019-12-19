package com.whatsapp.recovery.messages.status.saver.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.whatsapp.recovery.messages.status.saver.BuildConfig;
import com.whatsapp.recovery.messages.status.saver.R;
import com.whatsapp.recovery.messages.status.saver.Utils.Converter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import static com.whatsapp.recovery.messages.status.saver.Activity.MyPictureActivity.CHECK_VIDEO;
import static com.whatsapp.recovery.messages.status.saver.Activity.MyPictureActivity.IS_VIDEO;
import static com.whatsapp.recovery.messages.status.saver.Activity.MyPictureActivity.VIDEO_FILE;
import static com.whatsapp.recovery.messages.status.saver.Activity.MyPictureActivity.paths;

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

        if (optCheck == null){
            optCheck = "default";
        }


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

        mviewPagers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(optCheck != null) {
                    if (optCheck.equals(IS_VIDEO)) {
                        Uri uri = FileProvider.getUriForFile(SlideImages.this, getApplicationContext().getPackageName() + ".provider", new File(videoPath));

                        Intent intent2 = new Intent();
                        intent2.setAction(Intent.ACTION_VIEW);
                        intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent2.setDataAndType(uri, "video/mp4");
                        startActivity(intent2);
                    }
                }


            }
        });

      //  CustomGestureDetector customGestureDetector = new CustomGestureDetector();
       // gesturesDetectors = new GestureDetector(this, customGestureDetector);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_images, menu);
        if (optCheck !=null){

            if(optCheck.equals(IS_VIDEO)){

                MenuItem register = menu.findItem(R.id.st_play);
                register.setVisible(true);  //userRegistered is boolean, pointing if the user has registered or not.
                return true;

            }



        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.share) {

            if(optCheck != null) {
                if (optCheck.equals(IS_VIDEO)) {

                 //  Uri uri = Uri.fromFile(new File(videoPath));
                   shareFile(videoPath);

                }else {

                    shareFile(uriImages.toString());
                }
            }



            return true;
        }
        if (id == R.id.st_play) {

            if(optCheck.equals(IS_VIDEO)){

                Uri uri = FileProvider.getUriForFile(SlideImages.this, getApplicationContext().getPackageName() + ".provider",new File(videoPath));

                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_VIEW);
                intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent2.setDataAndType(uri, "video/mp4");
                startActivity(intent2);

            }

            return true;
        }
        if (id == R.id.st_download) {

            if (optCheck.equals(IS_VIDEO)){

                try {
                    Converter.copyFile(new File(videoPath), new File(Converter.getVideoFilename()));
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


    public void shareFile(String path){


        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(path);
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share file using"));

    }

    void shareFilePath(String myFilePath)
    {
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(myFilePath);
        if(fileWithinMyDir.exists()) {
            intentShareFile.setType("*/*");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+myFilePath));
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
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