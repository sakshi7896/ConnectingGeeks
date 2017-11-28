package com.learnit.sakshi.connectinggeeksapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView t= (TextView)findViewById(R.id.textview);
        Button b1 = (Button) findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        final Activity activity=this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.setText("Button Pressed345");

                Intent intent = new Intent(activity, Activity1.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(activity,Activity2.class);
                startActivity(i);
            }
        });
    }
}
