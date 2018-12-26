package com.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mangalorexpress.com.R;

public class Time_Up extends AppCompatActivity {
    Button playAgainButton;
    TextView timeUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time__up);
        //Initialize
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        timeUpText = (TextView)findViewById(R.id.timeUpText);

        //play again button onclick listener
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Time_Up.this,MainGameActivity.class);
                startActivity(intent);
                finish();


            }
        });


        //Setting typefaces for textview and button - this will give stylish font on textview and button
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shablagooital.ttf");
//        timeUpText.setTypeface(typeface);
//        playAgainButton.setTypeface(typeface);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
