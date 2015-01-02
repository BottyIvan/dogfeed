package com.botty.dogfeed;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by ivanbotty on 28/08/14.
 */
public class XMLParser extends Observable {

    private static XMLParser xmlParser = null;
    private ArrayList<Articolo> listaArticoli;
    Articolo articoloCorrente;
    public static XMLParser getInstance(){
        if(xmlParser == null)
        {
            xmlParser = new XMLParser();
        }
        return xmlParser;
    }

    public XMLParser()
    {
        listaArticoli = new ArrayList<Articolo>();
        articoloCorrente = new Articolo();

    }

    public void parseXML (String xml)
            throws XmlPullParserException, IOException
    {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader(xml));

        boolean insideItem = false;

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = true;
                } else if (xpp.getName().equalsIgnoreCase("title")) {
                    if (insideItem)
                    {
                        String titolo= new String(xpp.nextText());
                        articoloCorrente.setTitolo(titolo);
                    }
                }
                else if (xpp.getName().equalsIgnoreCase("link")) {
                    if (insideItem)
                    {
                        String link= new String(xpp.nextText());
                        articoloCorrente.setLink(link);
                    }
                }else if (xpp.getName().equalsIgnoreCase("dc:creator")) {
                    if (insideItem)
                    {
                        String autore= new String(xpp.nextText());
                        Log.e("Autore?", autore);
                        articoloCorrente.setAutore(autore);
                    }
                }else if (xpp.getName().equalsIgnoreCase("content:encoded")) {
                    if (insideItem)
                    {
                        String htmlData= new String(xpp.nextText());
                        Log.e("contetn?", htmlData);
                        Document doc = Jsoup.parse(htmlData);
                        String pic= doc.select("img").attr("abs:src");
                        articoloCorrente.setImmagine(pic);
                        articoloCorrente.setContenuto(htmlData);
                    }
                }
                else if (xpp.getName().equalsIgnoreCase("description")) {
                    if (insideItem)
                    {
                        String intro= new String(xpp.nextText());
                        Log.e("intro", intro);
                        articoloCorrente.setIntro(intro);
                    }
                }else if (xpp.getName().equalsIgnoreCase("category")) {
                    if (insideItem)
                    {
                        String categoria= new String(xpp.nextText());
                        Log.e("category", categoria);
                        articoloCorrente.setCategoria(categoria);
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                insideItem = false;
                listaArticoli.add(articoloCorrente);
                articoloCorrente = new Articolo();
            }
            eventType = xpp.next();
        }
        triggerObserver();

    }

    private void triggerObserver() {
        setChanged();
        notifyObservers(listaArticoli);
    }



}
