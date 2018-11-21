package com.neobis.olenburg.neobis_twitter

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.support.v7.widget.SearchView
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import com.twitter.sdk.android.tweetui.SearchTimeline
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Twitter.initialize(this)
        val userTimeline = UserTimeline.Builder()
            .screenName("olenburg4twitt")
            .build()
        val adapter = TweetTimelineListAdapter.Builder(this)
            .setTimeline(userTimeline)
            .build()
        list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val mSearchmenuItem = menu?.findItem(R.id.search_item)
        val searchView = mSearchmenuItem?.actionView as SearchView
        searchView.setQueryHint("enter Text")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s: String?): Boolean {
                Log.e("__got search:", s)

                val timeline = SearchTimeline.Builder()
                    .query(s)
                    .build()
                val adapter = TweetTimelineListAdapter.Builder(this@Home)
                    .setTimeline(timeline)
                    .build()
                list.adapter = adapter
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.e("__inItemSelected1 id=", item!!.itemId.toString())

        when (item.itemId) {
            R.id.search_item -> {
//                Log.e("__inItemSelected2 id=", item.itemId.toString() )
//                val search = item.actionView as? SearchView
//
//                search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//                    override fun onQueryTextChange(s: String): Boolean {
//                        // make a server call
//                        Log.e("__text changed:", s)
//                        val listView =  findViewById(R.id.list) as ListView
//                        Log.e("__got search:", s)
//
//                        val timeline = SearchTimeline.Builder()
//                            .query(s)
//                            .build()
//                        val adapter = TweetTimelineListAdapter.Builder(this@Home)
//                            .setTimeline(timeline)
//                            .build()
//                        listView.adapter = adapter
//
//                        return true
//                    }
//
//                    override fun onQueryTextSubmit(s: String): Boolean {
//                        return false
//                    }
//                })
                return true

            }
            R.id.tweet_add -> {
                val intent = Intent(this, AddTweet::class.java)
                startActivity(intent)
                return true;
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
