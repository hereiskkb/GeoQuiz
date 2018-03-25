package com.training.hereiskkb.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String BUTTON_STATE = "state";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String CHEAT_STATUS = "cheat status";

    private boolean mState = true;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private boolean mIsCheater;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_india, false),
            new Question(R.string.question_andes, true)
    };
    private int mCurrentIndex = 0;
    private TextView mQuestionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mState = savedInstanceState.getBoolean(BUTTON_STATE, true);
            mIsCheater = savedInstanceState.getBoolean(CHEAT_STATUS, false);
        }

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent( QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = true;
                mCurrentIndex = ( mCurrentIndex + 1 ) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
                activateButtons(mState);
            }
        });
        mQuestionTextView = findViewById(R.id.quiz_questions);
        updateQuestion();

        mQuestionTextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = true;
                mCurrentIndex = ( mCurrentIndex + 1 ) % mQuestionBank.length;
                updateQuestion();
                activateButtons(mState);
            }
        });

        mTrueButton = findViewById(R.id.true_button );
        mFalseButton = findViewById(R.id.false_button);
        activateButtons(mState);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT ) {
            if( data == null ) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(BUTTON_STATE, mState);
        savedInstanceState.putBoolean(CHEAT_STATUS, mIsCheater);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void activateButtons(boolean localState) {
        mTrueButton.setEnabled(localState);
        mFalseButton.setEnabled(localState);
    }
    private void checkAnswer( boolean ansValue) {
        activateButtons(false);
        mState = false;
        int result;
        if(mIsCheater) {
            result = R.string.judgement_toast;
        }
        else {
            if( mQuestionBank[mCurrentIndex].isAnswerTrue() == ansValue ) {
                result = R.string.correct_toast;
            }
            else {
                result = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
