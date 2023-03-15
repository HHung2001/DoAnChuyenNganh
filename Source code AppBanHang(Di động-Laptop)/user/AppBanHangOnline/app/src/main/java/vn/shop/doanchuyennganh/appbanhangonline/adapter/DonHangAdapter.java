package vn.shop.doanchuyennganh.appbanhangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.shop.doanchuyennganh.appbanhangonline.R;
import vn.shop.doanchuyennganh.appbanhangonline.model.DonHang;

public class DonHangAdapter  extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private  RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // đỗ dl vào recycleview
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng" + donHang.getId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerviewChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        // adpter chitiet
        ChitietAdapter chitietAdapter = new ChitietAdapter(context,donHang.getItem());
        holder.recyclerviewChitiet.setLayoutManager(layoutManager);
        holder.recyclerviewChitiet.setAdapter(chitietAdapter);
        holder.recyclerviewChitiet.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtdonhang;
        RecyclerView recyclerviewChitiet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            recyclerviewChitiet = itemView.findViewById(R.id.recyclerview_chitiet);
        }
    }
}
