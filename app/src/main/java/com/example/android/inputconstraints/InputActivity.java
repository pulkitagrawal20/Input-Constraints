package com.example.android.inputconstraints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.android.inputconstraints.databinding.ActivityInputBinding;

public class InputActivity extends AppCompatActivity {
    ActivityInputBinding b;

    //Storing the Generated Regular Expression(Regex):
    StringBuilder regExpression=new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b=ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        //Title of the Activity:
        setTitle("Inputs");

        SetUpOnClickListener();

        //Getting Constraints:
        getConstraints();

        setupHideError();
    }
    //To set the Onclick listener for the button:
    private void SetUpOnClickListener() {
        b.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendText();
            }
        });
    }

    //Hiding the Error of the Text Field when Text Changes:
    private void setupHideError() {
        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        b.inputText.getEditText().addTextChangedListener(myTextWatcher);
    }

    private void getConstraints() {
        //initializing Array of the Constraints:
        int[] type=getIntent().getExtras().getIntArray(Constants.CONSTRAINTS);

        //Making Regex on the basis of selected check box:
       regExpression.append("^[");
        if(type[0]==1){
            regExpression.append("A-Z");
        }
        if(type[1]==1){
            regExpression.append("a-z");
        }
        if(type[2]==1){
            regExpression.append("0-9");
        }
        if(type[3]==1){
            regExpression.append("+\\-*/%");
        }
        if(type[4]==1){
            regExpression.append("@#$&!*=");
        }
        regExpression.append("]*");
    }

    //send input back to the Parent Activity:
    private void SendText() {

        String text=b.inputText.getEditText().getText().toString().trim();

        //Making sure text should not empty:
        if(text.isEmpty()){
            b.inputText.setError("Please Enter An Expression!!");
            return;
        }

        //making sure Text matches to the regex:
        else if(!text.matches(regExpression.toString())){
            b.inputText.setError("Please Enter a valid Text!!");
            return;
        }

        //Sending Result back to the Parent Activity:
        Intent intent=new Intent();
        intent.putExtra(Constants.RESULT_STRING,text);
        setResult(RESULT_OK,intent);

        //Closing Activity:
        finish();
    }

    //Hiding Errors on changing text:
    private void hideError() {
        b.inputText.setError(null);
    }
}