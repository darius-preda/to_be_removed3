package ro.pub.cs.systems.eim.practicaltestv03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client extends Thread {
    public String userInput;
    public Context context;

    public Client(String userInput, Context context) {
        this.userInput = userInput;
        this.context = context;
    }

    @Override
    public void run() {
        try{
            String urlString = Constants.DICTIONARY_URL + userInput;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Log.d("ClientThread", "Raspuns complet: " + response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject everything = jsonArray.getJSONObject(0);
            JSONArray meanings = everything.getJSONArray("meanings");
            JSONObject firstMeaning = meanings.getJSONObject(0);
            JSONArray definitions = firstMeaning.getJSONArray("definitions");
            JSONObject firstDefinition = definitions.getJSONObject(0);
            String resultsString = firstDefinition.getString("definition");

            Log.d("ClientThread", "Definiție extrasă: " + resultsString);

            Intent intent = new Intent(Constants.ACTION_AUTOCOMPLETE);
            intent.putExtra(Constants.DATA_KEY, resultsString);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            Log.e("ClientThread", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
