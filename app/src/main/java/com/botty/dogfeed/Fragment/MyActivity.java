package com.botty.dogfeed.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.botty.dogfeed.Helper.Articolo;
import com.botty.dogfeed.Adapters.ArticoloAdapter;
import com.botty.dogfeed.Helper.ConnectionHelper;
import com.botty.dogfeed.Activities.FeedDetailsActivity;
import com.botty.dogfeed.R;
import com.botty.dogfeed.Helper.XMLParser;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleMusicDicesDrawable;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyActivity extends Fragment implements Observer
{

    ConnectionHelper cHelper = null;
    XMLParser xmlParser = null;
    ArrayList<Articolo> listaArticoli = null;
    ListView listViewArticoli;
    Context ctx;
    SwipeRefreshLayout layout;
    ShowcaseView sv;
    ArticoloAdapter mArticoloAdapter;
    ProgressDialog myProgressDialog;


    public MyActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // il layout era sbagliato xD
        View view =  inflater.inflate(R.layout.activity_main, container,
                false);

        //find view
        listViewArticoli = (ListView) view.findViewById(R.id.listViewArticoli);
        layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);

        ctx= getActivity();
        myProgressDialog = new ProgressDialog(ctx);
        myProgressDialog.setCancelable(false);


        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.back_toolbar, listViewArticoli, false);
        listViewArticoli.addHeaderView(headerView, null, false);

        //swipe to refresh
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your action here
                loadFeed();
                Toast.makeText(getActivity(),"Updated !",Toast.LENGTH_SHORT);
                layout.setRefreshing(false);
            }
        });
        layout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        //get connection
        cHelper = ConnectionHelper.getInstance();
        createDialog();
        //load feed
        int position = listViewArticoli.getCount();
        if (position > listViewArticoli.getCount() - 4){
            loadFeed();

        }

        return view;

    }

    public void createDialog()
    {
        myProgressDialog.show();
        myProgressDialog.setMessage("Caricamento...");
    }


    public void addArticolo(Articolo articolo){
        listaArticoli.add(articolo);
    }

    public void loadFeed()
    {
        xmlParser = XMLParser.getInstance();
        xmlParser.addObserver(this);
        cHelper.get("feed",null,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("statusCode", statusCode+"");
                String xml = new String(responseBody);
                try {
                    xmlParser.parseXML(xml);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("error",statusCode+"");
            }
        });
    }

    @Override
    public void update(Observable observable, final Object data) {
        layout.setRefreshing(false);
        listaArticoli = (ArrayList<Articolo>) data;
        mArticoloAdapter = new ArticoloAdapter(getActivity(),listaArticoli);
        listViewArticoli.setAdapter(mArticoloAdapter);
        listViewArticoli.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int i,	long id) {
                Object o = listViewArticoli.getItemAtPosition(i);
                Articolo newsData = (Articolo) o;
                Intent intent = new Intent(getActivity(), FeedDetailsActivity.class);
                intent.putExtra("feed", newsData);
                intent.putExtra("titoloArticolo",listaArticoli.get(i).getTitolo());
                intent.putExtra("contenutoArticolo",listaArticoli.get(i).getContenuto());
                intent.putExtra("link", listaArticoli.get(i).getLink());
                startActivity(intent);
            }
        });
        myProgressDialog.dismiss();

    }

    @Override public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(),"onResume !",Toast.LENGTH_SHORT).show();
        if(mArticoloAdapter!=null) {
            mArticoloAdapter = new ArticoloAdapter(getActivity(), listaArticoli);
            listViewArticoli.setAdapter(mArticoloAdapter);

        }
    }
}
