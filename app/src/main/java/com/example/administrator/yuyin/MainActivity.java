package com.example.administrator.yuyin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SpeechRecognizerTool.ResultsCallback {
    private SpeechRecognizerTool mSpeechRecognizerTool = new SpeechRecognizerTool(this);
    private Button speech;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        speech = (Button) findViewById(R.id.speech);
        speech.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizerTool.startASR
                                (MainActivity.this);
                        speech.setBackgroundResource(
                                R.drawable.bdspeech_btn_orangelight_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizerTool.stopASR();
                        speech.setBackgroundResource(
                                R.drawable.bdspeech_btn_orangelight_normal);
                        break;
                    default:
                        return false;
                }
                return
                        true;
            }
        });
    }


    protected void onStop() {
        super.onStop();
        mSpeechRecognizerTool.destroyTool();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSpeechRecognizerTool.createTool();
    }

    public void onResults(String result) {
        final String finalResult = result;
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override       public void run() {
                textView.setText
                    (finalResult);
            }      });

    }
}
