package com.whatsapp.recovery.messages.status.saver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whatsapp.recovery.messages.status.saver.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.whatsapp.recovery.messages.status.saver.Activity.ItemPreviewActivity;
import com.whatsapp.recovery.messages.status.saver.Activity.StatusSaverActivitiy;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import static com.whatsapp.recovery.messages.status.saver.Activity.StatusSaverActivitiy.allFileList;
import static com.whatsapp.recovery.messages.status.saver.Activity.StatusSaverActivitiy.checkActivity;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<ImageUrl> imageUrls;
    private Context context;
    InterstitialAd interstitialAd;

    public DataAdapter(Context context, ArrayList<ImageUrl> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_layout, viewGroup, false);

        MobileAds.initialize(context,context.getResources().getString(R.string.app_id));
        reqNewInterstitial();

        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {



        Glide.with(context).load(imageUrls.get(i).getImageUrl()).into(viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    reqNewInterstitial();
                    Intent intent = new Intent (context, ItemPreviewActivity.class);
                    String str = StatusSaverActivitiy.check.get(i);

                    if (str.equals("0"))
                    {
                        intent.putExtra("bitmap_uri",imageUrls.get(i).getImageUrl());
                        intent.putExtra("original_uri", allFileList.get(i).getAbsolutePath());
                        intent.putExtra("check", "video");
                    }
                    else
                    {
                        intent.putExtra("bitmap_uri",imageUrls.get(i).getImageUrl());
                        intent.putExtra("check", "image");
                    }
                    // intent.putExtra("bitmap_uri",imageUrls.get(i).getImageUrl());
                    intent.putExtra("activity",checkActivity);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {

                        reqNewInterstitial();
                        Intent intent = new Intent (context, ItemPreviewActivity.class);
                        String str = StatusSaverActivitiy.check.get(i);

                        if (str.equals("0"))
                        {
                            intent.putExtra("bitmap_uri",imageUrls.get(i).getImageUrl());
                            intent.putExtra("original_uri", allFileList.get(i).getAbsolutePath());
                            intent.putExtra("check", "video");
                        }
                        else
                        {
                            intent.putExtra("bitmap_uri",imageUrls.get(i).getImageUrl());
                            intent.putExtra("check", "image");
                        }
                        // intent.putExtra("bitmap_uri",imageUrls.get(i).getImageUrl());
                        intent.putExtra("activity",checkActivity);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
        }
    }

    public void reqNewInterstitial() {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getResources().getString(R.string.Interstitial));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }


}