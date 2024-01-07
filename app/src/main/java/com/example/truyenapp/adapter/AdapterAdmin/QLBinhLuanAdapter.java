package com.example.truyenapp.adapter.AdapterAdmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyenapp.Admin.ShowThongTinBinhLuan;
import com.example.truyenapp.R;
import com.example.truyenapp.database.Database;
import com.example.truyenapp.model.BinhLuan;

import java.util.ArrayList;

public class QLBinhLuanAdapter extends RecyclerView.Adapter<QLBinhLuanAdapter.QLBinhLuanViewHolder>{
    private Context context;
    private ArrayList<BinhLuan> list;
    private Database db;

    public QLBinhLuanAdapter(Context context, ArrayList<BinhLuan> list, Database db) {
        this.context = context;
        this.list = list;
        this.db = db;
    }

    @NonNull
    @Override
    public QLBinhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_qlbinhluan,parent,false);
        return new QLBinhLuanAdapter.QLBinhLuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLBinhLuanViewHolder holder, int position) {
        BinhLuan binhLuan=list.get(position);
        if(binhLuan==null){
            return;
        }

        holder.tv_id.setText(""+binhLuan.getId());
        int trangthai=binhLuan.getTrangthai();
        if(trangthai!=0){
            holder.bt_hien.setVisibility(View.GONE);
            holder.bt_an.setVisibility(View.VISIBLE);
            holder.tv_trangthai.setText("Hoạt động");
        }else {
            holder.bt_hien.setVisibility(View.VISIBLE);
            holder.bt_an.setVisibility(View.GONE);
            holder.tv_trangthai.setText("Bị khóa");
        }
        holder.tv_noidung.setText(binhLuan.getNoidung());
        holder.bt_an.setOnClickListener(view -> {
            db.updateTrangThaiBinhLuan(binhLuan.getId(),0);
            holder.bt_hien.setVisibility(View.VISIBLE);
            holder.bt_an.setVisibility(View.GONE);
            holder.tv_trangthai.setText("Bị khóa");
        });
        holder.bt_hien.setOnClickListener(view -> {
            db.updateTrangThaiBinhLuan(binhLuan.getId(),1);
            holder.bt_hien.setVisibility(View.GONE);
            holder.bt_an.setVisibility(View.VISIBLE);
            holder.tv_trangthai.setText("Hoạt động");
        });
        holder.ll_rcv_qlbinhluan.setOnClickListener(view -> {
            Intent intent=new Intent(holder.itemView.getContext(), ShowThongTinBinhLuan.class);
            intent.putExtra("id_binhluan",binhLuan.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class QLBinhLuanViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_id,tv_noidung,tv_trangthai;
        private Button bt_an,bt_hien;
        private LinearLayout ll_rcv_qlbinhluan;
        public QLBinhLuanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_idqlbinhluan);
            tv_noidung=itemView.findViewById(R.id.tv_noidungqlbinhluan);
            tv_trangthai=itemView.findViewById(R.id.tv_trangthaiqlbinhluan);
            bt_an=itemView.findViewById(R.id.bt_anqlbinhluan);
            bt_hien=itemView.findViewById(R.id.bt_hienqlbinhluan);
            ll_rcv_qlbinhluan=itemView.findViewById(R.id.ll_rcv_qlbinhluan);
        }
    }
}
