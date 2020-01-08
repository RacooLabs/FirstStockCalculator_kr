package com.racoo.firststockcalculator;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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



    long principal=0;
    int numbersday=0;
    BigDecimal rate = new BigDecimal("0");


    BigDecimal bigDecimal0;
    BigDecimal bigDecimal2;
    BigDecimal bigDecimal3;
    BigDecimal bigDecimal4;
    BigDecimal bigDecimal5;
    BigDecimal zero = new BigDecimal("0");



    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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



                try{

                    if(s != null && !TextUtils.isEmpty(s.toString())){
                        principal = Long.parseLong(s.toString().replaceAll(",",""));
                    } else {
                        principal = 0;
                    }


                } catch (NumberFormatException e){
                    Toast.makeText(getApplication(), "입력 값에 문제가 있습니다." ,Toast.LENGTH_LONG).show();
                    principal = 0;
                    TextInputEdit_principal.setText("");
                }






                if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)){ //이게 빈공간이 아니거나, 전이랑 같지 않으면 갱신을 해줍니다.
                    result = decimalFormat.format(parseDouble(s.toString().replaceAll(",","")));//이거 때문에 s자체를 바꿔버리네 씨발
                    TextInputEdit_principal.setText(result);
                    TextInputEdit_principal.setSelection(TextInputEdit_principal.length());
                }

                 // 원금계산을 위해 다시 콤마를 다 땜.

                RelativeLayout_btn_refresh.setEnabled(false); // 그리고 버튼을 비활성화/활성화 시키기 위한.
                TextView_result_money.setText("");

                if(principal != 0 && validation()) {

                    TextView_result_money.setText(decimalFormat.format(calculateStock()) + " 원");

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

                try{
                    if(s != null && !TextUtils.isEmpty(s.toString())){
                        numbersday = Integer.parseInt(s.toString());
                    } else {
                        numbersday = 0;
                    }
                } catch (NumberFormatException e){
                    Toast.makeText(getApplication(), "입력 값에 문제가 있습니다." ,Toast.LENGTH_LONG).show();
                    numbersday = 0;
                    TextInputEdit_number_days.setText("");
                }

                if(!TextUtils.isEmpty(s.toString())) { //이게 빈공간이 아니거나, 전이랑 같지 않으면 갱신을 해줍니다.
                    TextInputEdit_number_days.setSelection(TextInputEdit_number_days.length());
                }


                RelativeLayout_btn_refresh.setEnabled(false);
                TextView_result_money.setText("");




                if (numbersday != 0 && validation()){
                    TextView_result_money.setText(decimalFormat.format(calculateStock())+" 원");
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




                try{

                    if(s != null && !TextUtils.isEmpty(s.toString())){
                        rate = new BigDecimal(s.toString());
                    } else {
                        rate = zero;
                    }

                } catch (NumberFormatException e){
                    Toast.makeText(getApplication(), "입력 값에 문제가 있습니다." ,Toast.LENGTH_LONG).show();
                    rate = zero;
                    TextInputEdit_rate_return.setText("");
                }


                RelativeLayout_btn_refresh.setEnabled(false);
                TextView_result_money.setText("");


                if(!TextUtils.isEmpty(s.toString())) { //이게 빈공간이 아니거나, 전이랑 같지 않으면 갱신을 해줍니다.
                    TextInputEdit_rate_return.setSelection(TextInputEdit_rate_return.length());
                }

                if (!rate.equals(zero) && validation()){

                    TextView_result_money.setText(decimalFormat.format(calculateStock())+" 원");

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
                TextInputEdit_principal.setText("");
                TextInputEdit_rate_return.setText("");
                TextInputEdit_number_days.setText("");
            }

        });

    }


    public boolean validation(){

//        if (principal.length()>0 && rate.length()>0 && numbersday.length()>0) {
        if (principal>0 && rate.compareTo(zero)>0 && numbersday>0) {
            RelativeLayout_btn_refresh.setEnabled(true);
            RelativeLayout_btn_refresh.setClickable(true);

            Log.d("::::",Boolean.toString(rate.compareTo(zero)>0));

            return true;

        } else {

            RelativeLayout_btn_refresh.setEnabled(false);
            RelativeLayout_btn_refresh.setClickable(false);

            return false;
        }

    }


    public BigDecimal calculateStock() {

//        bigDecimal0 = new BigDecimal(Integer.parseInt(principal));
//        bigDecimal1 = new BigDecimal(Integer.parseInt(rate));
//        bigDecimal2 = new BigDecimal(Integer.parseInt(numbersday));
//        bigDecimal3 = new BigDecimal("100");
//        bigDecimal4 = new BigDecimal("1");
//        bigDecimal5 = new BigDecimal(Integer.parseInt(principal));


        bigDecimal0 = new BigDecimal(principal);
        bigDecimal2 = new BigDecimal(numbersday);
        bigDecimal3 = new BigDecimal("100");
        bigDecimal4 = new BigDecimal("1");
        bigDecimal5 = new BigDecimal(principal);

        for(int i = 0 ; i < numbersday ; i++){

            bigDecimal5 = bigDecimal4.add((rate.divide(bigDecimal3))).multiply(bigDecimal5);
        }

        return bigDecimal5.subtract(bigDecimal0).setScale(0, RoundingMode.FLOOR);

    }

}







