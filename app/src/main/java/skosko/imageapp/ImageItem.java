package skosko.imageapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Skosko on 9.5.2017.
 */

public class ImageItem {
    String url;
    String name;
    String location;
    String time;



    public static ArrayList<ImageItem> getScoresFromString(String file, Context context){


        final ArrayList<ImageItem> ScoreList = new ArrayList<>();

        try {
            JSONArray json = new JSONArray(file);
            for (int i = 0 ; i < json.length() ; i++){
                JSONObject data = json.getJSONObject(i);
                ImageItem THIS = new ImageItem();
                THIS.url = data.getString("url");
                THIS.location = data.getString("location");
                THIS.time = data.getString("time");
                THIS.name = data.getString("name");
                ScoreList.add(THIS);
               // System.out.println( THIS.url +"  " +  THIS.location +"  " +  THIS.time +"  " +  THIS.name +"  " );

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ScoreList;




    }



}
