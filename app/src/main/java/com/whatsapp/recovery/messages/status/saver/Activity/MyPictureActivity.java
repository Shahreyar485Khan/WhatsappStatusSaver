package com.whatsapp.recovery.messages.status.saver.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsapp.recovery.messages.status.saver.R;
import com.whatsapp.recovery.messages.status.saver.Utils.Converter;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.CHECK;
import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.SAVE_PHOTO;
import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.SAVE_STATUS;
import static com.whatsapp.recovery.messages.status.saver.Activity.MainActivity.SAVE_VIDEO;

public class MyPictureActivity extends AppCompatActivity {

    ImageAdapter myImageAdapters;

    ArrayList<String> stringArrayLists = new ArrayList<String>();
    GridView mgridview;
    TextView mtextView1;
    static String path;
    static File paths;
    String optCheck;
    ArrayList<File> videoFileList = new ArrayList<>();

    
    public static final String CHECK_VIDEO = "check_video";
    public static final String IS_VIDEO = "video";
    public static final String IS_IMAGE = "image";
    public static final String VIDEO_FILE = "video_file";
//    public static final String IS_VIDEO = "video";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_picture);
        InitView();
        mtextView1 = findViewById(R.id.textView1);

        //optCheck = getIntent().getStringExtra("key");
        optCheck = getIntent().getStringExtra(CHECK);

        try {
            mgridview = (GridView) findViewById(R.id.gridview_itemsGallery);
            myImageAdapters = new ImageAdapter(this);
            mgridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
            mgridview.setMultiChoiceModeListener(new MultiChoiceModeListener());


            String ExternalStorageDirectoryPath = null;

            if (optCheck != null){

                if(optCheck.equals(SAVE_PHOTO)){

                    ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Status_Saver/Saved_Images";
                }else if (optCheck.equals(SAVE_VIDEO)){

                    ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Status_Saver/Saved_Videos";

                } else if(optCheck.equals(SAVE_STATUS)){

                    ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";

                }

            }

            // Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_LONG).show();
            File targetDirectories = new File(ExternalStorageDirectoryPath);

            File[] files = targetDirectories.listFiles();
            for (File file : files) {
                if (file.exists()) {

                    videoFileList.add(file);
                    if(file.getAbsolutePath().contains(".mp4"))
                    {
                       
                        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), 0); //Creation of Thumbnail of video

                        Resources r = getResources();
                        Drawable[] layers = new Drawable[2];
                        layers[0] = new BitmapDrawable(r,bitmap);
                        layers[1] = r.getDrawable(R.drawable.ic_play_arrow_black_24dp);
                        LayerDrawable layerDrawable = new LayerDrawable(layers);

                        Bitmap bit  = drawableToBitmap(geSingleDrawable(layerDrawable));

                        Uri uri = Converter.bitmapToUriConverter(this,bit);
                        File pathFile = new File(Converter.getRealPathFromURI(this,uri.toString()));
                        myImageAdapters.add(pathFile.getAbsolutePath());
                    }
                    else{
                        myImageAdapters.add(file.getAbsolutePath());
                    }

                    mgridview.setAdapter(myImageAdapters);

                } else {
                    Toast.makeText(getApplicationContext(), "No File Found", Toast.LENGTH_SHORT).show();
                }
            }


                  /*  if (optCheck.equals("Video")){
                        if(file.getAbsolutePath().contains(".mp4"))
                        {
                            allFileList.add(file);
                            Bitmap  bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), 0); //Creation of Thumbnail of video
                            Uri uri = Converter.bitmapToUriConverter(this,bitmap);
                            File pathFile = new File(Converter.getRealPathFromURI(this,uri.toString()));
                            myImageAdapters.add(pathFile.getAbsolutePath());
                        }

                    }else if(optCheck.equals("Images")){
                        if(file.getAbsolutePath().contains(".jpg")) {
                            myImageAdapters.add(file.getAbsolutePath());
                        }
                    }*/
                   // mgridview.setAdapter(myImageAdapters);



//            gridview listener
            mgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    path = (String) adapterView.getItemAtPosition(i);



                    File pathsa = new File(path);
                    paths = pathsa;
                    
                   String path =  videoFileList.get(i).getAbsolutePath();
                    
                    Intent intent = new Intent(getApplicationContext(), SlideImages.class);
                    
                    if(path.contains("mp4")){

                        intent.putExtra(CHECK_VIDEO , IS_VIDEO);
                        intent.putExtra(VIDEO_FILE , path);
                    }
                    
                    startActivity(intent);



                //    Intent intent = new Intent(getApplicationContext(), SlideImages.class);
                        /*if(optCheck.equals("Images")){
                            intent.putExtra("check","img");
                            startActivity(intent);
                        }else if(optCheck.equals("Video")){

                           // Uri uri = Uri.fromFile(allFileList.get(i));
                           Uri uri = FileProvider.getUriForFile(MyPictureActivity.this, getApplicationContext().getPackageName() + ".provider", allFileList.get(i));

                            Intent intent2 = new Intent();
                            intent2.setAction(Intent.ACTION_VIEW);
                            intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent2.setDataAndType(uri, "video/mp4");
                            startActivity(intent2);
//                            intent.putExtra("file",uri.toString());
//                            intent.putExtra("check","vid");
                        }*/
                 
                }
            });

        } catch (Exception r) {
            r.printStackTrace();
        }


    }

    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }


    public void InitView() {
        mtextView1 = findViewById(R.id.textView1);
    }

    /*@Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }*/


    public  Drawable geSingleDrawable(LayerDrawable layerDrawable){
        int resourceBitmapHeight = 136, resourceBitmapWidth = 153;
        float widthInInches = 0.9f;
        int widthInPixels = (int)(widthInInches * getApplication().getResources().getDisplayMetrics().densityDpi);
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
    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }



    public class ImageAdapter extends BaseAdapter {

        ArrayList<String> mitemList = new ArrayList<String>();
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        void add(String path) {
            mitemList.add(path);

        }

        @Override
        public int getCount() {
            if (mitemList.size() == 0) {
                mtextView1.setVisibility(View.VISIBLE);
            } else {
                mtextView1.setVisibility(View.GONE);
            }
            return mitemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return mitemList.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            CheckableLayout l;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);

                l = new CheckableLayout(mContext);

                l.setLayoutParams(new GridView.LayoutParams(
                        GridView.LayoutParams.WRAP_CONTENT,
                        GridView.LayoutParams.WRAP_CONTENT));
                l.addView(imageView);

            } else {
                l = (CheckableLayout) convertView;
                imageView = (ImageView) l.getChildAt(0);
            }

            Bitmap bm = decodeSampledBitmapFromUri(mitemList.get(position), 220, 220);

            imageView.setImageBitmap(bm);
            return l;
        }

        public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

            Bitmap bm = null;
            // Offial decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, options);

            return bm;
        }

        public int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float) height / (float) reqHeight);
                } else {
                    inSampleSize = Math.round((float) width / (float) reqWidth);
                }
            }

            return inSampleSize;
        }

    }

    //TODO: multichoice listener method for grid
    public class MultiChoiceModeListener implements
            AbsListView.MultiChoiceModeListener {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            mode.setTitle("Select Items");
            mode.setSubtitle("1 item selected");
            mode.getMenuInflater().inflate(R.menu.main_menu, menu);
            mode.getMenu().getItem(2).setEnabled(false);

            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int id = item.getItemId();
            //sahre images

            if (id == R.id.selectall) {
                try {

                    for (int i = 0; i < mgridview.getAdapter().getCount(); i++) {
                        final int position = i;
                        mgridview.setItemChecked(position, true);
                        //l.setChecked(true);
                    }
                    myImageAdapters.notifyDataSetChanged();
                    item.setEnabled(false);

                    mode.getMenu().getItem(2).setEnabled(true);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }

                return true;
            } else if (id == R.id.ic_delte) {

                for (String path : stringArrayLists/* List of the files you want to send */) {
                    File file = new File(path);

                    file.delete();
                    Toast.makeText(getApplicationContext(), "Image Deleted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                }

                return true;
            } else if (id == R.id.deselectall) {
                try {
                    mgridview.clearChoices();
                    myImageAdapters.notifyDataSetChanged();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    //l.setChecked(false);
                    item.setEnabled(false);
                    //item.setChecked(false);
                    mode.getMenu().getItem(3).setEnabled(true);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }

                return true;
            }
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {

        }

        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {


            if (checked) {
                stringArrayLists.add(myImageAdapters.mitemList.get(position));
                int selectCount = mgridview.getCheckedItemCount();


                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("1 item selected");
                        //l.setChecked(true);
                        mode.getMenu().getItem(2).setEnabled(true);
                        //mode.getMenu().getItem(2).setChecked(true);
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        mode.getMenu().getItem(2).setEnabled(true);
                        //l.setChecked(true);
                        //mode.getMenu().getItem(2).setChecked(true);
                        break;
                }
            } else {
                stringArrayLists.remove(myImageAdapters.mitemList.get(position));

                int selectCount = mgridview.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("1 item selected");
                        //l.setChecked(false);
                        break;
                    default:
                        //l.setChecked(true);
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }
            }
        }
    }

    //TODO: checkable class
    public class CheckableLayout extends FrameLayout implements Checkable {
        private boolean mChecked;

        public CheckableLayout(Context context) {
            super(context);
        }

        @SuppressWarnings("deprecation")
        public void setChecked(boolean checked) {
            mChecked = checked;
            try {
                setForeground(checked ? getResources().getDrawable(
                        R.drawable.ic_check_black_24dp) : null);

            } catch (Exception ee) {

            }
        }

        public boolean isChecked() {
            return mChecked;
        }

        public void toggle() {
            setChecked(!mChecked);
        }

    }










}
