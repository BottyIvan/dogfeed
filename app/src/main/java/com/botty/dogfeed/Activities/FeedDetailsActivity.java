package com.botty.dogfeed.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.botty.dogfeed.Fragment.About;
import com.botty.dogfeed.Fragment.MyActivity;
import com.botty.dogfeed.Helper.Articolo;
import com.botty.dogfeed.R;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

/**
 * Created by ivanbotty on 08/09/14.
 */
public class FeedDetailsActivity extends Activity {

    private Articolo feed;
    WebView article;
    String titolo;
    String link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);

        feed = (Articolo) this.getIntent().getSerializableExtra("feed");

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(feed.getTitolo());
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_solid_cool)
                .headerLayout(R.layout.header)
                .contentLayout(R.layout.activity_feed_details);
        setContentView(helper.createView(this));
        helper.initActionBar(this);

        if (null != feed) {
            ImageView thumb = (ImageView) findViewById(R.id.featuredImg);
            Ion.with(thumb).load(feed.getImmagine());

            article= (WebView) findViewById(R.id.articleTxt);
            article.setWebViewClient(new WebViewClient());
            article.setBackgroundColor(Color.TRANSPARENT);
            Bundle extras = getIntent().getExtras();
            titolo = extras.getString("titoloArticolo");
            link = extras.getString("link");
            String contenuto = extras.getString("contenutoArticolo");
            article.getSettings().setJavaScriptEnabled(true);
            article.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}iframe{max-width: 100%;}</style>\n"+"<h2>"+titolo+"</h2>"+contenuto, null, null, null);

        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition (R.anim.open_main, R.anim.close_next);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}