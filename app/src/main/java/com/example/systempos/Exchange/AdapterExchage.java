package com.example.systempos.Exchange;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.systempos.R;

import java.util.List;

public class AdapterExchage extends RecyclerView.Adapter<AdapterExchage.MyViewHolder> {

    List<ExchangeData> exchangeData;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_show_exchage,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txChage.setText(String.valueOf(exchangeData.get(position).getExchageMoney()));
        holder.txDesction.setText(String.valueOf(exchangeData.get(position).getExchageDesc()));

        final ExchangeData exchangeData1 = exchangeData.get(position);
            if(exchangeData1 == null){
                return;
            }

        holder.cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Update_Exchagectivity.class);
                intent.putExtra("exchage",exchangeData1);
                view.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return exchangeData.size();
    }

    public void setExchange(List<ExchangeData> exchangeDataList){
        exchangeData = exchangeDataList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txChage,txDesction;
        Button btnUpdate;
        CardView cardUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txChage = itemView.findViewById(R.id.excahgeMoney);
            txDesction = itemView.findViewById(R.id.excahgeDescription);
            btnUpdate = itemView.findViewById(R.id.btnUp_exchage);
            cardUpdate = itemView.findViewById(R.id.cardshowExchage);
        }
    }

}
