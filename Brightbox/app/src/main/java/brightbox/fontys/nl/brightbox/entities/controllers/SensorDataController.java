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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import brightbox.fontys.nl.brightbox.entities.models.BrightBox;
import brightbox.fontys.nl.brightbox.entities.models.SensorData;

/**
 *
 * @author techlogic
 */

public class SensorDataController{


    private static SensorDataController INSTANCE = new SensorDataController();
    private final String  url = "http://brightbox.ddns.net:8000/api/sensor_data";

    private SensorDataController(){
    }

    public static SensorDataController getINSTANCE() {
        return INSTANCE;
    }

    public List<SensorData> findAll() throws ExecutionException, InterruptedException {
        CallAPI api = new CallAPI();
            return api.execute(url).get();
    }

    public List<SensorData> findById(int id) throws ExecutionException, InterruptedException {
       return new CallAPI().execute(url + "?id=" + id).get();
    }

    public List<SensorData> findByBrightBox(BrightBox box) throws ExecutionException, InterruptedException {
       return new CallAPI().execute(url + "?fk_bright_box=" + box.getId()).get();
    }




    private class CallAPI extends AsyncTask<String, String, List<SensorData>> {

        @Override
        protected List<SensorData> doInBackground(String... params) {
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
               return parseBrightBoxJSON(reader);
            } catch (Exception e ) {
                Log.e("JSON", e.getMessage());
            }
            return new ArrayList<>();
        }

        private List<SensorData> parseBrightBoxJSON(JsonReader reader) throws IOException {
            String result;
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("result")){
                    result = reader.nextString();
                    //TODO throw Exception if not successful
                }else if ( name.equals("json")){
                    return parseSensorData(reader);
                }else{
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new ArrayList<>();
        }

        private List<SensorData> parseSensorData(JsonReader reader) throws IOException {
            List<SensorData> list = new ArrayList<>();
            reader.beginArray();

            while(reader.hasNext()){
                reader.beginObject();
                int id = -1;
                int fk_bright_box = -1;
                Timestamp timestamp = null;
                double bloom = Double.NaN;
                double vega = Double.NaN;
                double grow = Double.NaN;
                double airTemp = Double.NaN;
                double humidity = Double.NaN;
                double ph = Double.NaN;
                double ec = Double.NaN;
                double waterTemp = Double.NaN;
                while(reader.hasNext()){
                    String name = reader.nextName();
                    switch(name){
                        case "id":{
                            id = reader.nextInt();
                            break;
                        }
                        case"fk_bright_box":{
                                fk_bright_box = reader.nextInt();
                                break;
                        }
                        case "timestamp":{
                            String s = reader.nextString();
                           s =  s.replace("T"," ");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                            Date date = null;
                            try {
                                date = dateFormat.parse(s);
                                timestamp = new Timestamp(date.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "bloom":{
                            bloom = reader.nextDouble();
                            break;
                        }
                        case "vega":{
                            vega = reader.nextDouble();
                            break;
                        }
                        case "air_temperatur":{
                            airTemp = reader.nextDouble();
                            break;
                        }case "humidity":{
                            humidity = reader.nextDouble();
                            break;
                        }case "ph_value":{
                            ph = reader.nextDouble();
                            break;
                        }case "ec_value":{
                            ec = reader.nextDouble();
                            break;
                        }case "water_temperatur": {
                            waterTemp = reader.nextDouble();
                            break;
                        }
                        case "grow": {
                            grow = reader.nextDouble();
                            break;
                        }
                        default: reader.skipValue();
                    }
                }
                if(id >= 0 && timestamp != null && fk_bright_box >=0 && vega != Double.NaN && airTemp != Double.NaN && humidity != Double.NaN && ph != Double.NaN && ec != Double.NaN && waterTemp != Double.NaN){
                    SensorData sensorData = new SensorData(id,timestamp,bloom,vega,grow,airTemp,humidity,ph,ec,waterTemp);
                    BrightBox box = null;
                    try {
                        box = BrightBoxController.getINSTANCE().findById(fk_bright_box);
                        sensorData.setFkBrightBox(box);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    list.add(sensorData);
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


