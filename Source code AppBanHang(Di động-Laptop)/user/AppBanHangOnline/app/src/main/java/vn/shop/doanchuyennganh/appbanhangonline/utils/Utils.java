package vn.shop.doanchuyennganh.appbanhangonline.utils;

import java.util.ArrayList;
import java.util.List;

import vn.shop.doanchuyennganh.appbanhangonline.model.GioHang;
import vn.shop.doanchuyennganh.appbanhangonline.model.User;

public class Utils {
    public static final String BASE_URL="http://192.168.1.10/banhang/";
    public static List<GioHang> manggiohang;
    //checkbox
   public static List<GioHang> mangmuahang = new ArrayList<>();
    //tự động điền email và pass
    public static User user_current = new User();

}
