package com.example.systempos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.systempos.adapter.adapter_main;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    GridView gridView;
    List<HomePage> datahomepage;
    LottieAnimationView lottieAnimationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        datahomepage = new ArrayList<>();

        datahomepage.add(new HomePage("Invoice list", R.drawable.image_invoice));
        datahomepage.add(new HomePage("Product list", R.drawable.image_product));
        datahomepage.add(new HomePage("Report", R.drawable.image_report));
        datahomepage.add(new HomePage("Catogory list", R.drawable.image_catogory));
        datahomepage.add(new HomePage("User list", R.drawable.image_user));
        datahomepage.add(new HomePage("Payment list", R.drawable.image_payment));
        datahomepage.add(new HomePage("Customer detail", R.drawable.image_customer));
        datahomepage.add(new HomePage("Location detail", R.drawable.image_location));
        datahomepage.add(new HomePage("Sales Orders", R.drawable.image_sale));
        datahomepage.add(new HomePage("Card", R.drawable.image_order));

        gridView = view.findViewById(R.id.gridview);
        gridView.setVerticalScrollBarEnabled(false);


        gridView.setAdapter(new adapter_main(datahomepage, requireContext()));

        return view;
    }
}