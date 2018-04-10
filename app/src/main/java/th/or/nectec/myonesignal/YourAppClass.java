package th.or.nectec.myonesignal;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

public class YourAppClass extends Application {
   @Override
   public void onCreate() {
      super.onCreate();

      // Initial OneSignal Push Notification
      OneSignal.startInit(this)
              .setNotificationReceivedHandler(new OneSignal.NotificationReceivedHandler() {
                 @Override
                 public void notificationReceived(OSNotification notification) {
                    //notification.payload.additionalData
                    Log.d("debug", notification.payload.rawPayload); // notification.payload.additionalData.toString();

                    Intent receivedNewMsgComplete = new Intent(CMConstant.RECEIVED_NEW_MESSAGE);//เตรียมส่งจ.ม.
                    receivedNewMsgComplete.putExtra(CMConstant.RECEIVED_MESSAGE, notification.payload.body);// payload แนบข้อความ
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(receivedNewMsgComplete);//ส่ง
                 }
              })
              .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                 @Override
                 public void notificationOpened(OSNotificationOpenResult result) {
                    Log.d("debug", "NotificationOpened");
                 }
              })
              .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
              .init();

      OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
         @Override
         public void idsAvailable(final String playerID, final String token) {

            new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                  if (playerID != null) {
                     Log.i(CMConstant.RECEIVED_PLAYERID, playerID);


                     // notifiy token and playerid via broadcast
                     Intent registrationComplete = new Intent(CMConstant.REGISTRATION_COMPLETE);
                     registrationComplete.putExtra(CMConstant.RECEIVED_TOKEN, token);
                     registrationComplete.putExtra(CMConstant.RECEIVED_PLAYERID, playerID);
                     LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);
                  }
               }
            }, 1000);



         }
      });


   }
}