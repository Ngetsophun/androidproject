package com.example.systempos.Card;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.systempos.Check_out.CheckOutData;
import com.example.systempos.Check_out.Comfirm_Activity;
import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.Product.ProductData;
import com.example.systempos.R;
import com.example.systempos.Setting.SettingData;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityCardBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {


    ActivityCardBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;

    RecyclerView recyclerView;

    TextView txSubtotal, txtotalAmount, txSubtotal_real,txDiscount_R,txDiscount_D,txCardTax;
    TextView orderSubtotal;

    TextView Exchange;
    String subTotal;


    EditText discount_p;
    double discount;


    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context;





        recyclerView = findViewById(R.id.recycler_CardList);

        txSubtotal = findViewById(R.id.SubTotal);
        discount_p = findViewById(R.id.checkDiscount_p);

        txDiscount_D = findViewById(R.id.check_Discount_D);
        txDiscount_R = findViewById(R.id.check_Discount_R);


//        orderSubtotal = findViewById(R.id.order_subtotal);

//
//        //dialog to print
//        dialog = new Dialog(CardActivity.this);
//        dialog.setContentView(R.layout.costom_dialog_save);
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.back_ground_dialog_save));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//
//        Button btncash  = dialog.findViewById(R.id.btncash);
//        Button btncancel = dialog.findViewById(R.id.btncancel);
//
//
//        TextView dialog_real = dialog.findViewById(R.id.dialog_Subtotal_Real);
//        TextView dialog_dollar = dialog.findViewById(R.id.dialog_Subtotal_Dollar);
//
//        btncancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(CardActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        btncash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent = new Intent(CardActivity.this,Invoiceitems.class);
//               startActivity(intent);
//
//
//            }
//        });


        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();

        List<ProductData> productData1 = userDAO.getAllProductFuture();

        List<CardData> cardData = userDAO.getAllCard();

        List<ExchangeData> exchangeData = userDAO.getAllExchageFuture();

        List<CheckOutData> checkOutData = userDAO.getAllCheckOutFuture();




        Card_Adapter cardAdapter = new Card_Adapter(cardData,productData1,checkOutData,getApplicationContext(),Exchange,orderSubtotal,txSubtotal, txtotalAmount, txSubtotal_real, discount_p);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cardAdapter);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingData  settingData = new SettingData();
                if(settingData == null){
                    Toast.makeText(CardActivity.this, "kolo", Toast.LENGTH_SHORT).show();
                }
                else {
                    subTotal = txSubtotal.getText().toString().trim();
                    Intent intent = new Intent(CardActivity.this, Comfirm_Activity.class);
                    intent.putExtra(Comfirm_Activity.SUBTOTAL,subTotal);
                    startActivity(intent);
                }
//

                ArrayList<String> engName = new ArrayList<>();
                ArrayList<String> khName = new ArrayList<>();
                ArrayList<String> qtyList = new ArrayList<>();
                ArrayList<String> priceList = new ArrayList<>();
                ArrayList<String> amountList = new ArrayList<>();

                for(int k =0 ; k<cardData.size() ; k++){
                    engName.add(cardData.get(k).getPro_cardNameEng());
                    khName.add(cardData.get(k).getPro_cardNameKH());
                    qtyList.add(String.valueOf(cardData.get(k).getPro_cardQty()));
                    priceList.add(String.valueOf(cardData.get(k).getPro_cardPrice()));
                    amountList.add(String.valueOf(cardData.get(k).getPro_cardQty() * cardData.get(k).getPro_cardCost()));
                }

                CheckOutData cheackout = new CheckOutData();
                cheackout.setQty(qtyList);
                cheackout.setPrice(priceList);
//                cheackout.setAmount(amountList);
//
//

            }

        });

        int i;
        double sum = 0.0;
        for (i = 0; i < cardData.size(); i++) {
            sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));
       }

        txSubtotal.setText(formatting(String.valueOf(sum)));

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