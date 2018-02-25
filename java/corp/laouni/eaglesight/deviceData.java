package corp.laouni.eaglesight;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by Ishimwe on 25/2/2018.
 */

public class deviceData {
    //read contacts
    //read call logs
    //read sms
    //track location
    //read phone state
    Context mContext;
    TelephonyManager tmanager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);


    public void readcontacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI; //contacts URI
        Cursor contacts = mContext.getContentResolver().query(uri, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC"); //contacts Cursor
        if (contacts != null && contacts.moveToFirst()) {
            do {
                String contactId = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor contactsData = mContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.CONTACT_ID + " = " + contactId, null, null);
                String displayName;
                String phoneNumber = "";
                StringBuilder contactOut = new StringBuilder();

                if (contactsData != null && contactsData.moveToFirst()) {
                    displayName = contactsData.getString(contactsData.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    do {
                        if (contactsData.getString(contactsData.getColumnIndex(ContactsContract.Contacts.Entity.MIMETYPE)).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            phoneNumber = contactsData.getString(contactsData.getColumnIndex(ContactsContract.Contacts.Entity.DATA1));
                        }
                        contactOut.append("\n\n").append(displayName).append(" : ").append(phoneNumber);
                    } while (contactsData.moveToNext());
                    contactsData.close();
                }
            } while (contacts.moveToNext());
            contacts.close();
        }
    }

    public void readcalllogs() {

        StringBuilder callLogOut = new StringBuilder();
        callLogOut.append("Call Log : ");

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            Cursor clCursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            if (clCursor != null) {
                while (clCursor.moveToNext()){
                    String callType="";
                    String phoneNumber = clCursor.getString(clCursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String calltypex = clCursor.getString(clCursor.getColumnIndex(CallLog.Calls.TYPE));
                    int ct = Integer.parseInt(calltypex);
                    switch (ct){
                        case CallLog.Calls.OUTGOING_TYPE:
                            callType = "OUTGOING";
                            break;
                        case CallLog.Calls.INCOMING_TYPE:
                            callType = "INCOMING";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            callType = "MISSED";
                            break;
                    }
                    Date callDayTime = new Date(Long.valueOf(clCursor.getString(clCursor.getColumnIndex(CallLog.Calls.DATE))));
                    String callDuration = clCursor.getString(clCursor.getColumnIndex(CallLog.Calls.DURATION));

                    callLogOut.append("\nPhone Number : ").append(phoneNumber).
                            append("\nCall Type : ").append(callType).
                            append("\nCall Time : ").append(callDayTime).
                            append("\nCall Duration").append(callDuration).
                            append("\n................................................");
                }

            } clCursor.close();

        }

    }
    public void getLocation(){}
    public void readsms(){
        StringBuilder SMS = new StringBuilder();
        Uri InboxSms = Uri.parse("content://sms/inbox");
        Uri SentSms = Uri.parse("content://sms/sent");
        Cursor inbox = mContext.getContentResolver().query(InboxSms,null,null,null,null);
        Cursor sent = mContext.getContentResolver().query(SentSms, null, null, null, null);
        if (inbox != null){
            SMS.append("\n\n INBOX MESSAGES");
            while (inbox.moveToNext()){
                String from = inbox.getString(inbox.getColumnIndexOrThrow("id"));
                String body = inbox.getString(inbox.getColumnIndexOrThrow("body"));
                SMS.append("\nFrom: ").append(from).
                        append("\nMessage: ").append(body).
                        append("............................................");

            }
        } inbox.close();

        if (sent != null){
            SMS.append("\n\n SENT MESSAGES");
            while (sent.moveToNext()){
                String to = sent.getString(inbox.getColumnIndexOrThrow("id"));
                String body2 = sent.getString(sent.getColumnIndexOrThrow("body"));
                SMS.append("\nTo: ").append(to).
                        append("\nMessage: ").append(body2).
                        append("............................................");
            }
        } sent.close();
    }
    public void readphonestate (){

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        int dataNetworkType = tmanager.getDataNetworkType(); //requires API 24
        int voiceNetworkType = tmanager.getVoiceNetworkType();
        String Imei = tmanager.getImei(); //requires API 26
        String Meid = tmanager.getMeid(); //requires API 26
        simCount = tmanager.getPhoneCount(); //requires API 23
        serviceState = tmanager.getServiceState(); //requires API 26;
        boolean dataState = tmanager.isDataEnabled(); //requires API 26
        }// requires API 24 */

        int phoneType = tmanager.getPhoneType();
        int DataState = tmanager.getDataState();
        String softwareVersion = tmanager.getDeviceSoftwareVersion();
        int simState = tmanager.getSimState();
        String simOperatorName = tmanager.getSimOperatorName();
        String simSerialNumber = tmanager.getSimSerialNumber();
        String subscriberID = tmanager.getSubscriberId();
        boolean wifiState = wifiManager.isWifiEnabled();

    }
}
