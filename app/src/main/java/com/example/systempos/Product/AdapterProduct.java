package com.example.systempos.Product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyviewHolder> implements Filterable {

    List<ProductData> productData;
    List<ProductData> productDatalistFull;
    Context context;
    UserDAO userDAO;

    public AdapterProduct(List<ProductData> productData, Context context) {
        this.productData = productData;
        this.context = context;
        this.productDatalistFull = new ArrayList<>(productData);
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_product,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ProductData productData1 = productData.get(position);
        if(productData1== null){
            return;
        }

        Glide.with(context).load(productData.get(position).getImage()).into(holder.imgProduct);

        holder.id.setText(String.valueOf(productData.get(position).getProID()));
        holder.name.setText(String.valueOf(productData.get(position).getProname()));
        holder.qty.setText(String.valueOf(productData.get(position).getProqty()));
        holder.cost.setText(String.valueOf(productData.get(position).getProcost()));
        holder.price.setText(String.valueOf(productData.get(position).getProprice()));
        holder.tax.setText(String.valueOf(productData.get(position).getProtax()));

        //delete product

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = UserDatabase.getUserDatabase(view.getContext()).userDAO();

                userDAO.DeleteProductByid(productData.get(position).getProID());
                productData.remove(position);
                notifyDataSetChanged();
            }
        });



//          /card go to update

        holder.cardProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UpdateProduct.class);
                intent.putExtra("product",productData1);
                view.getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });



    }

    @Override
    public int getItemCount() {
        return productData.size();
    }

    public void setProduct(List<ProductData> productDataList){
        productData = productDataList;
        notifyDataSetChanged();

    }


    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView id,name,qty,cost,price,tax;
        ImageView imgProduct,imgdelete;
        CardView cardProduct;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.proID);
            name = itemView.findViewById(R.id.proName);
            qty = itemView.findViewById(R.id.proQty);
            cost = itemView.findViewById(R.id.proCost);
            price = itemView.findViewById(R.id.proPrice);
            tax = itemView.findViewById(R.id.proTax);
            imgProduct = itemView.findViewById(R.id.proImage);
            imgdelete = itemView.findViewById(R.id.proimgDelete);
            cardProduct = itemView.findViewById(R.id.CardShowProduct);


        }
    }



    @Override
    public Filter getFilter() {
        return AllProductFilter;
    }
    private Filter AllProductFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ProductData> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filterList.addAll(productDatalistFull);
            }else {
                String FiltterPattern = charSequence.toString().toLowerCase().trim();
                for(ProductData productData1:productDatalistFull){
                    if(productData1.getProname().toLowerCase().contains(FiltterPattern) || productData1.getPronameKh().toLowerCase().contains(FiltterPattern)){

                        filterList.add(productData1);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            productData.clear();
            productData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };



}