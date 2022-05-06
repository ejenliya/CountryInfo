package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText user_field;
    private Button main_btn;
    private TextView result_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_field = findViewById(R.id.user_field);
        main_btn = findViewById(R.id.main_btn);
        result_info = findViewById(R.id.result_info);

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_field.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.no_user_input, Toast.LENGTH_LONG).show();
                else {
                    String country = user_field.getText().toString();
                    String url = "https://date.nager.at/api/v3/CountryInfo/" + country;

                    new GetURLData().execute(url);
                }
            }
        });

    }

    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            result_info.setText("Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                Response res = new Response(jsonObject);

                Log.i("response: country", res.getCountry());
                Log.i("response: region", res.getRegion());
                Log.i("response: neighbours", res.getCountryBorderStr());

                result_info.setText("Country: " + res.getCountry() + "\nRegion: " + res.getRegion() + "\nNeighbours: " + res.getCountryBorderStr());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Response {
        private String country;
        private String region;
        private JSONArray borders;

        public Response(JSONObject jo) throws JSONException {
            this.country = jo.getString("officialName");
            this.region = jo.getString("region");
            this.borders = jo.getJSONArray("borders");
        }

        void setCountry(String country) {this.country = country;}
        void setRegion(String region) {this.region = region;}
        void setBorders(JSONArray borders) {this.borders = borders;}

        String getCountry() {return country;}
        String getRegion() {return region;}
        String[] getCountryBorder() throws JSONException {
            String[] country_border = new String[borders.length()];
            for(int i = 0; i < borders.length(); i++) {
                country_border[i] =  borders.getJSONObject(i).getString("officialName");
            }
            return country_border;
        }
        String getCountryBorderStr() throws JSONException {
            String country_border = "";
            for(int i = 0; i < borders.length(); i++) {
                country_border += borders.getJSONObject(i).getString("officialName");
                if(i != borders.length() - 1) country_border += ", ";
            }
            return country_border;
        }
    }
}