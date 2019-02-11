package com.example.natallia_radaman.sayhello

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import android.util.Log

class SayJobService : JobService() {

    override fun onStartJob(jobParams: JobParameters?): Boolean {
        Log.d(TAG, "Performing long running task in scheduled job")
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    companion object {

        private const val TAG = "SayJobService"
    }
}