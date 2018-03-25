package com.training.hereiskkb.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE="com.training.hereiskkb.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.training.hereiskkb.geoquiz.answer_shown";
    private static final String TAG = "CheatActivity";
    private static final String SAVE_STATE = "save state status";
    private static final String SAVE_ANS = "save ans";

    private boolean mAnswerIsTrue;
    private boolean mAnswerShown = false;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private int mAns;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if( savedInstanceState != null ) {
            mAnswerShown = savedInstanceState.getBoolean(SAVE_STATE, false);
            mAnswerIsTrue = savedInstanceState.getBoolean(SAVE_ANS, false);
        }
        mAnswerTextView = findViewById(R.id.answer_text_view);
        if( mAnswerShown ) {
            if(mAnswerIsTrue) {
                mAns = R.string.true_button;
            }
            else {
                mAns = R.string.false_button;
            }
            showAnswer(mAns);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue) {
                    mAns = R.string.true_button;
                }
                else {
                    mAns = R.string.false_button;
                }
                showAnswer(mAns);
                setAnswerShowResult(true);
            }
        });
    }

    private void showAnswer(int textResValue) {
        mAnswerTextView.setText(textResValue);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putBoolean(SAVE_STATE, mAnswerShown);
        savedInstanceState.putBoolean(SAVE_ANS, mAnswerIsTrue);
    }

    private void setAnswerShowResult(boolean isAnswerShown) {
        mAnswerShown = isAnswerShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}