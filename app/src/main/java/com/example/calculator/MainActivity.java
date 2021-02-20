package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1=null;
    private String pendingOperation="=";
    private Double previousAnswer=0.0;
    private final String STATE_PENDING_OPERATION="text content";
    private final String STATE_OPERAND1="OPERAND1";
    private final String STATE_PREVIOUSANSWER="PREV ANS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result=(EditText)findViewById(R.id.result);
        newNumber=(EditText)findViewById(R.id.newNumber);
        displayOperation=(TextView)findViewById(R.id.operation);

        Button button0=(Button)findViewById(R.id.button0);
        Button button1=(Button)findViewById(R.id.button1);
        Button button2=(Button)findViewById(R.id.button2);
        Button button3=(Button)findViewById(R.id.button3);
        Button button4=(Button)findViewById(R.id.button4);
        Button button5=(Button)findViewById(R.id.button5);
        Button button6=(Button)findViewById(R.id.button6);
        Button button7=(Button)findViewById(R.id.button7);
        Button button8=(Button)findViewById(R.id.button8);
        Button button9=(Button)findViewById(R.id.button9);
        Button buttonDot=(Button)findViewById(R.id.buttonDot);
        Button buttonNeg=(Button)findViewById(R.id.buttonNeg);
        Button buttonC=(Button)findViewById(R.id.buttonC);
        Button buttonAns=(Button)findViewById(R.id.buttonAns);
        Button buttonDel=(Button)findViewById(R.id.buttonDel);

        Button buttonEquals=(Button)findViewById(R.id.buttonEquals);
        Button buttonDivide=(Button)findViewById(R.id.buttonDivide);
        Button buttonMultiply=(Button)findViewById(R.id.buttonMultiply);
        Button buttonMinus=(Button)findViewById(R.id.buttonMinus);
        Button buttonPlus=(Button)findViewById(R.id.buttonAdd);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b=(Button)v;
                if(b.getText().toString().equals("ANS")){
                    newNumber.append(previousAnswer.toString());
                }
                else
                    newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
        buttonAns.setOnClickListener(listener);

        View.OnClickListener opListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b=(Button) v;
                String op=b.getText().toString();
                String value=newNumber.getText().toString();
                try {
                    Double doubleValue=Double.valueOf(value);
                    performOperation(doubleValue,op);
                } catch (NumberFormatException e){
                    newNumber.setText("");
                }
                pendingOperation=op;
                displayOperation.setText(pendingOperation);
            }
        };
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=newNumber.getText().toString();
                if(value.length()==0){
                    newNumber.setText("-");
                }
                else{
                    try {
                        Double doubleValue=Double.valueOf(value);
                        doubleValue*=-1;
                        newNumber.setText(doubleValue.toString());
                    }catch(NumberFormatException e){
                        //number was - or . previously
                        newNumber.setText("");
                    }
                }
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                displayOperation.setText("");
                newNumber.setText("");
                operand1=null;
            }
        });
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newNumber.getText().toString().length()>0){
                    newNumber.setText(newNumber.getText().toString().substring(0,newNumber.getText().toString().length()-1));
                }
            }
        });
    }
    private void performOperation(Double value,String operation){
        if(null==operand1) {
            operand1 = value;
        }
        else {
            if (pendingOperation.equals("="))
                pendingOperation = operation;
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
            }
        }
        previousAnswer=operand1;
        result.setText(operand1.toString());
        newNumber.setText("");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        displayOperation.setText(savedInstanceState.getString(STATE_PENDING_OPERATION));
        pendingOperation=savedInstanceState.getString(STATE_PENDING_OPERATION);
        try {
            operand1=Double.valueOf(savedInstanceState.getString(STATE_OPERAND1));
            previousAnswer=Double.valueOf(savedInstanceState.getString(STATE_PREVIOUSANSWER));
        } catch (NumberFormatException e){
            operand1=null;
            previousAnswer=0.0;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,displayOperation.getText().toString());
        outState.putString(STATE_OPERAND1,result.getText().toString());
        outState.putString(STATE_PREVIOUSANSWER,previousAnswer.toString());
        super.onSaveInstanceState(outState);
    }
}