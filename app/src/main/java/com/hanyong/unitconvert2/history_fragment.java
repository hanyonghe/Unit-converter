package com.hanyong.unitconvert2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
public class history_fragment extends Fragment {
    private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_history, container, false);
        textView = returnView.findViewById(R.id.tv_history);
     String temp = "";
     try {
         assert getArguments() != null;
         if(getArguments().getString("record")!=null){
             temp = getArguments().getString("record");}
                getArguments().remove("record");
     }catch (Exception ignored) {}
     textView.setText(temp);
        // this function is keep failing because I can not call the new Area class
     // it would be a good idea to move this button the the area activity but it will take too much space.

//        Button bt = returnView.findViewById(R.id.bt_clare_history);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                (new Area()).clearDB();
//            }
//        });
        return returnView;
    }

}

