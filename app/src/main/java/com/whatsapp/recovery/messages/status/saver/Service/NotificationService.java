package com.whatsapp.recovery.messages.status.saver.Service;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.whatsapp.recovery.messages.status.saver.DBConnect.DatabaseHelper;
import com.whatsapp.recovery.messages.status.saver.Utils.Converter;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationService extends NotificationListenerService {


    Context context;
    ArrayList<String> msgList = new ArrayList<>();

   private DatabaseHelper dbManager;

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

        dbManager = new DatabaseHelper(this);


    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {

        String mText;
        String pack = sbn.getPackageName();

        if (pack.equals("com.whatsapp")) {

            Bundle extras = sbn.getNotification().extras;

            String text = (extras.get("android.text").toString());

            String title = extras.getString("android.title");




          switch (title) {

              case "WhatsApp":
              case "Finished backup":
              case "Backup in progress":
              case "Backup paused":
              case "Deleting messages...": {

                  break;
              }
              default: {

                  if (text != null) {

                      if (!text.contains("new messages")) {

                          {
                              if (dbManager.isDuplicate(text, title)) {

                                  Log.d("duplicate", "new");
                              } else {
                                  dbManager.insertData(title, text, Converter.getReminingTime());

                              }
                          }
                      }

                  }
              }
          }

            /*if (!title.equals("WhatsApp") || !title.equals("Backup in progress") || !title.equals("Backup paused") || !title.equals("Finished backup") || !title.equals("Deleting messages...") || !title.equals("wasif mahmood")) {


                if (text != null) {

                    if (!text.contains("new messages")) {

                        {
                            if(!dbManager.isDuplicate(text,title))

                                dbManager.insertData(title, text,Converter.getReminingTime() );
                        }
                    }
                }
            }
*/


          /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Parcelable b[];

                b = (Parcelable[]) extras.get(Notification.EXTRA_MESSAGES);

                if (b != null) {
                    //  String content = "";
                    for (Parcelable tmp : b) {

                        Bundle msgBundle = (Bundle) tmp;
                        String content = msgBundle.getString("text");
                        //if (!msgList.contains(content)) {

                       // if (msgList.isEmpty()){

                           msgList.add(content);
                       // }

                        //}

                    }
                }





            }else {*/

               // String pack = sbn.getPackageName();
              //  String ticker = sbn.getNotification().tickerText.toString();
              //  Bundle bundle = sbn.getNotification().extras;
               // String title = bundle.getString("android.title");

              //  String text = (extras.get("android.text").toString());



           // }
          //  dbManager.deleteAll();

  //          String title = extras.getString("android.title");

           /* if (!title.equals("WhatsApp")) {
                if (text != null) {

                    if (!text.contains("new messages")) {

                        {
                            if(!dbManager.isDuplicate(text,title))
                            dbManager.insert(title, text);
                        }
                    }
                }
            }*/
          //  int size = msgList.size();

          /*  for (int i = 0; i < msgList.size(); i++) {

                String text = msgList.get(i);

              //  dbManager.deleteAll();
              // if (!dbManager.isDuplicate(text)) {
                   dbManager.insert(title, text);
            //   }
                // mainActivity.showMessage(text,pack,title);
              *//*  Intent msgrcv = new Intent("Msg");
                msgrcv.putExtra("text", text);
                msgrcv.putExtra("package", pack);
                msgrcv.putExtra("title", title);
                LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);*//*


            }*/


        }





           /* int iconId = extras.getInt(Notification.EXTRA_LARGE_ICON);

            try {
                PackageManager manager = getPackageManager();
                Resources resources = manager.getResourcesForApplication(pack);

                Drawable icon = resources.getDrawable(iconId);
                icon.getAlpha();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (extras.containsKey(Notification.EXTRA_PICTURE)) {
                // this bitmap contain the picture attachment
                Bitmap bmp = (Bitmap) extras.get(Notification.EXTRA_PICTURE);
            }
*/
        // Bitmap bigIcon = (Bitmap) extras.get(Notification.);

        //  Byte dp =  extras.getByte("android.largeIcon");


          /*  ByteBuffer buffer =  extras.get("android.largeIcon");
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);*/





      /*  String pack = sbn.getPackageName();
        String ticker = "";
        if (sbn.getNotification().tickerText != null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bitmap bmp;
        Bundle extras;
        byte[] byteArrayS;
        String encoded = null;

        extras = sbn.getNotification().extras;
        Log.d("extras", extras.toString());

        String contato="";
        String texto = "";
        String search = "mensagens";
        if((extras.getString("android.title").toLowerCase().contains(search.toLowerCase()))){
            if(extras.getString("android.title").toLowerCase().contains("Whatsapp".toLowerCase())){
                extras.getString("android.title").replace("Whatsapp ","");
                Log.d("REPLACE","REPLACE CONCLUÍDO");
            }

            if((extras.getString("android.text").toLowerCase().contains(search.toLowerCase()))){
                Log.d("MSG1","MENSAGEM NÃO AUTORIZADA");
            }
        }

        //TRATA AS NOTIFICAÇÕES FAZENDO COM QUE CADA MENSAGEM ENTRE DE UMA EM UMA DENTRO DA LISTA.
        if (extras.getCharSequence("android.text") != "") {
            if(extras.getString("android.summaryText")!= null) {
                contato = extras.getString("android.title");
                texto = extras.getCharSequence("android.text").toString();
                Log.d("TEXTO1", texto);
            }
        }
        if(extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES) != null){

            if (extras.get("android.textLines") != null) {
                CharSequence[] charText = (CharSequence[]) extras
                        .get("android.textLines");
                Log.d("CHARTEXT",charText.toString());
                if (charText.length > 0) {
                    texto = charText[charText.length - 1].toString();
                    Log.d("TEXTO2",texto);
                }

            }
        }



        Log.i("ContatoINTENT",contato);
        if (texto != "") {
            Log.i("TextoINTENT",texto);
        }
*/

















      // Notification mNotification = sbn.getNotification();

      //  Toast.makeText(context, "this   ", Toast.LENGTH_SHORT).show();
/*

        String pack = sbn.getPackageName();
        String ticker = sbn.getNotification().tickerText.toString();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();

        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",title);
        Log.i("Text",text);

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);

        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
*/

    }




    public static List<String> getText(Notification notification)
    {
        // We have to extract the information from the view
        RemoteViews views = notification.bigContentView;
        if (views == null) views = notification.contentView;
        if (views == null) return null;

        // Use reflection to examine the m_actions member of the given RemoteViews object.
        // It's not pretty, but it works.
        List<String> text = new ArrayList<String>();
        try
        {
            Field field = views.getClass().getDeclaredField("mActions");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);

            // Find the setText() and setTime() reflection actions
            for (Parcelable p : actions)
            {
                Parcel parcel = Parcel.obtain();
                p.writeToParcel(parcel, 0);
                parcel.setDataPosition(0);

                // The tag tells which type of action it is (2 is ReflectionAction, from the source)
                int tag = parcel.readInt();
                if (tag != 2) continue;

                // View ID
                parcel.readInt();

                String methodName = parcel.readString();
                if (methodName == null) continue;

                    // Save strings
                else if (methodName.equals("setText"))
                {
                    // Parameter type (10 = Character Sequence)
                    parcel.readInt();

                    // Store the actual string
                    String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
                    text.add(t);
                }

                // Save times. Comment this section out if the notification time isn't important
                else if (methodName.equals("setTime"))
                {
                    // Parameter type (5 = Long)
                    parcel.readInt();

                    String t = new SimpleDateFormat("h:mm a").format(new Date(parcel.readLong()));
                    text.add(t);
                }

                parcel.recycle();
            }
        }

        // It's not usually good style to do this, but then again, neither is the use of reflection...
        catch (Exception e)
        {
            Log.e("NotificationClassifier", e.toString());
        }

        return text;
    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

    }







}
