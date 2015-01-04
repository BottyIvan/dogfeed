package com.botty.dogfeed.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.botty.dogfeed.R;
import com.melnykov.fab.FloatingActionButton;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class About extends Fragment {

    private ListView mOpenProjList;
    private ArrayAdapter<String> listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_about, container,
                false);

        mOpenProjList = (ListView) view.findViewById(R.id.openproj);

        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_about, mOpenProjList, false);
        mOpenProjList.addHeaderView(headerView, null, false);

        CircularImageView imgRound = (CircularImageView) view.findViewById(R.id.Circle);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.button_floating_action);
        floatingActionButton.attachToListView(mOpenProjList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gpiu = new Intent(Intent.ACTION_VIEW);
                gpiu.setData(Uri.parse("http:google.com/+IvanBotty"));
                startActivity(gpiu);
            }
        });

        String[] autori = new String[]{"Android Asynchronous Http Client", "UrlImageViewHelper",
                "FadingActionBar", "ShowcaseView", "CircularImageView", "AndroidTransition", "FloatingActionButton"};

        final ArrayList<String> listaAutori = new ArrayList<String>();
        listaAutori.addAll(Arrays.asList(autori));
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.openproabout, listaAutori);
        mOpenProjList.setAdapter(listAdapter);

        mOpenProjList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent AndroidAsynchronousHttpClient = new Intent(Intent.ACTION_VIEW);
                        AndroidAsynchronousHttpClient.setData(Uri.parse("http://loopj.com/android-async-http/"));
                        startActivity(AndroidAsynchronousHttpClient);
                        break;
                    case 1:
                        Intent UrlImageViewHelper = new Intent(Intent.ACTION_VIEW);
                        UrlImageViewHelper.setData(Uri.parse("https://github.com/koush/UrlImageViewHelper"));
                        startActivity(UrlImageViewHelper);
                        break;
                    case 2:
                        Intent FadingActionBar = new Intent(Intent.ACTION_VIEW);
                        FadingActionBar.setData(Uri.parse("https://github.com/ManuelPeinado/FadingActionBar"));
                        startActivity(FadingActionBar);
                        break;
                    case 3:
                        Intent ShowcaseView = new Intent(Intent.ACTION_VIEW);
                        ShowcaseView.setData(Uri.parse("https://github.com/amlcurran/ShowcaseView"));
                        startActivity(ShowcaseView);
                        break;
                    case 4:
                        Intent CircularImageView = new Intent(Intent.ACTION_VIEW);
                        CircularImageView.setData(Uri.parse("https://github.com/Pkmmte/CircularImageView"));
                        startActivity(CircularImageView);
                        break;
                    case 5:
                        Intent AndroidTransition = new Intent(Intent.ACTION_VIEW);
                        AndroidTransition.setData(Uri.parse("https://github.com/black1987/AndroidTransition"));
                        startActivity(AndroidTransition);
                        break;
                    case 6:
                        Intent FloatingActionButton = new Intent(Intent.ACTION_VIEW);
                        FloatingActionButton.setData(Uri.parse("https://github.com/makovkastar/FloatingActionButton"));
                        startActivity(FloatingActionButton);
                        break;
                    default:
                        return;
                }

            }
        });

        return view;
    }


}


