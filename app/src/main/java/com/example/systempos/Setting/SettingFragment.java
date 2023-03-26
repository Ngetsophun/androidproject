package com.example.systempos.Setting;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.systempos.R;
import com.example.systempos.ViewModel.SettingViewModel;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.FragmentSettingBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
import java.util.List;

public class SettingFragment extends Fragment {


    FragmentSettingBinding binding;
    SettingData settingData;
    SettingViewModel settingViewModel;
    UserDatabase userDatabase;
    File file;

    AdapterSetting adapterSetting;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater,container,false);
        settingData = new SettingData();

        //liveData

        binding.floatAddSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.showSettingFragement.setVisibility(View.GONE);
                binding.addSettingFragement.setVisibility(View.VISIBLE);
            }
        });
        binding.btnSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.showSettingFragement.setVisibility(View.VISIBLE);
                binding.addSettingFragement.setVisibility(View.GONE);
            }
        });

        settingViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SettingViewModel.class);
        settingViewModel.getALLSettingLiveData().observe(getViewLifecycleOwner(), new Observer<List<SettingData>>() {
            @Override
            public void onChanged(List<SettingData> settingData) {
                if(settingData !=null){
                    adapterSetting = new AdapterSetting(settingData,getActivity());
                    adapterSetting.setSetting(settingData);
                    binding.recyclerShowSetting.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
                    binding.recyclerShowSetting.setAdapter(adapterSetting);

                }
            }
        });



        //save data
        binding.btnSettingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeName = binding.settingComName.getText().toString().trim();
                String storeAddress = binding.settingAddress.getText().toString().trim();
                String storeNumber = binding.settingNumber.getText().toString().trim();
                String storeExchage = binding.settingExchage.getText().toString();

                settingData.setStoreName(storeName);
                settingData.setStoreAddress(storeAddress);
                settingData.setStoreNumber(storeNumber);
                settingData.setStorrExchage(Double.parseDouble(storeExchage));
                settingData.setStoreImage(file.getPath());

                settingViewModel.InsertSetting(settingData);

                binding.settingAddress.setText("");
                binding.settingComName.setText("");
                binding.settingNumber.setText("");
                binding.settingExchage.setText("");

            }
        });

        //Choose Image
        binding.settingImagecopany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();
            }
        });




        return binding.getRoot();
    }

    private void ChooseImage(){
        launcher.launch(
                ImagePicker.Companion.with(getActivity())
                        .maxResultSize(1080,1080, true)
                        .crop()
                        .galleryOnly()
                        .createIntent()
        );
    }
    ActivityResultLauncher<Intent> launcher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                if(result.getResultCode()==RESULT_OK){

                    assert result.getData() != null;

                    Uri uri=result.getData().getData();

                    file = new File(uri.getPath());
                    binding.settingImagecopany.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){

                }
            });

}