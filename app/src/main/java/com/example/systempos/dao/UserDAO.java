package com.example.systempos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.systempos.Card.CardData;
import com.example.systempos.Check_out.CheckOutData;
import com.example.systempos.Customer.CustomerData;
import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.Payment.PaymentData;
import com.example.systempos.Product.ProductData;
import com.example.systempos.Sale.SaleData;
import com.example.systempos.Setting.SettingData;
import com.example.systempos.User.UserData;
import com.example.systempos.location.LocationData;
import com.example.systempos.model.CatogoryData;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void InsertLocation(LocationData locationData);
    @Insert
    void InsertUser(UserData userData);

    @Insert
    void InsertProduct(ProductData productData);

    @Insert
    void InsertCustomer(CustomerData customerData);

    @Insert
    void InsertPayment(PaymentData paymentData);
    @Insert
    void InsertCotogory(CatogoryData catogoryData);

    @Insert
    void InsertCard(CardData cardData);
    @Insert
    void InsertExchage(ExchangeData exchangeData);

    @Insert
    void  InsertSetting(SettingData settingData);

    @Insert
    void  InsertCheckOut(CheckOutData checkOutData);




   @Query("SELECT * FROM catogory")
    List<CatogoryData> getAllCatoFuture();

    @Query("SELECT * FROM catogory")
    LiveData<List<CatogoryData>> getAllCatoLiveData();


  @Query("SELECT * FROM tbUser")
   List<UserData> getAllUserFuture();

    @Query("SELECT * FROM tbUser")
    LiveData<List<UserData>> getAllUserLiveData();

    @Query("SELECT * FROM product")
    List<ProductData> getAllProductFuture();

    @Query("SELECT * FROM product")
    LiveData<List<ProductData>> getAllProductLiveData();

    @Query("SELECT * FROM customer")
    List<CustomerData> getAllCustomerFuture();
    @Query("SELECT * FROM customer")
    LiveData<List<CustomerData>> getAllCustomerLiveData();


    @Query("SELECT * FROM payment")
    List<PaymentData> getAllPaymentFuture();

    @Query("SELECT * FROM payment")
   LiveData<List<PaymentData>> getAllPaymentLiveData();

    @Query("SELECT * FROM Location")
    List<LocationData> getAllLocationFuture();
//
    @Query("SELECT * FROM Location")
    LiveData<List<LocationData>> getAllLocationLiveData();

    @Query("SELECT * FROM exchage")
    LiveData<List<ExchangeData>> getAllExchageLiveData();
    @Query("SELECT * FROM exchage")
    List<ExchangeData> getAllExchageFuture();

   @Query("SELECT * FROM Setting")
   List<SettingData> getALLSettingFuture();

    @Query("SELECT * FROM Setting")
    LiveData<List<SettingData>> getALLSettingLiveData();

    @Query("SELECT * FROM CheckOUT")
    List<CheckOutData> getAllCheckOutFuture();
    @Query("SELECT * FROM CheckOUT")
    LiveData<List<CheckOutData>> getAllCheckOutLiveData();




    @Query("SELECT * FROM sale")
    List<SaleData>getAllSale();

    @Query("SELECT * FROM card")
    List<CardData> getAllCard();

    @Query("SELECT EXISTS(SELECT * FROM Card WHERE pro_cardNameEng=:productNameEng OR Pro_cardNameKH=:productNameKH )")
    Boolean cartExists(String productNameEng,String productNameKH);





//
//   @Query("UPDATE product SET CatoID =:CatoID, ProBarcode =:Probarcode, Proname =:Proname, PronameKh=:PronameKH,Proprice =:ProPrice WHERE ProID =:ProID")
//   void UpdateProductByid(String CatoID,String Probarcode, String ProID,String Proname,String PronameKH,Double ProPrice);

    @Update
    void UpdateProduct(ProductData productData);
     @Update
     void UpdatePayment(PaymentData paymentData);
     @Update
     void UpdateCatogory(CatogoryData catogoryData);

     @Update
     void UpdateCustomer(CustomerData customerData);

     @Update
     void UpdaAllteUser(UserData userData);

     @Update
     void UpdateAllLocatioon(LocationData locationData);
     @Update
     void UpdateExchage(ExchangeData exchangeData);

     @Update
     void UpdateSetting(SettingData settingData);








   @Query("DELETE FROM catogory WHERE id =:id")
    void DeleteCatoByid(int id);

   @Query("DELETE FROM tbUser WHERE id =:id")
    void DeleteUserByid(int id);

   @Query("DELETE FROM product WHERE ProID =:ProID")
   void DeleteProductByid(int ProID);

   @Query("DELETE FROM customer WHERE id =:Cusid")
   void DeleteCustomerByid(int Cusid);

   @Query("DELETE FROM payment WHERE payId =:Payid")
   void DeletePaymentByid(int Payid);
   @Query("DELETE FROM location WHERE locID =:locID")
    void DeleteLocationById(int locID);

   @Query("DELETE FROM card WHERE Cardid =:CardID")
    void DeleteCardById(int CardID);

    @Query("SELECT * from tbUser where name=:username AND password=:password")
    List<UserData> loginAccount(String username , String password);



}
