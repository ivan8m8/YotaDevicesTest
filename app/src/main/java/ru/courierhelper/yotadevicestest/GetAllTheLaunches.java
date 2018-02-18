package ru.courierhelper.yotadevicestest;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ivan on 14.02.2018.
 */

public class GetAllTheLaunches extends AsyncTask<String, Void, ArrayList<Launch>> {

    private ArrayList<Launch> launches = new ArrayList<>();

    public interface LaunchesAreReadyListener {
        void launchesAreReady(ArrayList<Launch> launches);
    }

    private LaunchesAreReadyListener launchesAreReadyListener = null;

    public GetAllTheLaunches(LaunchesAreReadyListener launchesAreReadyListener) {
        this.launchesAreReadyListener = launchesAreReadyListener;
    }

    @Override
    protected void onPostExecute(ArrayList<Launch> launches) {
        super.onPostExecute(launches);
        launchesAreReadyListener.launchesAreReady(launches);
    }

    @Override
    protected ArrayList<Launch> doInBackground(String... strings) {

        StringBuilder response = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject;

        if (jsonArray != null) {
            for (int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.optJSONObject(i);
                Launch launch = new Launch(
                        jsonObject.optJSONObject("rocket").optString("rocket_name"),
                        Long.parseLong(jsonObject.optString("launch_date_unix")),
                        jsonObject.optJSONObject("links").optString("mission_patch"),
                        jsonObject.optString("details"),
                        jsonObject.optJSONObject("links").optString("article_link"),
                        jsonObject.optJSONObject("links").optString("video_link")
                );
                launches.add(launch);
            }
        }

        return launches;
    }
}
