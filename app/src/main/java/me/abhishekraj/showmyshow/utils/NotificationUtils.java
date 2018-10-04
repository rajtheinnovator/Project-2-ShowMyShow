package me.abhishekraj.showmyshow.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.activity.MainActivity;

/**
 * Created by ABHISHEK RAJ on 8/8/2017.
 */

public class NotificationUtils {

    public static final int ID = 1;
    public static final int WATER_REMINDER_NOTIFICATION_ID = 2;
    public static final int ACTION_IGNORE_PENDING_INTENT_ID = 3;
    public static final int ACTION_DRINK_PENDING_INTENT_ID = 4;

    public static void remindUserBecauseCharging(Context context) {
        // This method will create a notification for charging. It might be helpful
        // to take a look at this guide to see an example of what the code in this method will look like:
        // https://developer.android.com/training/notify-user/build-notification.html

        // Constructs the Builder object.
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_circle_outline_white_48dp)
                        .setLargeIcon(largeIcon(context))
                        .setContentTitle("Refreshed")
                        .setContentText("New Movies found. Click here to take a look !")
                        .setDefaults(Notification.DEFAULT_VIBRATE) // requires VIBRATE permission
                        .setContentIntent(contentIntent(context))
                        .setAutoCancel(true)

                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Now all your favorite movies has been saved for the further inspection. Would you take a look at them ?"));

        // to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, builder.build());

    }


    public static void clearAllNotifications(Context context) {

        NotificationManager nf = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nf.cancelAll();
    }

    public static PendingIntent contentIntent(Context context) {

        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context,
                ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static Bitmap largeIcon(Context context) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.posterplaceholder);
        // resources object and R.drawable.ic_local_drink_black_24px
        return largeIcon;
    }


}
