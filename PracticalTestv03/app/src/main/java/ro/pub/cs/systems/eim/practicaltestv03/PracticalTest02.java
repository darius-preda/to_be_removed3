package ro.pub.cs.systems.eim.practicaltestv03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest02 extends AppCompatActivity {

    private EditText userInputEditText = null;
    private Button sendButton = null;
    private TextView descriptionTextView = null;

    private Button timeButton = null;

    private void initializeViews() {
        userInputEditText = findViewById(R.id.input_word_edit_text);
        sendButton = findViewById(R.id.send_button);
        descriptionTextView = findViewById(R.id.description_text_view);
        timeButton = findViewById(R.id.time_button);
    }

    private void setupListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendButton();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });
    }

    public void handleSendButton() {
        String userInput = userInputEditText.getText().toString();
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT).show();
            return;
        }
        Client client = new Client(userInput, getApplicationContext());
        client.start();
    }

    public void handleTimeButton() {
        String serverIp = "10.0.2.2";
        TimeClient timeClient = new TimeClient(serverIp, getApplicationContext());
        timeClient.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitypractical_test02_main);

        initializeViews();
        setupListeners();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BroadcastReceiver", "onReceive apelat");
            if (Constants.ACTION_AUTOCOMPLETE.equals(intent.getAction())) {
                String receivedData = intent.getStringExtra(Constants.DATA_KEY);
                if (descriptionTextView != null) {
                    descriptionTextView.setText(receivedData);
                }
                Toast.makeText(context, "Primit: " + receivedData, Toast.LENGTH_SHORT).show();
            } else if ("ro.pub.cs.systems.eim.practicaltestv03.TIME_ACTION".equals(intent.getAction())) {
                String time = intent.getStringExtra("time");
                if (descriptionTextView != null) {
                    descriptionTextView.setText(time);
                }
                Toast.makeText(context, "Timp primit: " + time, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_AUTOCOMPLETE);
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltestv03.TIME_ACTION");
        registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}