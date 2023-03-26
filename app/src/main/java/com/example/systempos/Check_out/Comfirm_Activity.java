package com.example.systempos.Check_out;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.systempos.Card.CardData;
import com.example.systempos.CurrentDateHelper;
import com.example.systempos.Customer.CustomerData;
import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.Payment.PaymentData;
import com.example.systempos.R;
import com.example.systempos.Setting.SettingData;
import com.example.systempos.ViewModel.CheckOutViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityConfirmBinding;
import com.example.systempos.databinding.StoreAllCatogoryBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Comfirm_Activity extends AppCompatActivity {

    public static String SUBTOTAL = "SUBTOTAL";
    public static String EXCHANGE = "EXCHAGE";
    ActivityConfirmBinding binding;
    AutoCompleteTextView autoCompleteTextViewCustomer,autoCompleteTextViewPayment;
    List<CustomerData> customerData;
    TextView checkExchage,checkSubtotal,checkdiscountAmount,SubtotalAferDiscount_R,SubtotalAferDiscount_D,MoneyChange_D,MoneyChage_KH;
    EditText checkDiacount_P,confirm_Received_D;
    List<PaymentData> paymentData;

    CheckOutData checkOutData;
    CheckOutViewModel checkOutViewModel;


    String Subtotal,Exchage;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        autoCompleteTextViewCustomer = findViewById(R.id.Spinner_select_Customer);
        autoCompleteTextViewPayment = findViewById(R.id.Spinner_select_addPayment);

//        List<CardData> cardData = userDAO.getAllCard();



        checkExchage = findViewById(R.id.check_exchageMoney);
        checkSubtotal = findViewById(R.id.checkSubTotal);
        checkdiscountAmount = findViewById(R.id.checkdiscoountAmount);
        SubtotalAferDiscount_R = findViewById(R.id.check_Discount_R);
        SubtotalAferDiscount_D = findViewById(R.id.check_Discount_D);
        MoneyChange_D = findViewById(R.id.moneychange_D);
        MoneyChage_KH = findViewById(R.id.moneychange_KH);

        checkDiacount_P = findViewById(R.id.checkDiscount_p);

        confirm_Received_D = findViewById(R.id.Confirm_Receive_D);



        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();

        customerData =userDAO.getAllCustomerFuture();
        paymentData = userDAO.getAllPaymentFuture();

        LiveData<List<ExchangeData>> exchangeData = userDAO.getAllExchageLiveData();
        List<CardData> cardData = userDAO.getAllCard();
        List<SettingData> settingData = userDAO.getALLSettingFuture();

        checkOutData = new CheckOutData();

        checkOutViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CheckOutViewModel.class);
        checkOutViewModel.getAllCheckOutLiveData().observe(Comfirm_Activity.this, new Observer<List<CheckOutData>>() {
            @Override
            public void onChanged(List<CheckOutData> checkOutData) {
                if(checkOutData != null){

                }
            }
        });




        ArrayList<String> CustomerArrayList = new ArrayList<>();
        for(int i= 0; i< customerData.size(); i++){
            CustomerArrayList.add(customerData.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.store_spinner_cust,R.id.store_spinner_customer,CustomerArrayList);
        autoCompleteTextViewCustomer.setAdapter(arrayAdapter);

        ArrayList<String> PaymentArrayList = new ArrayList<>();
        for(int k = 0; k<paymentData.size(); k++){
            PaymentArrayList.add(paymentData.get(k).getPaymentType());
        }
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,R.layout.store_spinner_payment,R.id.store_spinner_paymnet,PaymentArrayList);
        autoCompleteTextViewPayment.setAdapter(arrayAdapter1);


        //get Extra sub total

        Intent intent = getIntent();

        Subtotal = intent.getStringExtra(SUBTOTAL);
        checkSubtotal.setText(Subtotal);


        //btn Caculate
        binding.btnCaculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double discount = 0;
                Double ChangeDollar;

                int i;
                double sum = 0.0;
                for (i = 0; i < cardData.size(); i++) {
                    sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));
                }

                try {
                    if(checkDiacount_P.getText().toString().isEmpty()){
                        SubtotalAferDiscount_D.setText(numberFormat(String.valueOf(sum)));
                        checkdiscountAmount.setText(" 0.0");
                        SubtotalAferDiscount_R.setText(" 0.0");
                        MoneyChage_KH.setText(" 0.0");
                        ChangeDollar = Double.parseDouble(confirm_Received_D.getText().toString());

                        //Change Dollar
                        double ChangeMoneyDollar =ChangeDollar - sum;
                        MoneyChange_D.setText(numberFormat(String.valueOf(ChangeMoneyDollar)));

                        //kh
//                        double ChangeKH = ChangeMoneyDollar * Chan
                        MoneyChage_KH.setText(formatting(String.valueOf(ChangeMoneyDollar)));

                    }
                    else{
                        discount = Double.parseDouble(checkDiacount_P.getText().toString());
                        double AfterDisc = Double.parseDouble(checkExchage.getText().toString());
                        double discount_price = discount / 100;
                        double discountTotal = sum - (sum * discount_price);

                        double  AfterDiscount_R = discountTotal * AfterDisc;

                        SubtotalAferDiscount_D.setText(numberFormat(String.valueOf(discountTotal)));

                        SubtotalAferDiscount_R.setText(formatting(String.valueOf(AfterDiscount_R)));
                        //show amount(%)
                        checkdiscountAmount.setText("%"+checkDiacount_P.getText());

                        try {

                            //Change Money Dollar
                            if(confirm_Received_D.getText().toString().isEmpty()){
                                MoneyChange_D.setText("0.0");
                                MoneyChage_KH.setText("0.0");

                            }else {
                                ChangeDollar = Double.parseDouble(confirm_Received_D.getText().toString());
                                double ChangeMoneyDollar = ChangeDollar - discountTotal;
                                MoneyChange_D.setText( numberFormat(String.valueOf(ChangeMoneyDollar)));

                                //kh
                               double ChangeKH = ChangeMoneyDollar * AfterDisc;
                                MoneyChage_KH.setText(formatting(String.valueOf(ChangeKH)));

                            }
                        }catch (Exception e){

                        }

                    }


                }catch (Exception e){

                }


            }
        });


        //btn check OUT
        binding.btncheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CustomerName = binding.SpinnerSelectCustomer.getText().toString();
                String PaymentType = binding.SpinnerSelectAddPayment.getText().toString();
                String ExchageToKH = binding.checkExchageMoney.getText().toString();
                String Discount_P = binding.checkDiscountP.getText().toString();
                String Subtotal = binding.checkSubTotal.getText().toString();
                String TotalAfterDisc_R =binding.checkDiscountR.getText().toString();
                String ToalAfterDisc_D = binding.checkDiscountD.getText().toString();
                String Receive_D = binding.ConfirmReceiveD.getText().toString();
                String Change_D = binding.moneychangeD.getText().toString();
                String Change_KH = binding.moneychangeKH.getText().toString();




                ArrayList<String> CheckPrice = new ArrayList<>();
                ArrayList<String> CheckAmount = new ArrayList<>();
                ArrayList<String> CheckQty = new ArrayList<>();
                ArrayList<String> CheckProName = new ArrayList<>();

                for (int k =0; k<cardData.size(); k++){

                    CheckQty.add(String.valueOf(cardData.get(k).getPro_cardQty()));
                    CheckPrice.add(String.valueOf(cardData.get(k).getPro_cardPrice()));
                    CheckAmount.add(String.valueOf(cardData.get(k).getPro_cardQty() * cardData.get(k).getPro_cardCost()));


                }
//                if(binding.checkDiscountP == 0){
//
//
//                }


                checkOutData.setCustomerName(CustomerName);
                checkOutData.setPaymentType(PaymentType);
                checkOutData.setExchageToKH(Double.parseDouble(ExchageToKH));
                checkOutData.setDiscountAmount(Double.parseDouble(Discount_P));
//
                checkOutData.setSubTotal(Double.parseDouble(Subtotal));

                checkOutData.setTotalAfterDollar(Double.parseDouble(ToalAfterDisc_D));
                checkOutData.setTotalAfterKhmer(Double.parseDouble(TotalAfterDisc_R));
                checkOutData.setReceive_D(Double.parseDouble(Receive_D));

                checkOutData.setChange_D(Double.parseDouble(Change_D));
                checkOutData.setChange_R(Double.parseDouble(Change_KH));

                checkOutData.setDate(CurrentDateHelper.getCurrentDate());

                checkOutData.setSubTotal(Double.parseDouble(Subtotal));

                checkOutViewModel.InsertCheckOut(checkOutData);



//
  //              CheckOutData checkOutData = new CheckOutData();
                checkOutData.setQty(CheckQty);
                checkOutData.setPrice(CheckPrice);
                checkOutData.setProductNames(CheckProName);
                checkOutData.setAmount(CheckAmount);


//
                CardData cardData1 = new CardData();
                 for (int i= 0; i<cardData.size(); i++){
//                     checkOutData.setProductNames(cardData.get(i).getPro_cardNameEng());

//                    checkOutData.setPrice(CheckPrice.get(i));
                 }



            }
        });

        //send data
        int i;
        double sum = 0.0;
        for (i = 0; i < cardData.size(); i++) {
            sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));
        }
        checkExchage.setText(String.valueOf(settingData.get(0).getStorrExchage()));
        checkdiscountAmount.setText("0");
        MoneyChange_D.setText("0.00");
        MoneyChage_KH.setText("0.00");




    }
    public static String numberFormat(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        return decimalFormat.format(Double.parseDouble(number));
    }

    public static String formatting(String number){
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return  decimalFormat.format(Double.parseDouble(number));
    }
}