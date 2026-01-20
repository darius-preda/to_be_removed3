package ro.pub.cs.systems.eim.practicaltestv03;

import android.os.Bundle;
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

    private void initializeViews() {
        userInputEditText = findViewById(R.id.input_word_edit_text);
        sendButton = findViewById(R.id.send_button);
        descriptionTextView = findViewById(R.id.description_text_view);
    }

    private void setupListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendButton();
            }
        });
    }

    public void handleSendButton() {
        String userInput = userInputEditText.getText().toString();
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitypractical_test02_main);

        initializeViews();
        setupListeners();
    }
}