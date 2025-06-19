package com.example.mynewapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextPhone, editTextMessage, editTextKey;
    Spinner encryptionSpinner, decryptionSpinner;
    Button btnEncrypt, btnDecrypt, btnSend;
    TextView tvEncrypted, tvDecrypted;

    String encryptedMessage = "";
    String decryptedMessage = "";

    String[] techniques = {"Select the technique", "Ceaser Cipher", "Rail Fence Cipher", "Playfair Cipher"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhone = findViewById(R.id.phoneNumber);
        editTextMessage = findViewById(R.id.message);
        editTextKey = findViewById(R.id.encryptionKey);
        encryptionSpinner = findViewById(R.id.encryptionTechniqueSpinner);
        decryptionSpinner = findViewById(R.id.decryptionTechniqueSpinner);
        btnEncrypt = findViewById(R.id.encryptButton);
        btnDecrypt = findViewById(R.id.decryptButton);
        btnSend = findViewById(R.id.sendEncryptedSmsButton);
        tvEncrypted = findViewById(R.id.encryptedLabel);
        tvDecrypted = findViewById(R.id.decryptedLabel);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, techniques) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(android.R.color.white));
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        encryptionSpinner.setAdapter(adapter);
        decryptionSpinner.setAdapter(adapter);

        btnEncrypt.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString();
            String key = editTextKey.getText().toString();
            String selected = encryptionSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(message) || TextUtils.isEmpty(key) || selected.equals("Select the technique")) {
                Toast.makeText(MainActivity.this, "Enter message, key, and select technique", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                switch (selected) {
                    case "Ceaser Cipher":
                        int shift = Integer.parseInt(key);
                        encryptedMessage = CaesarCipher.encrypt(message, shift);
                        break;
                    case "Rail Fence Cipher":
                        int rails = Integer.parseInt(key);
                        encryptedMessage = RailFenceCipher.encrypt(message, rails);
                        break;
                    case "Playfair Cipher":
                        encryptedMessage = PlayfairCipher.encrypt(message, key);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Invalid encryption technique", Toast.LENGTH_SHORT).show();
                        return;
                }
                tvEncrypted.setText("Encrypted Message: " + encryptedMessage);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Encryption Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnDecrypt.setOnClickListener(v -> {
            String message = encryptedMessage;
            if (TextUtils.isEmpty(message)) {
                message = editTextMessage.getText().toString();
            }
            String key = editTextKey.getText().toString();
            String selected = decryptionSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(message) || TextUtils.isEmpty(key) || selected.equals("Select the technique")) {
                Toast.makeText(MainActivity.this, "Enter message, key, and select technique", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                switch (selected) {
                    case "Ceaser Cipher":
                        int shift = Integer.parseInt(key);
                        decryptedMessage = CaesarCipher.decrypt(message, shift);
                        break;
                    case "Rail Fence Cipher":
                        int rails = Integer.parseInt(key);
                        decryptedMessage = RailFenceCipher.decrypt(message, rails);
                        break;
                    case "Playfair Cipher":
                        decryptedMessage = PlayfairCipher.decrypt(message, key);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Invalid decryption technique", Toast.LENGTH_SHORT).show();
                        return;
                }
                tvDecrypted.setText("Decrypted Message: " + decryptedMessage);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Decryption Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnSend.setOnClickListener(v -> {
            String phone = editTextPhone.getText().toString();

            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(encryptedMessage)) {
                Toast.makeText(MainActivity.this, "Enter phone number and encrypt a message", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            } else {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, encryptedMessage, null, null);
                Toast.makeText(MainActivity.this, "SMS Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
