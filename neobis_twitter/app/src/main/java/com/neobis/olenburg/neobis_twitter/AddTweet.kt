package com.neobis.olenburg.neobis_twitter

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.ImageView
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import java.io.File
import android.widget.Toast
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import android.content.Intent
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.DialogInterface
import android.support.v7.app.AlertDialog;
import android.app.Activity
import com.squareup.picasso.Picasso
import android.R.attr.data
import android.util.Log
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient


class AddTweet : AppCompatActivity() {
    private var pickedImageView: ImageView? = null
    private val GALLERY_REQUEST_CODE = 332
    private val SHARE_PERMISSION_CODE = 223
    //URI of picked/captured image
    private var cameraFileURI: Uri? = null
    //Twitter auth client to do custom Twitter login
    private var client: TwitterAuthClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tweet)

        pickedImageView = findViewById(R.id.picked_image_view);
    }

    /**
     * method to share picked/capture image with text using Twitter Native Composer
     * NOTE : For this you should authenticate user before sharing image as the builder required TwitterSession.
     * It does not depend on the Twitter for Android app being installed.
     *
     * @param view
     */
    fun shareUsingTwitterNativeComposer(view: View) {
        //check if user has picked/captured image or not
        if (cameraFileURI != null) {
            val session = TwitterCore.getInstance().sessionManager
                .activeSession//get the active session
            Log.e("______session ", session.toString())

            if (session != null) {
                val intent = ComposerActivity.Builder(this)
                    .session(session)//Set the TwitterSession of the User to Tweet
                    .image(cameraFileURI)//Attach an image to the Tweet
                    .text("This is Native Kit Composer Tweet!!")//Text to prefill in composer
                    .hashtags("#android")//Hashtags to prefill in composer
                    .createIntent()//finally create intent
                startActivity(intent)

            } else {
            //    authenticateUser();
                Log.e("______share error", session)
        }
        } else {
            //if not then show dialog to pick/capture image
            Toast.makeText(this, "Please select image first to share.", Toast.LENGTH_SHORT).show()
            onPermissionGranted();
        }
    }

//    private fun authenticateUser() {
//        client = TwitterAuthClient()//init twitter auth client
//        client?.authorize(this, object : Callback<TwitterSession>() {
//            override fun success(twitterSessionResult: Result<TwitterSession>) {
//                //if user is successfully authorized start sharing image
//                Toast.makeText(this@AddTweet, "Login successful.", Toast.LENGTH_SHORT).show()
//                shareUsingTwitterNativeComposer()
//            }
//
//            override fun failure(e: TwitterException) {
//                //if user failed to authorize then show toast
//                Toast.makeText(this@AddTweet, "Failed to authenticate by Twitter. Please try again.", Toast.LENGTH_LONG).show()
//
//            }
//        })
//    }


    private fun onPermissionGranted() {
        AlertDialog.Builder(this)
            .setTitle("Select Option")
            .setItems(arrayOf("Gallery"), DialogInterface.OnClickListener { dialogInterface, i ->
                when (i) {
                    0 ->
                        //Gallery
                        selectImageFromGallery()
                }
            })
            .setCancelable(true)
            .create()
            .show()
    }

    /**
     * start activity to pick image from gallery
     */
    private fun selectImageFromGallery() {
        val `in` = Intent(Intent.ACTION_PICK)
        `in`.type = "image/*"
        startActivityForResult(`in`, GALLERY_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                //get the picked image URI
                val imageUri = data!!.data
                //set the picked image URI to created variable
                cameraFileURI = imageUri

                displayImage(imageUri)
                } else {

                    Toast.makeText(this, "Failed to pick up image from gallery.", Toast.LENGTH_SHORT).show()
                }

            else ->
                //put this here as Twitter requires to send result back to our Class
                if (client != null)
                    client?.onActivityResult(requestCode, resultCode, data);
        }
    }

    private fun displayImage(imageUri: Uri) {
        Picasso.with(this).load(imageUri).into(pickedImageView)
    }
}
