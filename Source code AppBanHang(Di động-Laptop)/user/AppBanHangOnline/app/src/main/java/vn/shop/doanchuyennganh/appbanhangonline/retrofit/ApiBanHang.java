package vn.shop.doanchuyennganh.appbanhangonline.retrofit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import vn.shop.doanchuyennganh.appbanhangonline.model.DonHangModel;
import vn.shop.doanchuyennganh.appbanhangonline.model.LoaiSpModel;
import vn.shop.doanchuyennganh.appbanhangonline.model.MessageModel;
import vn.shop.doanchuyennganh.appbanhangonline.model.SanPhamMoiModel;
import vn.shop.doanchuyennganh.appbanhangonline.model.UserModel;

public interface ApiBanHang {
    //get data
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    // post data
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
      @Field("page") int page,
      @Field("loai") int loai
    );
    // user dang ki
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("uid") String uid
    );
    // user dang ki
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass

    );
    //resetpass
    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<MessageModel> donHang(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> timKiem(
            @Field("timkiem") String timkiem
    );


    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token

    );


    @POST("updatemomo.php")
    @FormUrlEncoded
    Observable<MessageModel> updateMomo(
            @Field("id") int id,
            @Field("token") String token

    );


    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> gettoken(
            @Field("status") int status
    );


}
