package com.example.truyenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.truyenapp.database.Database;

public class TrangChu extends AppCompatActivity {

    String email;
    MeowBottomNavigation meowBottomNavigation;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        Anhxa();
        Intent intent=getIntent();
        email=intent.getStringExtra("email");

        db=new Database(this);
        int thongbao=db.countThongBaoNow();

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_noti));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_book));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_account));

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment=null;
                switch (item.getId()){
                    case 3:
                        if(email!=null){
                            fragment= new TuSachFragment();
                        }
                        break;
                    case 1:
                        fragment= new HomeFragment();
                        break;
                    case 4:
                        if(email!=null){
                            fragment= new TaiKhoanFragment();
                        }
                        break;
                    case 2:
                        if(email!=null){
                            fragment= new ThongBaoFragment();
                        }
                        break;
                }
                if(fragment!=null){
                    loadFragment(fragment);
                }
            }
        });

        if(thongbao!=0){
            meowBottomNavigation.setCount(2,""+thongbao);
        }
        meowBottomNavigation.show(1,true);

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (item.getId()==3){
                    if(email!=null){
                        Toast.makeText(getApplicationContext(), "Tủ sách", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Vui Lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.getId()==1){
                    Toast.makeText(getApplicationContext(), "Trang chủ", Toast.LENGTH_SHORT).show();
                }
                if (item.getId()==4){
                    if(email!=null){
                        Toast.makeText(getApplicationContext(), "Tài khoản", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Vui Lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.getId()==2){
                    if(email!=null){
                        Toast.makeText(getApplicationContext(), "Thông báo", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Vui Lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                if (item.getId()==3){
                    if(email!=null){
                        Toast.makeText(getApplicationContext(), "Tủ sách", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Vui Lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.getId()==1){
                    Toast.makeText(getApplicationContext(), "Trang chủ", Toast.LENGTH_SHORT).show();
                }
                if (item.getId()==4){
                    if(email!=null){
                        Toast.makeText(getApplicationContext(), "Tài khoản", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Vui Lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.getId()==2){
                    if(email!=null){
                        Toast.makeText(getApplicationContext(), "Thông báo", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Vui Lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void Anhxa() {
        meowBottomNavigation=findViewById(R.id.bottom_nav);
    }

    public void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

}