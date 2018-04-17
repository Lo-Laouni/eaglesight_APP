package corp.laouni.eaglesight;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by Ishimwe on 17/4/2018.
 */

public class deviceConfigs {
    Context mContext;
    TelephonyManager tmanager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    WifiManager wmanager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    public boolean checkwifistatus(){
        boolean status = wmanager.isWifiEnabled();
        return status;
    }
    public void changewifistatus(boolean w){
        if(w){
            boolean status = wmanager.setWifiEnabled(w);
        }
        else {
            boolean status = wmanager.setWifiEnabled(w);
        }
    }
    public boolean checkdatastatus(){
       // boolean status = tmanager.isDataEnabled();
       // return  status;
        return false; //just temporarily
    }
    public void changedatastatus(boolean d){
        //tmanager.setDataEnabled(d);
    }
    public void decommission(){}
}
