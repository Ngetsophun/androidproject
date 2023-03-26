package com.example.systempos.Card;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.systempos.Check_out.CheckOutData;
import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.Product.ProductData;
import com.example.systempos.R;
import com.example.systempos.Setting.SettingData;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.MyViewHolder> {

    List<CardData> cardData;
    Context context;

    UserDAO userDAO;

    List<ProductData> productData;

   List<SettingData> settingDataList;
   List<CheckOutData> checkOutData;




    TextView subtotal, subtotal_real,txCardTax,ExchageKH;
    TextView totalAmount,OrderSubtotal;



    EditText discount_p;
    UserDatabase userDatabase;



    public Card_Adapter(List<CardData> cardData,List<ProductData> productData,List<CheckOutData> checkOutData,Context context,TextView orderSubtotal,TextView ExchageKH, TextView subtotal, TextView totalAmount, TextView subtotal_real, EditText discount_p) {
        this.cardData = cardData;
        this.context = context;
        this.productData = productData;
        this.subtotal = subtotal;
        this.txCardTax = txCardTax;
        this.discount_p = discount_p;
        this.totalAmount = totalAmount;
        this.subtotal_real = subtotal_real;
        this.OrderSubtotal = orderSubtotal;
        this.ExchageKH = ExchageKH;
        this.checkOutData = checkOutData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_show_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(cardData.get(position).getPro_cardimg()).into(holder.imgcard);
        holder.cardNameKh.setText(String.valueOf(cardData.get(position).Pro_cardNameKH));
        holder.cardNameEng.setText(String.valueOf(cardData.get(position).pro_cardNameEng));
        holder.cardPrice.setText(String.valueOf(cardData.get(position).Pro_cardPrice));
        holder.UpdateQty.setText(String.valueOf(cardData.get(position).Pro_cardQty));


        holder.imgdeletecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = UserDatabase.getUserDatabase(view.getContext()).userDAO();

                userDAO.DeleteCardById(cardData.get(position).Cardid);
                cardData.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = cardData.get(position).getPro_cardQty();
                if (qty > 1) {
                    qty--;
                    cardData.get(position).setPro_cardQty(qty);
                    notifyDataSetChanged();
                    UpdatePrice();
                }


            }
        });



        holder.Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = cardData.get(position).getPro_cardQty();
                qty++;
                cardData.get(position).setPro_cardQty(qty);
                notifyDataSetChanged();
                UpdatePrice();


            }
        });

       //  btnComfirm
//        holder.Confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
//////
////                CheckOutData checkOutData = new CheckOutData();
////
////                checkOutData.setProductNames(cardData.get(position).getPro_cardNameEng());
////                checkOutData.setPrice(cardData.get(position).getPro_cardPrice());
////                checkOutData.setQty(cardData.get(position).getPro_cardQty());
//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cardNameEng, cardNameKh, cardPrice;
        ImageView imgcard, imgdeletecard;
        EditText UpdateQty;
        Button Minus, Plus, Confirm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNameEng = itemView.findViewById(R.id.Card_nameEng);
            cardNameKh = itemView.findViewById(R.id.Card_nameKH);
            cardPrice = itemView.findViewById(R.id.Card_Price);
            imgcard = itemView.findViewById(R.id.CardImage);
            UpdateQty = itemView.findViewById(R.id.editQty);
            imgdeletecard = itemView.findViewById(R.id.imgdelete_Card);
            Minus = itemView.findViewById(R.id.btnMinusd);
            Plus = itemView.findViewById(R.id.btnPlus);
            Confirm = itemView.findViewById(R.id.btnConfirm);

        }
    }

    public void UpdatePrice() {
        int i;
        double sum = 0.0;

        for (i = 0; i < cardData.size(); i++) {
            sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));

        }
        subtotal.setText( numberFormat(String.valueOf(sum)));
//        subtotal.setText(numberFormat(String.valueOf(sum)));





    }


    public static String numberFormat(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,#0.00");
        return decimalFormat.format(Double.parseDouble(number));
    }

}
