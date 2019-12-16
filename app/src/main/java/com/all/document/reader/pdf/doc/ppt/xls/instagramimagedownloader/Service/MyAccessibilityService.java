package com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.Service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyAccessibilityService extends AccessibilityService {

    Context context;
    public static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";

    protected void onServiceConnected() {

        //context = getApplicationContext();

        Log.d("Tortuga", "AccessibilityService Connected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


      /*  final int eventType = event.getEventType();
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                switch (eventType) {
                    case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                        Notification notification = (Notification) event.getParcelableData();
                        if (event.getPackageName().equals(WHATSAPP_PACKAGE_NAME)) {
                            RemoteViews views = notification.bigContentView;
                            Class<?> secretClass = views.getClass();
                            ArrayList<String> raw = new ArrayList<String>();
                            Field outerFields[] = secretClass.getDeclaredFields();
                            for (Field outerField : outerFields) {
                                if (outerField.getName().equals("mActions")) {
                                    outerField.setAccessible(true);
                                    ArrayList<Object> actions = null;
                                    try {
                                        actions = (ArrayList<Object>) outerField.get(views);
                                        for (Object action : actions) {
                                            Field innerFields[] = action.getClass().getDeclaredFields();

                                            Object value = null;
                                            Integer type = null;
                                            for (Field field : innerFields) {
                                                try {
                                                    field.setAccessible(true);
                                                    if (field.getName().equals("type")) {
                                                        type = field.getInt(action);
                                                    } else if (field.getName().equals("value")) {
                                                        value = field.get(action);
                                                    }
                                                } catch (IllegalArgumentException e) {
                                                } catch (IllegalAccessException e) {
                                                }
                                            }

                                            if (type != null && type == 10 && value != null) {
                                                raw.add(value.toString());
                                            }
                                        }
                                    } catch (IllegalArgumentException e1) {
                                    } catch (IllegalAccessException e1) {
                                    }
                                }
                                parseWhatsappRawMessages(raw);

                            }
                        }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
*/









       /* Log.d("Tortuga","FML");
        if (e.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            Log.d("Tortuga","Recieved event");
            Parcelable data = e.getParcelableData();
            if (data instanceof Notification) {
                Log.d("Tortuga","Recieved notification");
                Notification notification = (Notification) data;
                Log.d("Tortuga","ticker: " + notification.tickerText);
                Log.d("Tortuga","icon: " + notification.icon);
                Log.d("Tortuga", "notification: "+ e.getText());

                Intent msgrcv = new Intent("Msg");
                msgrcv.putExtra("package", "pack");
                msgrcv.putExtra("ticker", notification.tickerText);
                msgrcv.putExtra("title", notification.icon);
                msgrcv.putExtra("text", e.getText().toString());

               // LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

            }
        }*/


    }


    @Override
    public void onInterrupt() {

    }


    private void parseWhatsappRawMessages(ArrayList<String> raw) {

        int count = raw.size();
        if (count > 2) {
            Log.d("this", "RAW TITLE: " + raw.get(0));
            Log.d("this", "RAW MESSAGE: " + raw.get(1));
        }
    }
























   /* Context context;
    @Override
    protected void onServiceConnected() {
        Log.d("AccessibilityService", "ServiceConnected");

        super.onServiceConnected();
       *//* context = getApplicationContext();
        Toast.makeText(context, "onServiceConnected", Toast.LENGTH_SHORT).show();
        try
        {
            AccessibilityServiceInfo info = new AccessibilityServiceInfo();

            info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;

            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;

            info.notificationTimeout = 100;

            setServiceInfo(info);
        }
        catch(Exception e)
        {
            Log.d("ERROR onServiceConnect", e.toString());
        }*//*
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {


        *//*if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (accessibilityEvent.getPackageName().toString().equals("com.whatsapp")){
                StringBuilder message = new StringBuilder();
                if (!accessibilityEvent.getText().isEmpty()) {
                    for (CharSequence subText : accessibilityEvent.getText()) {
                        message.append(subText);
                    }

                    // Message from +12345678
                    Toast.makeText(context, "show  "+message.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }*//*






      *//*  Notification notification = (Notification) accessibilityEvent.getParcelableData();
        if (null!= notification) {
            RemoteViews views = notification.contentView;
            Class secretClass = views.getClass();

            try {
                Map<Integer, String> text = new HashMap<Integer, String>();

                Field outerFields[] = secretClass.getDeclaredFields();
                for (int i = 0; i < outerFields.length; i++) {
                    if (!outerFields[i].getName().equals("mActions")) continue;

                    outerFields[i].setAccessible(true);

                    ArrayList<Object> actions = (ArrayList<Object>) outerFields[i]
                            .get(views);
                    for (Object action : actions) {
                        Field innerFields[] = action.getClass().getDeclaredFields();

                        Object value = null;
                        Integer type = null;
                        Integer viewId = null;
                        for (Field field : innerFields) {
                            field.setAccessible(true);
                            if (field.getName().equals("value")) {
                                value = field.get(action);
                            } else if (field.getName().equals("type")) {
                                type = field.getInt(action);
                            } else if (field.getName().equals("viewId")) {
                                viewId = field.getInt(action);
                            }
                        }

                        if (type == 9 || type == 10) {
                            text.put(viewId, value.toString());
                        }
                    }

                    System.out.println("title is: " + text.get(16908310));
                    System.out.println("info is: " + text.get(16909082));
                    System.out.println("text is: " + text.get(16908358));


                    Intent msgrcv = new Intent("Msg");
                    msgrcv.putExtra("package", "pack");
                    msgrcv.putExtra("ticker", "tick");
                    msgrcv.putExtra("title", text.get(16908310));
                    msgrcv.putExtra("text", text.get(16908358));
                    Toast.makeText(context, "onServiceConnected"+text.get(16908358), Toast.LENGTH_SHORT).show();

                    LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }*//*



    }


    public static RemoteViews getContentView(Context context, Notification notification)
    {
        if(notification.contentView != null)
            return notification.contentView;
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Notification.Builder.recoverBuilder(context, notification).createContentView();
        else
            return null;
    }

    @Override
    public void onInterrupt() {

    }*/
}
