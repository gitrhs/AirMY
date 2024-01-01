package com.example.airmy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.airmy.R;

public class CustomPopupDialog extends Dialog {

    private TextView titleTextView, textViewInformation, textViewContent;

    public CustomPopupDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);

        // Initialize the contentTextView
        titleTextView = findViewById(R.id.textViewTitle);
        textViewInformation = findViewById(R.id.textViewInformation);
        textViewContent = findViewById(R.id.textViewContent);
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
    public void setDesc(String desc) {
        textViewInformation.setText(desc);
    }
    public void setContent(String content) {
        textViewContent.setText(content);
    }
    @Override
    public void onBackPressed() {
        dismiss(); // Close the popup when the back button is pressed
    }
}
