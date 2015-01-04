package com.botty.dogfeed.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.botty.dogfeed.Helper.Articolo;
import com.botty.dogfeed.R;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

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
        Ion.with(img).centerCrop().error(R.drawable.dog).load(articoloCorrente.getImmagine());
        return vi;
    }
}