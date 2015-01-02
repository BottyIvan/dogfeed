package com.botty.dogfeed;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.botty.dogfeed.Fragment.About;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

/**
 * Created by ivanbotty on 08/09/14.
 */
public class FeedDetailsActivity extends Activity {

    private Articolo feed;

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
            UrlImageViewHelper.setUrlDrawable(thumb, feed.getImmagine(),null, 600, new UrlImageViewCallback() {
                @Override
                public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                    if (!loadedFromCache) {
                        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                        scale.setDuration(300);
                        scale.setInterpolator(new OvershootInterpolator());
                        imageView.startAnimation(scale);
                    }
                }
            });

            // TextView title = (TextView) findViewById(R.id.titolo);
           // title.setText(feed.getTitolo());

            TextView contenuto = (TextView) findViewById(R.id.contenuto);
            contenuto.setText(Html.fromHtml(feed.getContenuto(), null, null));
            contenuto.setMovementMethod(LinkMovementMethod.getInstance());

            TextView categoria = (TextView) findViewById(R.id.categoria);
            categoria.setText(Html.fromHtml(feed.getCategoria(), null, null));
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
            case R.id.about:
                Intent about = new Intent(this, About.class);
                about.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(about);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}