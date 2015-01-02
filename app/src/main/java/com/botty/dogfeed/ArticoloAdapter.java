package com.botty.dogfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.image.SmartImageView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanbotty on 28/08/14.
 */
public class ArticoloAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Articolo> listaArticoli;
    private static LayoutInflater inflater = null;

    public ArticoloAdapter(Activity a, ArrayList<Articolo> list) {
        mActivity = a;
        listaArticoli = list;
        inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaArticoli.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listaArticoli.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.row, null);
        }

        TextView titolo = (TextView) vi.findViewById(R.id.titolo);
        TextView autore = (TextView) vi.findViewById(R.id.autore);
        TextView intro =(TextView) vi.findViewById(R.id.intro);
        ImageView img = (ImageView) vi.findViewById(R.id.immagine);
        Articolo articoloCorrente = listaArticoli.get(position);
        titolo.setText(Html.fromHtml(articoloCorrente.getTitolo(), null, null));
        intro.setText(Html.fromHtml(articoloCorrente.getIntro(), null, null));
        autore.setText(Html.fromHtml(articoloCorrente.getAutore(), null, null));
        UrlImageViewHelper.setUrlDrawable(img, articoloCorrente.getImmagine(),null, 600, new UrlImageViewCallback() {
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
        return vi;
    }
}