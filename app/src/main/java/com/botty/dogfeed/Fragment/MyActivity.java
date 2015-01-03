package com.botty.dogfeed.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.botty.dogfeed.Articolo;
import com.botty.dogfeed.ArticoloAdapter;
import com.botty.dogfeed.ConnectionHelper;
import com.botty.dogfeed.FeedDetailsActivity;
import com.botty.dogfeed.R;
import com.botty.dogfeed.XMLParser;
import com.github.amlcurran.showcaseview.ShowcaseView;
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

    SwipeRefreshLayout layout;
    ShowcaseView sv;
    ProgressBar mProgressBar;

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

        //get connection
        cHelper = ConnectionHelper.getInstance();

        //load feed
        loadFeed();

      /*  //swipe to refresh
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your action here
                loadFeed();
                layout.setRefreshing(false);
            }
        });
        layout.setColorScheme(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
*/
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        return view;

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

        listaArticoli = (ArrayList<Articolo>) data;
        listViewArticoli.setAdapter( new ArticoloAdapter(getActivity() , listaArticoli) );

        listViewArticoli.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,	long id) {
                Object o = listViewArticoli.getItemAtPosition(position);
                Articolo newsData = (Articolo) o;

                Intent intent = new Intent(getActivity(), FeedDetailsActivity.class);
                intent.putExtra("feed", newsData);
                startActivity(intent);
            }
        });

    }
}
