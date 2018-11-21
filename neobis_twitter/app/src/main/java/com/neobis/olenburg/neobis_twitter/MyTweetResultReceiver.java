package com.neobis.olenburg.neobis_twitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;



public class MyTweetResultReceiver extends BroadcastReceiver {

    private static final String TAG = MyTweetResultReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        //if Tweet upload success then show toast if required
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            // you will get Tweet Id here in case you want to use it in your app.
            final Long tweetId = bundle.getLong(TweetUploadService.EXTRA_TWEET_ID);
            Toast.makeText(context, "Tweet uploaded successfully with Tweet ID : " + tweetId, Toast.LENGTH_SHORT).show();
            Intent dialogIntent = new Intent(context, Home.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);
        } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
            //if Tweet upload failed then u can retry tweet uploading by starting the intent again.
            Bundle bundle = intent.getExtras();

            // start activity via using this intent if required
            final Intent retryIntent = bundle.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);

            //show toast of failure
            Toast.makeText(context, "Failed to uploaded tweet.", Toast.LENGTH_SHORT).show();
            Intent dialogIntent = new Intent(context, Home.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);

        } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
            // if user cancel the tweet compose then show toast
            Toast.makeText(context, "User cancelled Tweet compose..", Toast.LENGTH_SHORT).show();
            Intent dialogIntent = new Intent(context, Home.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);
        }
    }
}
