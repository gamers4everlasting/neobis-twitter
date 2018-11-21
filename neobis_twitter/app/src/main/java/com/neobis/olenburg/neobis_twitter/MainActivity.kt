package com.neobis.olenburg.neobis_twitter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig



class MainActivity : AppCompatActivity() {
    val CONSUMER_KEY = "T5TUq4wU8cuA72Ban58PRDBCL"
    val CONSUMER_SECRET = "26dKIehFTgEhFAtBiL2GVfVgRF2cu9qesHwICUowmmsNIJtb7x"
    var login_btn: TwitterLoginButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
            .debug(true)
            .build()
        Twitter.initialize(config)
        setContentView(R.layout.activity_main)



        login_btn = findViewById(R.id.login_button)

        login_btn!!.setCallback(object : Callback<TwitterSession>(){

            override fun success(result: Result<TwitterSession>) {
                val session = TwitterCore.getInstance().sessionManager.activeSession
                login(result)

            }
            override fun failure(exception: TwitterException?) {
                Log.e("____login failed ", exception.toString())
            }

        });

    }
    fun login(data: Result<TwitterSession>)
    {

        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        login_btn?.onActivityResult(requestCode, resultCode, data);
    }

}
