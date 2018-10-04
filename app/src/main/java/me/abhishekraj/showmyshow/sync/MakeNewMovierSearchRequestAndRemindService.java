package me.abhishekraj.showmyshow.sync;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by ABHISHEK RAJ on 8/8/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MakeNewMovierSearchRequestAndRemindService extends JobService {
    private AsyncTask mAsyncTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        // Do some work here
        mAsyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = MakeNewMovierSearchRequestAndRemindService.this;
                ReminderTask.executeTask(context, ReminderTask.ACTION_CHARGING_REMINDER);

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }

        };

        mAsyncTask.execute();
        return true; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
        }
        return true; // Answers the question: "Should this job be retried?"
    }
}