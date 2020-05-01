package com.hanyong.unitconvert2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;

import java.text.DecimalFormat;


public class Area extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //declare the spinner spinner position
    private static int from_option = 0;
    private static int to_option = 1;

    // all unit will be tranform to square inch first
    private static double from_rate = 0; // from_unit to square inch
    private static double to_rate = 0; // to_unit to square inch

    private String[] area_unit = {"square foot","square meter","square inch","square yard","acre","square mile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        //configure the spinner event listenner
        Spinner spinner_to = findViewById(R.id.sp_area_to);
        Spinner spinner_from = findViewById(R.id.sp_area_from);

        ArrayAdapter<String> adapter_from = new ArrayAdapter<String>(this,R.layout.my_spinner,area_unit);
        adapter_from.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter_from);
        spinner_from.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter_to = new ArrayAdapter<String>(this,R.layout.my_spinner,area_unit);
        adapter_to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(adapter_to);
        spinner_to.setSelection(1);                            // set default selection for the second spinner
        spinner_to.setOnItemSelectedListener(this);


        // set a Text-change listenner for 'from' line edittext element
        EditText et_form = findViewById(R.id.et_from);
        // set focus on the edittext
        et_form.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        et_form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        if(spinner.getId()==R.id.sp_area_from){

            //convert rate square meter
            switch (position){
                case 0: from_rate = 0.092903;
                    from_option = 0;
                    break;
                case 1: from_rate = 1;
                    from_option = 1;
                    break;
                case 2: from_rate = 0.00064516;
                    from_option = 2;
                    break;
                case 3: from_rate = 0.836127;
                    from_option = 3;
                    break;
                case 4: from_rate = 4046.86;
                    from_option = 4;
                    break;
                case 5: from_rate = 2.59e+6;
                    from_option = 5;
                    break;
            }
            calculate();
        }
        if(spinner.getId()==R.id.sp_area_to){

            //convert rate square meter
            switch (position){
                case 0:
                    to_rate = 0.092903;
                    to_option = 0;
                    break;
                case 1: to_rate = 1;
                    to_option = 1;
                    break;
                case 2: to_rate = 0.00064516;
                    to_option = 2;
                    break;
                case 3: to_rate = 0.836127;
                    to_option = 3;
                    break;
                case 4: to_rate = 4046.86;
                    to_option = 4;
                    break;
                case 5: to_rate = 2.59e+6;
                    to_option = 5;
                    break;
            }
            calculate();
        }

    }

    private void calculate() {
        EditText et_to = findViewById(R.id.et_to);
        EditText et_from = findViewById(R.id.et_from);

        // get user input
        String temp = et_from.getText().toString();
        double from_amount = 0;
        if(!"".equals(temp)) from_amount = Double.parseDouble(temp);

        //calculate the number should display in the 'to' line, and display it
        double to_amount = from_amount * from_rate / to_rate;
        DecimalFormat df = new DecimalFormat("#.00000");
        String temp1 = df.format(to_amount);
//         if the string is too long, the edittext in the 'to' line should get higher
        final LayoutParams layoutparams = (RelativeLayout.LayoutParams) et_to.getLayoutParams();
        if(temp1.length()>13){
            layoutparams.height = 140;
            et_to.setLayoutParams(layoutparams);
        }else{
            layoutparams.height = 90;
            et_to.setLayoutParams(layoutparams);
        }
        // display the result
        if(!"".equals(temp)){
            et_to.setText(temp1);
        }
        else {
            et_to.setText("");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void switch_unit(View view) {
        //switch the spinner selected option
        Spinner spinner_from = findViewById(R.id.sp_area_from);
        Spinner spinner_to = findViewById(R.id.sp_area_to);
        spinner_from.setSelection(to_option);
        spinner_to.setSelection(from_option);
        calculate();
    }
}
