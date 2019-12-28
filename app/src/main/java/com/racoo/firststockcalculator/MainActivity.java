package com.racoo.firststockcalculator;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText TextInputEdit_principal, TextInputEdit_number_days,
            TextInputEdit_rate_return;
    private TextView TextView_result_money;
    private RelativeLayout RelativeLayout_btn_refresh;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private String result="";
    String principal="", numbersday="", rate="";

    BigDecimal bigDecimal0;
    BigDecimal bigDecimal1;
    BigDecimal bigDecimal2;
    BigDecimal bigDecimal3;
    BigDecimal bigDecimal4;
    BigDecimal bigDecimal5;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //이식 성

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        setCom();


        MobileAds.initialize(this,  "ca-app-pub-7972968096388401~4930885563");

        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);


        RelativeLayout_btn_refresh.setEnabled(false);
        TextView_result_money.setText("");


        addTextChangedListenter();
        clear();


        }


    public void setCom(){

        setContentView(R.layout.activity_main_constraint);

        TextInputEdit_principal = findViewById(R.id.TextInputText_principal_set);
        TextInputEdit_number_days = findViewById(R.id.TextInputEdit_number_days_set);
        TextInputEdit_rate_return = findViewById(R.id.TextInputEdit_rate_return_set);
        TextView_result_money = findViewById(R.id.TextView_result_money);
        RelativeLayout_btn_refresh = findViewById(R.id.RelativeLayout_btn_refresh);
        mAdView = findViewById(R.id.adView);


    }

    public void addTextChangedListenter(){

        TextInputEdit_principal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if(!TextUtils.isEmpty(s.toString())&& !s.toString().equals(result)){
                    result = decimalFormat.format(parseDouble(s.toString().replaceAll(",","")));//이거 때문에 s자체를 바꿔버리네 씨발
                    TextInputEdit_principal.setText(result);
                    TextInputEdit_principal.setSelection(result.length());
                }

                principal = s.toString().replaceAll(",","");

                RelativeLayout_btn_refresh.setEnabled(false);
                TextView_result_money.setText("");

                if(principal != null && !principal.equals("")){

                    if(validation()) {

                        RelativeLayout_btn_refresh.setEnabled(validation());
                        RelativeLayout_btn_refresh.setClickable(validation());
                        TextView_result_money.setText(decimalFormat.format(calculateStock())+" 원");

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        TextInputEdit_number_days.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//
                RelativeLayout_btn_refresh.setEnabled(false);
                TextView_result_money.setText("");

                numbersday = s.toString();

                if (numbersday != null && !numbersday.equals("")){

                    if(validation()) {

                        RelativeLayout_btn_refresh.setEnabled(validation());
                        RelativeLayout_btn_refresh.setClickable(validation());
                        TextView_result_money.setText(decimalFormat.format(calculateStock())+" 원");
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        TextInputEdit_rate_return.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                RelativeLayout_btn_refresh.setEnabled(false);
                TextView_result_money.setText("");
//
                rate = s.toString();


                if (rate != null && !rate.equals("") ){

                    if(validation()) {

                        RelativeLayout_btn_refresh.setEnabled(validation());
                        RelativeLayout_btn_refresh.setClickable(validation());
                        TextView_result_money.setText(decimalFormat.format(calculateStock())+" 원");


                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

    }




    public void clear(){

        RelativeLayout_btn_refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextInputEdit_principal.setText(null);
                TextInputEdit_rate_return.setText(null);
                TextInputEdit_number_days.setText(null);
            }

        });

    }


    public boolean validation(){

        return principal.length()>0 && rate.length()>0 && numbersday.length()>0;

    }


    public BigDecimal calculateStock() {

        bigDecimal0 = new BigDecimal(String.valueOf(principal));

        bigDecimal1 = new BigDecimal(String.valueOf(rate));
        bigDecimal2 = new BigDecimal(String.valueOf(numbersday));
        bigDecimal3 = new BigDecimal("100");
        bigDecimal4 = new BigDecimal("1");
        bigDecimal5 = new BigDecimal(String.valueOf(principal));

        for(int i = 0 ; i < Integer.parseInt(numbersday) ; i++){

            bigDecimal5 = bigDecimal4.add((bigDecimal1.divide(bigDecimal3))).multiply(bigDecimal5);
        }

        return bigDecimal5.subtract(bigDecimal0).setScale(0, RoundingMode.FLOOR);

    }

}







