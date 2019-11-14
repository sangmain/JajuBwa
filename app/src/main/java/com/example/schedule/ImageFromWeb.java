package com.example.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ImageFromWeb extends AppCompatActivity {

    private EditText editTextURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_from_web);
        editTextURL = findViewById(R.id.tb_weburl);

    }

    public void onConfirmClick(View v){

        String url = editTextURL.getText().toString();

        if (url.length() == 0){
            Log.d("sangmin", "string is null");
            finish();
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("getURL", url);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
