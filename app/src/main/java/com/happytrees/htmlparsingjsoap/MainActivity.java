package com.happytrees.htmlparsingjsoap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    private Button getBtn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });
    }



    //In the getWebsite() method, we create a new Thread to download the content of the website.
    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.ssaurel.com/blog").get();
                    String title = doc.title();
                    Elements links = doc.select("a[href]");

                    builder.append(title).append("\n");

                    for (Element link : links) {
                        builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }



}

/*
In the getWebsite() method, we create a new Thread to download the content of the website.
 We use the connect() method of the Jsoup object to connect the application to the website,
 then we call the get() method to download the content. These calls return a Document object
  instance. We have to call the select() method of this instance with the query to get all
  the links of the content. This query returns an Elements instance and finally, we have just
   to iterate on the elements contained in this object to display the content of each link to
    the screen.At the end of our separated Thread, we refresh the UI with the links got from the website.
     This refresh is embedded inside a runOnUiThread call because it’s forbidden to refresh the UI elements
      inside a separated thread.
 */