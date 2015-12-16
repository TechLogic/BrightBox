/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.brightbox.entities.controllers;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public void findAll() {
        new CallAPI().execute(url);
    }


    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString=params[0]; // URL to call

            String resultToDisplay = "";

            InputStream in = null;

            // HTTP Get
            try {

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                in = new BufferedInputStream(urlConnection.getInputStream());

            } catch (Exception e ) {

               Log.e("JSON",e.getMessage());

                return e.getMessage();

            }
            Log.d("JSON",resultToDisplay);
            return resultToDisplay;
        }


        }

        protected void onPostExecute(String result) {

        }

    } // end CallAPI



