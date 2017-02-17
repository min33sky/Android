package com.example.heo_mh.miniproject1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Heo-MH on 2017-02-17.
 */

public class CalculatorLayout extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calculator_layout, container, false);

        Button addButton = (Button) view.findViewById(R.id.addButton);
        Button subButton = (Button) view.findViewById(R.id.subtractButton);
        Button mulButton = (Button) view.findViewById(R.id.multiplyButton);
        Button divButton = (Button) view.findViewById(R.id.divideButton);

        // 더하기
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input1 = (EditText) view.findViewById(R.id.number1);
                EditText input2 = (EditText) view.findViewById(R.id.number2);
                int num1 = Integer.parseInt(input1.getText().toString());
                int num2 = Integer.parseInt(input2.getText().toString());
                TextView result = (TextView) view.findViewById(R.id.result);
                result.setText(Integer.toString(num1 + num2));
            }
        });

        // 빼기
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input1 = (EditText) view.findViewById(R.id.number1);
                EditText input2 = (EditText) view.findViewById(R.id.number2);
                int num1 = Integer.parseInt(input1.getText().toString());
                int num2 = Integer.parseInt(input2.getText().toString());
                TextView result = (TextView) view.findViewById(R.id.result);
                result.setText(Integer.toString(num1 - num2));
            }
        });

        // 곱하기
        mulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input1 = (EditText) view.findViewById(R.id.number1);
                EditText input2 = (EditText) view.findViewById(R.id.number2);
                int num1 = Integer.parseInt(input1.getText().toString());
                int num2 = Integer.parseInt(input2.getText().toString());
                TextView result = (TextView) view.findViewById(R.id.result);
                result.setText(Integer.toString(num1 * num2));
            }
        });

        // 나누기
        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input1 = (EditText) view.findViewById(R.id.number1);
                EditText input2 = (EditText) view.findViewById(R.id.number2);
                int num1 = Integer.parseInt(input1.getText().toString());
                int num2 = Integer.parseInt(input2.getText().toString());
                TextView result = (TextView) view.findViewById(R.id.result);
                result.setText(Integer.toString(num1 / num2));
            }
        });




        return view;
    }
}
