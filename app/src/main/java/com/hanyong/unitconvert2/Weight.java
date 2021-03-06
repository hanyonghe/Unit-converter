package com.hanyong.unitconvert2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Weight extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //declare the spinner spinner position
    private static int from_option = 0;
    private static int to_option = 1;
    private static boolean isFragmentShow = false;

    private static String fragement_text = "";
    // all unit will be tranform to square inch first
    private static double from_rate = 0; // from_unit to square inch
    private static double to_rate = 0; // to_unit to square inch

    private String[] weight_unit = {
            "metric ton","kilogram","gram","miligram","microgram","US ton",
            "stone","pound","ounce"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        final RecordDB db = new RecordDB(this);
        //configure the spinner event listenner
        Spinner spinner_to = findViewById(R.id.sp_weight_to);
        Spinner spinner_from = findViewById(R.id.weight_spinner_from);

        ArrayAdapter<String> adapter_from = new ArrayAdapter<String>(this,R.layout.my_spinner,weight_unit);
        adapter_from.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter_from);
        spinner_from.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter_to = new ArrayAdapter<String>(this,R.layout.my_spinner,weight_unit);
        adapter_to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(adapter_to);
        spinner_to.setSelection(1);                // set default selection for the second spinner
        spinner_to.setOnItemSelectedListener(this);

        // set a Text-change listenner for 'from' line edittext element
        EditText et_form = findViewById(R.id.weight_editText_from);
        // set focus on the edittext
        et_form.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        et_form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                calculate_from();
            }
        });
        //The toggle button control the visibility of the fragment
        ToggleButton toggle = findViewById(R.id.weight_toggleButton_history);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showHistory();
                } else {
                    // hide the record
                    history_fragment fragment = new history_fragment();
                    getSupportFragmentManager().beginTransaction().remove(fragment);
                    LinearLayout ll = findViewById(R.id.weight_fragment_layout);
                    ll.setVisibility(LinearLayout.INVISIBLE);
                    isFragmentShow = false;
                }
            }

        });

    }

    private void showHistory() {
        //get records from database
        RecordDB db = new RecordDB(this);
        ArrayList<Record> records = db.getRecord();
        StringBuilder history = new StringBuilder();
        if(records.size()!=0) {
            for (Record record : records) {
                history.append(record.getFrom_amount()).append(" "+record.getFrom_unit()).append(" = ").
                        append(record.getTo_amount()).append(" "+record.getTo_unit()).append(";\n");
                Log.d("retrieve",record.getTo_unit());
            }
        }
        fragement_text = history.toString();

        //sent the records to fragment.
        history_fragment fragment = new history_fragment();
        Bundle bundle = new Bundle();
        bundle.putString("record",fragement_text);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.weight_fragment_container,fragment).commit();

        LinearLayout ll = findViewById(R.id.weight_fragment_layout);
        ll.setVisibility(LinearLayout.VISIBLE);
        isFragmentShow = true;
    }

    //handle when the user pick a option form the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        if(spinner.getId()==R.id.weight_spinner_from){

            //convert rate kilogram
            switch (position){
                case 0: from_rate = 0.001;
                    from_option = 0;
                    break;
                case 1: from_rate = 1;
                    from_option = 1;
                    break;
                case 2: from_rate = 1000;
                    from_option = 2;
                    break;
                case 3: from_rate = 1e+6;
                    from_option = 3;
                    break;
                case 4: from_rate = 1e+9;
                    from_option = 4;
                    break;
                case 5: from_rate = 0.00110231;
                    from_option = 5;
                    break;
                case 6: from_rate = 0.157473;
                    from_option = 6;
                    break;
                case 7: from_rate = 2.20462;
                    from_option = 7;
                    break;
                case 8: from_rate = 35.274;
                    from_option = 8;
                    break;
            }
            calculate_from();
        }
        if(spinner.getId()==R.id.sp_weight_to){

            //convert rate kilogram
            switch (position){
                case 0: to_rate = 0.001;
                    to_option = 0;
                    break;
                case 1: to_rate = 1;
                    to_option = 1;
                    break;
                case 2: to_rate = 1000;
                    to_option = 2;
                    break;
                case 3: to_rate = 1e+6;
                    to_option = 3;
                    break;
                case 4: to_rate = 1e+9;
                    to_option = 4;
                    break;
                case 5: to_rate = 0.00110231;
                    to_option = 5;
                    break;
                case 6: to_rate = 0.157473;
                    to_option = 6;
                    break;
                case 7: to_rate = 2.20462;
                    to_option = 7;
                    break;
                case 8: to_rate = 35.274;
                    to_option = 8;
                    break;
            }
            calculate_from();
        }

    }

    private void calculate_from() {
        EditText et_to = findViewById(R.id.weight_editText_to);
        EditText et_from = findViewById(R.id.weight_editText_from);

        // get user input
        String temp = et_from.getText().toString();
        double from_amount = 0;
        if(!"".equals(temp)) from_amount = Double.parseDouble(temp);

        //calculate the number should display in the 'to' line, and display it
        double to_amount = from_amount * to_rate / from_rate;
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
        Spinner spinner_from = findViewById(R.id.weight_spinner_from);
        Spinner spinner_to = findViewById(R.id.sp_weight_to);
        spinner_from.setSelection(to_option);
        spinner_to.setSelection(from_option);
        calculate_from();
    }

    public void clear_input(View view) {
        EditText et_from = findViewById(R.id.weight_editText_from);
        et_from.setText("");
        calculate_from();
        clearDB();
        if(isFragmentShow = true){
            showHistory();
        }
    }

    public void save_result(View view) {
        try{
        calculate_from();
        double from_amount=0,to_amount=0;
        EditText et_from = findViewById(R.id.weight_editText_from);
        EditText et_to = findViewById(R.id.weight_editText_to);
        String text_from = et_from.getText().toString();
        String text_to  = et_to.getText().toString();
            if (text_from == "" | text_to == "") {
                Toast.makeText(this, "make a convertion before saving", Toast.LENGTH_SHORT).show();
                throw new NullPointerException("Invalid empty input");
            }
        from_amount = Double.parseDouble(text_from);
        to_amount = Double.parseDouble(text_to);
        Record record = new Record(from_amount,to_amount,weight_unit[from_option],weight_unit[to_option]);
        RecordDB db = new RecordDB(this);
        long insertId = db.insertRecord(record);
        if(insertId > 0){
            Toast.makeText(this, "Record saved", Toast.LENGTH_SHORT).show();
            showHistory();
        }else{
            Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show();
        }
        }catch (Exception e){
            Log.d("Exception_are",e.getMessage());
        }
    }

    public void clearDB(){
        RecordDB db = new RecordDB(this);
        db.deleteRecords();
        }


}
