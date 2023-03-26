package com.example.systempos.Sale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import com.example.systempos.Card.CardActivity;
import com.example.systempos.Card.CardData;
import com.example.systempos.Catogory.View_Catogoey;
import com.example.systempos.Product.AdapterProduct;
import com.example.systempos.Product.ProductData;
import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivitySaleProductBinding;
import com.example.systempos.model.CatogoryData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Sale_ProductActivity extends AppCompatActivity {

    ActivitySaleProductBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;
    RecyclerView recyclerView;
    BottomSheetDialog sheetDialog;
    List<CardData> cardDataList;

    List<CatogoryData> catogoryDataList  = new ArrayList<>();
    String CatogoryName;
    CatogoryData catogoryData;

    AdapterProductToSale adapterProductToSale;
    AdapterProduct adapterProduct;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        recyclerView = findViewById(R.id.recycler_Sale_View);
        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        userDAO = userDatabase.userDAO();
        List<ProductData> productData = userDAO.getAllProductFuture();
        AdapterProductToSale adapterProductToSale = new AdapterProductToSale(productData,getApplicationContext());

//        List<CatogoryData> catogoryData1 = userDAO.getAllCatoFuture();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterProductToSale);

        getIntenData();

        OnTabLayout();


        binding.imgSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.BarPosSearch.setVisibility(View.GONE);
                binding.BarPosCloseSearch.setVisibility(View.VISIBLE);
            }
        });
        binding.imgCloseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.BarPosSearch.setVisibility(View.VISIBLE);
                binding.BarPosCloseSearch.setVisibility(View.GONE);
            }
        });

        binding.edPosSearchlistProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterProductToSale.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.shopSaleTocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sale_ProductActivity.this, CardActivity.class);
                startActivity(intent);
            }
        });

        //tab bar


    }


    //tab layout
    private void OnTabLayout(){
        binding.ListCatoPos.addTab(binding.ListCatoPos.newTab().setText("All"));
        if(catogoryDataList.size() !=0){
            for(CatogoryData catogoryData: catogoryDataList){
                CatogoryName = catogoryData.getCatoname();
                binding.ListCatoPos.addTab(binding.ListCatoPos.newTab().setText(CatogoryName));

            }

        }
        binding.ListCatoPos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String getTab = (String) tab.getText();
                if(getTab.equals("All")){
                    adapterProduct.getFilter().filter(null);
                }else {
                    adapterProduct.getFilter().filter(getTab);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void GetCategory(){

    }


    private void getIntenData(){
        if(getIntent().hasExtra("product")){

            ProductData productData = (ProductData) getIntent().getSerializableExtra("product");

//            binding.Dialog_nameEng.setText(String.valueOf(productData.getProname()));
//            binding.Dialog_Price.setText(String.valueOf(productData.getProprice()));
//

        }

    }





    //Adapter Sale
    public class AdapterProductToSale extends RecyclerView.Adapter<AdapterProductToSale.MyviewHolder> implements Filterable {

        List<ProductData> productData;
        List<ProductData> productDataListFull;
        Context context;


        public AdapterProductToSale(List<ProductData> productData, Context context) {
            this.productData = productData;
            this.context = context;
            this.productDataListFull = new ArrayList<>(productData);

        }


        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_show_product,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

            Glide.with(context).load(productData.get(position).getImage()).into(holder.image_product);
            holder.name.setText(String.valueOf(productData.get(position).getProname()));
            holder.namekh.setText(String.valueOf(productData.get(position).getPronameKh()));
            holder.price.setText(String.valueOf(productData.get(position).getProprice()));

            holder.cardorderproduct.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    CardData cardData = new CardData();
                    cardData.setPro_cardQty(1);
                    cardData.setPro_cardNameEng(productData.get(position).getProname());
                    cardData.setPro_cardNameKH(productData.get(position).getPronameKh());
                    cardData.setPro_cardPrice(productData.get(position).getProprice());
                    cardData.setPro_cardimg(productData.get(position).getImage());

                    if(userDAO.cartExists(cardData.getPro_cardNameEng(),cardData.getPro_cardNameKH())){
                        Toast.makeText(context, "You buy item Already", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        userDAO.InsertCard(cardData);
                        Toast.makeText(context, "Buy 1", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

        @Override
        public int getItemCount() {
            return productData.size();
        }


        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView name,namekh,price;

            ImageView image_product,image_shop;
            CardView cardorderproduct;



            public MyviewHolder(@NonNull View itemView) {
                super(itemView);

                name =itemView.findViewById(R.id.showname_sale);
                namekh = itemView.findViewById(R.id.shownameKH_sale);
                price = itemView.findViewById(R.id.showprice_sale);
                image_product = itemView.findViewById(R.id.ImageProduct);
                image_shop = itemView.findViewById(R.id.img_pro_sale);
                cardorderproduct = itemView.findViewById(R.id.card_orderProduct);



            }
        }
        @Override
        public Filter getFilter() {

            return AllProductSaleFilter;
        }
        private Filter AllProductSaleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<ProductData> filterSaleList = new ArrayList<>();

                if(charSequence == null || charSequence.length() == 0){
                    filterSaleList.addAll(productDataListFull);
                }else {
                    String FiltterPattern = charSequence.toString().toLowerCase().trim();

                    for(ProductData productData1:productDataListFull){
                        if(productData1.getProname().toLowerCase().contains(FiltterPattern) || productData1.getPronameKh().toLowerCase().contains(FiltterPattern)){
                            filterSaleList.add(productData1);

                        } else if (productData1.getCatoID().toLowerCase().contains(FiltterPattern)) {
                            filterSaleList.add(productData1);
                        }

                    }
                }
                FilterResults results = new FilterResults();
                results.values = filterSaleList;
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


}