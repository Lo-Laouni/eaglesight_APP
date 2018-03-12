package corp.laouni.eaglesight;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ishimwe on 12/3/2018.
 */

public class upstream {

    private JSONObject formatDevInfoAsJSON(String a, String b, String c, String d, String e){

        final JSONObject uploads = new JSONObject();

        try {
            JSONArray data = new JSONArray();

            data.put(a);
            data.put(b);
            data.put(c);
            data.put(d);
            data.put(e);

            uploads.put("data", data);
            return  uploads;
        }catch (JSONException E) {
            Log.d("Error formatdataJSON: ", E.toString());
        }
        return null;
    }
    private JSONObject formatDevDataAsJSON(String a, String b, String c, String d,String e, String f, String g, String h){

        final JSONObject uploads = new JSONObject();

        try {
            JSONArray data = new JSONArray();

            data.put(a);
            data.put(b);
            data.put(c);
            data.put(d);
            data.put(e);
            data.put(f);
            data.put(g);
            data.put(h);

            uploads.put("data", data);
            return  uploads;
        }catch (JSONException E) {
            Log.d("Error formatdataJSON: ", E.toString());
        }
        return null;
    }
    protected void sendDevInfoToServer(String a, String b, String c, String d, String e){
        final JSONObject json = formatDevInfoAsJSON(a, b, c, d, e);

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Log.d("UserDataUpload : ",result);
            }
        }.execute();
    }
    protected void sendDevDataToServer(String a, String b, String c, String d,String e, String f, String g, String h){
        final JSONObject json = formatDevDataAsJSON(a, b, c, d, e, f, g, h);

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Log.d("UserDataUpload : ",result);
            }
        }.execute();
    }
    private String getServerResponse(JSONObject json){
        String apiUrl1 = "loiclaouni.pythonanywhere.com/api/v1/luxmdm";
        String apiURL2 = "loiclaouni.pythonanywhere.com/api/v1/AppRegToken";
        String apiURL3 = "loiclaouni.pythonanywhere.com/api/v1/devdata";
        HttpURLConnection apiconnection = null;
        URL url;
        String response ="";
        try {
            if (json.has("key")){
                url = new URL(apiURL2);
            }
            else if (json.has("DeviceData")){
                url = new URL(apiURL3);
            }
            else {
                url= new URL(apiUrl1);
            }

            apiconnection = (HttpURLConnection) url.openConnection();
            apiconnection.setRequestMethod("POST");
            apiconnection.setRequestProperty("Content-Type", "application/json");
            apiconnection.setRequestProperty("Accept","application/json");
            apiconnection.setDoOutput(true);
            apiconnection.setDoInput(true);
            DataOutputStream dataOut = new DataOutputStream(apiconnection.getOutputStream());
            dataOut.writeBytes(json.toString());
            dataOut.flush();
            dataOut.close();

            int responseCode = apiconnection.getResponseCode();
            String responseMsg = apiconnection.getResponseMessage();
            if (responseCode == -1){
                response = "Invalid HTTP response";
            }
            response = "Response Code: "+responseCode+"\nResponse Message: "+responseMsg;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert apiconnection != null;
            apiconnection.disconnect();
        }
        return  response;
    }
}
