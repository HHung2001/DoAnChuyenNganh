package vn.shop.doanchuyennganh.appbanhangonline.retrofit;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


import vn.shop.doanchuyennganh.appbanhangonline.model.NotiResponse;
import vn.shop.doanchuyennganh.appbanhangonline.model.NotiSendData;

public interface ApiPushNofication {
    @Headers(
            {
                    "Content-Type:application/json",
                        "Authorization:key=AAAAD_2bJQ0:APA91bEzmy88pSXbR71DxM6fQeHa7FKtefDVJcU7ry6qQR3BHx6ODAfq1-hK90CJOpZaF7GBagG_zgMsiUIRtIyUCCTcmu0P7Sr3bx0CCngQb_VUsE6wOP3TRoJ2jr3PiTArP-9Qio3b"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
