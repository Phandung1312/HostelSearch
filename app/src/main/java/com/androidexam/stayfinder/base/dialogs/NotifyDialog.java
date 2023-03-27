package com.androidexam.stayfinder.base.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.text.HtmlCompat;

import com.androidexam.stayfinder.R;

public class NotifyDialog extends Dialog {
    private String title;
    private String message;
    private String textButton;

    public NotifyDialog(@NonNull Context context,String title, String message, String textButton) {
        super(context);
        this.title = title;
        this.message = message;
        this.textButton = textButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notify);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvContent = findViewById(R.id.tvContent);
        tvContent.setText(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));
        AppCompatButton btnOK = findViewById(R.id.btnOK);
        if(textButton != null){
            btnOK.setText(textButton);
        }
        btnOK.setOnClickListener(v -> dismiss());
    }
}
