package com.example.schedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.schedule.ExampleAppWidgetProvider;
public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView widgetView;

    Button button;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.current_image);
        button = (Button)findViewById(R.id.set_image_button);
        widgetView = (ImageView)findViewById(R.id.scrImage);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK &&  requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
//            widgetView.setImageURI(imageUri);

//            widgetView.setImageResource();

            ExampleAppWidgetProvider EP = new ExampleAppWidgetProvider(ExampleAppWidgetProvider.context);
//            EP.aaa(imageUri);
        }
    }

}
