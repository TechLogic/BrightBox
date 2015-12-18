/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brightbox.fontys.nl.brightbox.entities.controllers;


import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import brightbox.fontys.nl.brightbox.entities.models.BrightBox;

/**
 *
 * @author techlogic
 */
public class BrightBoxController{

    private static BrightBoxController INSTANCE = new BrightBoxController();
    private final String  url = "http://brightbox.ddns.net:8000/api/bright_box";

    private BrightBoxController(){
    }

    public static BrightBoxController getINSTANCE() {
        return INSTANCE;
    }

    public List<BrightBox> findAll() throws ExecutionException, InterruptedException {
        return new CallAPI().execute(url).get();
    }

    public List<BrightBox> findById(int id) throws ExecutionException, InterruptedException {
     return new CallAPI().execute(url+"?id="+id).get();
    }




    private class CallAPI extends AsyncTask<String, String, List<BrightBox>> {

        @Override
        protected List<BrightBox> doInBackground(String... params) {
            String urlString=params[0]; // URL to call

            String resultToDisplay = "";

            InputStream in = null;
            String result = "Empty";

            // HTTP Get
            try {

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                in = new BufferedInputStream(urlConnection.getInputStream());
                JsonReader reader = new JsonReader(new InputStreamReader(in));
               return  parseBrightBoxJSON(reader);
            } catch (Exception e ) {

               Log.e("JSON",e.getMessage());

            }
            return  new ArrayList<>();
        }

        private List<BrightBox> parseBrightBoxJSON(JsonReader reader) throws IOException{
            String result;
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("result")){
                    result = reader.nextString();
                    //TODO throw Exception if not successful
                }else if ( name.equals("json")){
                    return parseBrightBox(reader);
                }else{
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new ArrayList<>();
        }

        private List<BrightBox> parseBrightBox(JsonReader reader) throws IOException {
            List<BrightBox> list = new ArrayList<>();
            reader.beginArray();

            while(reader.hasNext()){
                reader.beginObject();
                int id = -1;
                String box_name = null;
                String description = null;
                String identifier = null;
                while(reader.hasNext()){
                    String name = reader.nextName();
                    switch(name){
                        case "id":{
                    id = reader.nextInt();
                            break;
                        }
                        case "name":{
                            box_name = reader.nextString();
                            break;
                        }
                        case "description":{
                            description = reader.nextString();
                            break;
                        }
                        case "identifier":{
                            identifier = reader.nextString();
                            break;
                        }
                        default: reader.skipValue();
                    }
                }
                if(id >= 0 && description  != null && box_name != null && identifier != null){
                    BrightBox box = new BrightBox(id,box_name,description,identifier);
                    list.add(box);
                }
                reader.endObject();
            }
            reader.endArray();
            return list;
        }


    }

        protected void onPostExecute(String result) {

        }

    } // end CallAPI



