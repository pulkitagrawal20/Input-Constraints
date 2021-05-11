package com.example.android.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.example.android.inputconstraints.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InputConstraintsActivity extends AppCompatActivity {
    //Request code for transfer the data from one to another activity:
    private static final int REQUEST_CODE = 0;
    ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        SetUpOnClickListener();

        HideError();

    }
    //OnClick Listener for button:
    private void SetUpOnClickListener() {
        b.TakeInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TakeInput();
            }
        });
    }
    //Hiding Error After Correction:
    private void HideError() {
        //Initializing the state change listener:
        CompoundButton.OnCheckedChangeListener myListener= new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                b.resultText.setVisibility(View.GONE);
            }
        };
        //attaching listener to all classes:
        b.UpperCaseBox.setOnCheckedChangeListener(myListener);
        b.LowerCaseBox.setOnCheckedChangeListener(myListener);
        b.DigitsBox.setOnCheckedChangeListener(myListener);
        b.MathOperationsBox.setOnCheckedChangeListener(myListener);
        b.OtherSymbols.setOnCheckedChangeListener(myListener);
    }
    //To open the input Activity on the basis of Constraints:
    private void TakeInput() {

        int[] type= {0,0,0,0,0};

        //Check for the Selection of check boxes
        if(!(b.UpperCaseBox.isChecked() || b.LowerCaseBox.isChecked() || b.DigitsBox.isChecked() || b.MathOperationsBox.isChecked() || b.OtherSymbols.isChecked())){
         b.resultText.setTextColor(getResources().getColor(R.color.red));
          b.resultText.setVisibility(View.VISIBLE);
          b.resultText.setText("Please Select At Least One Box!!");
          return;
        }

        //Make array according to selected check box:
        if(b.UpperCaseBox.isChecked())
            type[0] += 1;
        if(b.LowerCaseBox.isChecked())
            type[1] += 1;
        if(b.DigitsBox.isChecked())
            type[2] += 1;
        if(b.MathOperationsBox.isChecked())
            type[3] += 1;
        if(b.OtherSymbols.isChecked())
            type[4] += 1;

        Intent intent=new Intent(this,InputActivity.class);
        intent.putExtra(Constants.CONSTRAINTS,type);

        //Start input activity and getting the Result:
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Checking Result:
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){

            b.resultText.setTextColor(Color.GREEN);
            b.resultText.setText("Result is: " + data.getStringExtra(Constants.RESULT_STRING));
        }
        else {
            // set the color to red and set the error string
            b.resultText.setTextColor(Color.RED);
            b.resultText.setText("No data received!");
        }
        b.resultText.setVisibility(View.VISIBLE);


    }
}