package vn.shop.doanchuyennganh.appbanhangonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.shop.doanchuyennganh.appbanhangonline.R;
import vn.shop.doanchuyennganh.appbanhangonline.model.CreateOrder;
import vn.shop.doanchuyennganh.appbanhangonline.model.NotiSendData;
import vn.shop.doanchuyennganh.appbanhangonline.retrofit.ApiBanHang;
import vn.shop.doanchuyennganh.appbanhangonline.retrofit.ApiPushNofication;
import vn.shop.doanchuyennganh.appbanhangonline.retrofit.RetrofitClient;
import vn.shop.doanchuyennganh.appbanhangonline.retrofit.RetrofitClientNoti;
import vn.shop.doanchuyennganh.appbanhangonline.utils.Utils;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
        Toolbar toolbar;
        TextView txttongtien, txtsdt, txtemail;
        EditText edtdiachi;
        AppCompatButton btndathang, btnzalo;
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        ApiBanHang apiBanHang;
        long tongtien ;
        int totalItem;
        int iddonhang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        //zalo
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);


        initView();
        countItem();
        initControl();

    }

    private void countItem() {
         totalItem = 0;
        // cứ 1 lần chạy qua là lấy số lượng + tổng, nếu co ckeckbox thì mangmuhang
        for(int i = 0; i<Utils.mangmuahang.size();i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // load lên màn hình
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        txtsdt.setText(Utils.user_current.getMobile());


        // hàm kiểm tra nhập đủ thông tin
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Địa chỉ", Toast.LENGTH_LONG).show();
                }else{
                    // post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    //kiem tra,nếu co ckeckbox thì mangmuahang
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.donHang(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalItem, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                               messageModel -> {

                                   // thong bao truyen day ne
                                   pushNotiToUser();

                                   Toast.makeText(getApplicationContext(), "Thêm đơn hàng thành công", Toast.LENGTH_LONG).show();
                                   Utils.mangmuahang.clear();
                                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                   startActivity(intent);
                                   finish();
                               },
                               throwable -> {
                                   Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                               }
                            ));



                }
            }
        });

        btnzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Địa chỉ", Toast.LENGTH_LONG).show();
                }else{
                    // post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    //kiem tra,nếu co ckeckbox thì mangmuahang
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.donHang(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalItem, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {

                                        // thong bao truyen day ne
                                        pushNotiToUser();

                                        Toast.makeText(getApplicationContext(), "Đang chuyển hướng", Toast.LENGTH_LONG).show();
                                        Utils.mangmuahang.clear();
                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
                                        requestZalo();


                                        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        //startActivity(intent);
                                        //finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));



                }

            }
        });





    }

    private void requestZalo() {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder("100000");
            //Log.d("Amount", txtAmount.getText().toString());
            //lblZpTransToken.setVisibility(View.VISIBLE);
            String code = data.getString("return_code");
            //Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();
            Log.d("test", code);
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                Log.d("test", token);



                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        compositeDisposable.add(apiBanHang.updateMomo(iddonhang,token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        messageModel -> {
                                            if (messageModel.isSuccess()){
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        },
                                        throwable -> {
                                            Log.d("Error", throwable.getMessage());
                                        }
                                ));

                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void pushNotiToUser() {

        //get token
        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                for(int i=0; i<userModel.getResult().size(); i++){

                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", "Thông báo");
                                    data.put("body", "Bạn có 1 đơn hàng mới");
                                    NotiSendData notiSendData = new NotiSendData(userModel.getResult().get(i).getToken(), data);
                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
                                    compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {

                                                    },
                                                    throwable -> {
                                                        Log.d("logg", throwable.getMessage());
                                                    }
                                            ));


                                }


                            }

                        },
                        throwable -> {
                            Log.d("logg", throwable.getMessage());
                        }
                ));



    }


    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar);
        txttongtien = findViewById(R.id.txttongtien);
        txtsdt = findViewById(R.id.txtsodienthoai);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
        btnzalo = findViewById(R.id.btnzalo);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

}