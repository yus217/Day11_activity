package com.example.yuecheng.day11_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageDownloadTask task =  new ImageDownloadTask();
        Bitmap result;

        EditText edt = (EditText) findViewById(R.id.editText);
        //edt.getText();

        try {
            task.execute("https://en.wikipedia.org/wiki/Chrysanthemum#/media/File:Chrysanthemum_sp.jpg").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            Bitmap image = null;

            try {
                url = new URL(strings[0]);
                //open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //connect
                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();
                image = BitmapFactory.decodeStream(stream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap image) {

            ImageView img = (ImageView) findViewById(R.id.imageView);
            img.setImageBitmap(image);

        }
    }


}

