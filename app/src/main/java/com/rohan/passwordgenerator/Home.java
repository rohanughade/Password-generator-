package com.rohan.passwordgenerator;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Home extends Fragment {
    EditText length ,webname;
    CheckBox lower,upper,sym,num;
    AppCompatButton gen,reset,save,savePass;
    TextView result, warning, passwordresult;


    public Home() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_home, container, false);
        length = view.findViewById(R.id.leng);
        lower = view.findViewById(R.id.checkLowCas);
        upper = view.findViewById(R.id.checkUppCas);
        sym = view.findViewById(R.id.checkSym);
        num = view.findViewById(R.id.checkNum);
        gen = view.findViewById(R.id.genBtn);
        reset = view.findViewById(R.id.reset);
        result = view.findViewById(R.id.result);
        warning = view.findViewById(R.id.warning);
        save = view.findViewById(R.id.save);
        lower.setChecked(true);

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dailog);
        webname = dialog.findViewById(R.id.webname);
        savePass = dialog.findViewById(R.id.savePass);
        passwordresult = dialog.findViewById(R.id.passwordresult);
        Mydb mydb = new Mydb(getContext());






        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReset();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passresylt = result.getText().toString();

                if (TextUtils.isEmpty(passresylt)) {
                    Toast.makeText(getContext(), "No password generated to save!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();

                passwordresult.setText(passresylt);

                savePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String jk = webname.getText().toString();
                        mydb.addPass(jk,passwordresult.getText().toString());
                        dialog.dismiss();
                    }
                });

            }
        });

         return view;
    }
    public void last(){
        if (!isValid()) return;
        String PassString = generatePassString();
        if (PassString.isEmpty()){
            Toast.makeText(getContext(),"Please cheack at least one checkBox",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(length.getText().toString())) {
            Toast.makeText(getContext(), "Please enter a valid length", Toast.LENGTH_SHORT).show();
            return;
        }
        int passlen = Integer.parseInt(length.getText().toString());
        if (passlen<=0){
            Toast.makeText(getContext(),"Please enter some value",Toast.LENGTH_LONG).show();
            return;
        }


        String resup = generatePass(passlen,PassString);
        result.setText(resup);
        result.setBackgroundResource(R.drawable.search_back);
        Log.d("pass",resup);


    }

    public String generatePassString(){
        String PassString = "";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "1234567890";
        String Symbol = "!@#%&*_-+=-";
        if (lower.isChecked()){
            PassString += lowerCase;
        }
        if (upper.isChecked()){
            PassString += upperCase;
        }
        if (num.isChecked()){
            PassString += numbers;
        }
        if (sym.isChecked()){
            PassString += Symbol;
        }
        return PassString;



    }
    public String  generatePass(int passLength,String passString){
        String resul ="";
        for (int i = 0;i<passLength;i++){
            int ran = (int)(Math.random()*passString.length());
            resul += passString.charAt(ran);
        }
        return resul;
    }

    public void setReset(){
        lower.setChecked(true);
        upper.setChecked(false);
        num.setChecked(false);
        sym.setChecked(false);
        result.setText("");
        length.setText("");
        warning.setText("");
        result.setBackgroundResource(0);

    }
    public boolean isValid(){
        String input = length.getText().toString();
        if (TextUtils.isEmpty(input)){
            warning.setText("Please enter valid length");
            return false;
        }
        try {
            int passLen = Integer.parseInt(input);
            if (passLen<4 || passLen>16){
                warning.setText("length should between 4 and 16");
                return false;
            }
        }catch (NumberFormatException e){
            warning.setText("invalid input");
            return false;
        }
        warning.setText("");
        return true;
    }


}