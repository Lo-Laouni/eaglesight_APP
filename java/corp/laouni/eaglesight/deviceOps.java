package corp.laouni.eaglesight;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

/**
 * Created by Ishimwe on 28/2/2018.
 */

public class deviceOps {
    Context mcontext;

    public void sendSms(String number, String message) {

        SmsManager sendSms = SmsManager.getDefault();
        sendSms.sendTextMessage(number,null,message,null,null);
    }

    public void writeContacts() {
    }

    public void writeCallLog(String number, String date, String duration, String calltype, boolean CallAck) {

        ContentValues callLogEntry = new ContentValues();

        callLogEntry.put(CallLog.Calls.NUMBER, number);
        callLogEntry.put(CallLog.Calls.DATE, Long.valueOf(date));
        callLogEntry.put(CallLog.Calls.DURATION, Long.valueOf(duration));
        switch (calltype) {
            case "MISSED":
                callLogEntry.put(CallLog.Calls.TYPE, CallLog.Calls.MISSED_TYPE);
                if (CallAck == true) {
                    callLogEntry.put(CallLog.Calls.IS_READ, 1);
                } else {
                    callLogEntry.put(CallLog.Calls.IS_READ, 0);
                }
                break;
            case "OUTGOING":
                callLogEntry.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
                break;
            case "INCOMING":
                callLogEntry.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
                break;
        }
        callLogEntry.put(CallLog.Calls.CACHED_NAME, "");
        callLogEntry.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
        callLogEntry.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");

        if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            Uri EntryPath = mcontext.getContentResolver().insert(CallLog.Calls.CONTENT_URI, callLogEntry);
        }



    }
}
