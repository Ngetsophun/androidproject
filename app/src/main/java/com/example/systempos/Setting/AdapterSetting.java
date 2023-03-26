package com.example.systempos.Setting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.systempos.R;

import java.util.List;

public class AdapterSetting extends RecyclerView.Adapter<AdapterSetting.MyViewHolder>{

    List<SettingData> settingData;
    Context context;

    public AdapterSetting(List<SettingData> settingData, Context context) {
        this.settingData = settingData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_show_setting,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final SettingData setting = settingData.get(position);
        if(setting == null){
            return;
        }
        holder.storeName.setText(String.valueOf(settingData.get(position).getStoreName()));
        holder.storeAddress.setText(String.valueOf(settingData.get(position).getStoreAddress()));
        holder.storeNumber.setText(String.valueOf(settingData.get(position).getStoreNumber()));
        holder.storeExchange.setText(String.valueOf(settingData.get(position).getStorrExchage()));

        Glide.with(context).load(setting.getStoreImage()).into(holder.storeImage);

        //update setting

        holder.cardSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Update_settingActivity.class);
                intent.putExtra("Setting",setting);
                view.getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    @Override
    public int getItemCount() {
        return settingData.size();
    }

    public void setSetting(List<SettingData> settingDataList){
        settingData = settingDataList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView storeName,storeAddress,storeNumber,storeExchange;
        ImageView storeImage;

        CardView cardSetting;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.show_setting_names);
            storeAddress = itemView.findViewById(R.id.show_setting_address);
            storeNumber = itemView.findViewById(R.id.show_setting_phones);
            storeExchange = itemView.findViewById(R.id.show_setting_Exchage);
            storeImage = itemView.findViewById(R.id.show_setting_Image);
            cardSetting = itemView.findViewById(R.id.Show_Setting);


        }
    }

}
