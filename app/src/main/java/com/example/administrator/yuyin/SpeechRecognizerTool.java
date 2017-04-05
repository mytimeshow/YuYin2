package com.example.administrator.yuyin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.baidu.speech.VoiceRecognitionService;

/**
 * Created by Administrator on 2017/3/31 0031.
 */

public class SpeechRecognizerTool implements RecognitionListener {

    public interface ResultsCallback {
        void onResults(String result);
    }

    private Context mContext;
    private SpeechRecognizer mSpeechRecognizer;
    private ResultsCallback mResultsCallback;

    public SpeechRecognizerTool(Context context) {
        mContext = context;
    }

    public synchronized void createTool() {
        if (null == mSpeechRecognizer) {
// 创建识别器

            mSpeechRecognizer =
                    SpeechRecognizer.createSpeechRecognizer(mContext, new
                            ComponentName(mContext, VoiceRecognitionService.class));
            // / 注册监听器
            mSpeechRecognizer.setRecognitionListener(this);
        }
    }

    public synchronized void destroyTool() {
        mSpeechRecognizer.stopListening();
        mSpeechRecognizer.destroy();
        mSpeechRecognizer = null;
    }

    // 开始识别
    public void startASR(ResultsCallback callback) {
        mResultsCallback = callback;
        Intent intent = new Intent();
        bindParams(intent);
        mSpeechRecognizer.startListening(intent);
    }

    public void stopASR() {
        mSpeechRecognizer.stopListening();
    }


    private void bindParams(Intent intent) {      // 设置识别参数
    }

    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(mContext, "请开始说话", Toast.LENGTH_SHORT).show();
    }


    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        // 最终结果处理
        if (mResultsCallback != null) {
            String text = results.get(SpeechRecognizer.RESULTS_RECOGNITION)
                    .toString().replace("]", "").replace("[", "");
            mResultsCallback.onResults(text);
        }
    }

    public void onPartialResults(Bundle partialResults) {

    }


    public void onEvent(int eventType, Bundle params) {

    }
}
