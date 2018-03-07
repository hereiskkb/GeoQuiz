package com.training.hereiskkb.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalsButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
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
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.quiz_questions);
        updateQuestion();
        mQuestionTextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = ( mCurrentIndex + 1 ) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = ( mCurrentIndex + 1 ) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mPrevButton = (ImageButton) findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mCurrentIndex == 0 ) {
                    mCurrentIndex = mQuestionBank.length - 1 ;
                }
                else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }
                updateQuestion();
            }
        });
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalsButton = (Button) findViewById(R.id.false_button);
        mFalsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer( boolean ansValue ) {
        int result = 0;
        if( mQuestionBank[mCurrentIndex].isAnswerTrue() == ansValue ) {
            result = R.string.correct_toast;
        }
        else {
            result = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
