package com.example.climateSim;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class QuestionActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    //int qid = 0;
    Random generator = new Random();
    int qid = generator.nextInt(20);
    int answered = 0;

    Question currentQ;
    TextView txtQuestion, scored;
    Button button1, button2;
    Button endquiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        QuizHelper db = new QuizHelper(this);  // my question bank class
        quesList = db.getAllQuestions();  // this will fetch all quiz questions
        currentQ = quesList.get(qid); // the current question

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        // the textview in which the question will be displayed

        // the three buttons,
        // the idea is to set the text of three buttons with the options from question bank
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        endquiz = (Button) findViewById(R.id.endquiz);

        // the textview in which score will be displayed
        scored = (TextView) findViewById(R.id.score);

        // method which will set the things up for the quiz
        setQuestionView();

        // button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // passing the button text to other method
                // to check whether the answer is correct or not
                // same for all three buttons
                getAnswer(button1.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button2.getText().toString());
            }
        });

        endquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if over do this
                Intent intent = new Intent(QuestionActivity.this,
                        ResultActivity.class);
                Bundle b = new Bundle();
                b.putInt("score", score); // Your score
                intent.putExtras(b); // Put your score to your next
                startActivity(intent);
                finish();
            }
        });

    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {

            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score+=100;
            scored.setText("Score: " + score);
            //toast
            Context context = getApplicationContext();
            CharSequence text = "You have earned 1k dollar!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            //toast
            Context context = getApplicationContext();
            CharSequence text = "Oops, the answer is incorrect!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        if (answered < 5) {

            // if questions are not over then do this
            currentQ = quesList.get(qid);
            setQuestionView();
        } else {

            // if over do this
            Intent intent = new Intent(QuestionActivity.this,
                    ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivityForResult(intent,1);
 
        }
 
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1)
        {
            Intent i = new Intent();
            int earnMoney = data.getIntExtra("Money",0);
            i.putExtra("earnMoney",earnMoney);
            setResult(5,i);                 
            finish();
        }

    }

    private void setQuestionView() {

        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        answered ++;
        Random generator = new Random();
        qid = generator.nextInt(20);
    }

}
