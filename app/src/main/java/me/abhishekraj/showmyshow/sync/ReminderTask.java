package me.abhishekraj.showmyshow.sync;

import android.content.Context;

import me.abhishekraj.showmyshow.utils.NotificationUtils;

/**
 * Created by ABHISHEK RAJ on 8/8/2017.
 */

public class ReminderTask {

    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss_notification";

    public static final String ACTION_CHARGING_REMINDER = "charging_reminder";

    public static void executeTask(Context context, String action) {
        if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_CHARGING_REMINDER.equals(action)) {

            issueChargingReminder(context);

        }
    }

    private static void issueChargingReminder(Context context) {
        NotificationUtils.remindUserBecauseCharging(context);
    }

}