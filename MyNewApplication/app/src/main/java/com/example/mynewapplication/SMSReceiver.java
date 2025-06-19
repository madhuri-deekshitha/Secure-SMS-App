package com.example.mynewapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        StringBuilder str = new StringBuilder();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus == null) return;

            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
                str.append("From: ").append(msgs[i].getOriginatingAddress()).append("\n");
                str.append("Encrypted Message: ").append(msgs[i].getMessageBody()).append("\n");
            }

            // Display the encrypted message as a Toast
            Toast.makeText(context, str.toString(), Toast.LENGTH_LONG).show();

            // Optionally, decrypt the message here using your cipher functions
        }
    }
}
