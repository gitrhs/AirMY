package com.example.airmy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.airmy.R;

public class CustomPopupDialog extends Dialog {

    private TextView titleTextView;

    public CustomPopupDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout); // Use your popup layout file

        // Initialize the contentTextView
        titleTextView = findViewById(R.id.textViewTitle); // Use the actual ID in your popup layout
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Close the popup when the back button is clicked
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Additional setup if needed
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }
    @Override
    public void onBackPressed() {
        dismiss(); // Close the popup when the back button is pressed
    }
}
