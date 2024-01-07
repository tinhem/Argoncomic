package com.example.truyenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.truyenapp.Admin.QuanLyBinhLuan;
import com.example.truyenapp.Admin.QuanLyTaiKhoan;
import com.example.truyenapp.Admin.QuanLyThongKe;
import com.example.truyenapp.Admin.QuanLyTruyen;
import com.example.truyenapp.activity.DiemThuong;
import com.example.truyenapp.adapter.TruyenAdapter;
import com.example.truyenapp.database.Database;
import com.example.truyenapp.model.TaiKhoan;
import com.example.truyenapp.model.Truyen;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    View headerLayout, view;
    Button bt_dnhome, bt_dxhome;
    Menu menu;
    MenuItem mn_it_chucnangquantri;
    TaiKhoan taiKhoan;
    TextView tv_TimKemHome,tv_xephang, tv_theloai, tv_emailhome, tv_diemthuong,tv_diemdanh;

    Database db;
    Truyen truyen;
    String email;

    private RecyclerView rv,rv2,rv3;
    private TruyenAdapter _rv,rv_2,rv_3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);

        db=new Database(getActivity());
        Anhxa();

        Intent i = getActivity().getIntent();
        email=i.getStringExtra("email");
        tv_emailhome.setText(email);

        if(tv_emailhome.getText().length()!=0){
            taiKhoan = db.getTaiKhoan(email);
            if(taiKhoan.getLoaitk()==1){
                mn_it_chucnangquantri.setVisible(true);
            }else {
                mn_it_chucnangquantri.setVisible(false);
            }
            tv_emailhome.setVisibility(view.VISIBLE);
            bt_dxhome.setVisibility(view.VISIBLE);
            bt_dnhome.setVisibility(view.GONE);
        }
        else{
            mn_it_chucnangquantri.setVisible(false);
            tv_emailhome.setVisibility(view.GONE);
            bt_dxhome.setVisibility(view.GONE);
            bt_dnhome.setVisibility(view.VISIBLE);

        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);

        rv.setLayoutManager(linearLayoutManager);
        rv2.setLayoutManager(linearLayoutManager2);
        rv3.setLayoutManager(linearLayoutManager3);

        String lenhSqlite_truyenmoi="SELECT *" +
                "  FROM truyen \n" +
                "  where id in (select truyen.id from truyen inner join chapter on truyen.id=chapter.idtruyen where chapter.tenchapter='Chapter 1' order by chapter.ngaydang desc limit 5)";
        ArrayList<Truyen> truyenmoi=db.getTruyen(lenhSqlite_truyenmoi);
        _rv=new TruyenAdapter(truyenmoi,getActivity(),email);

        String lenhSqlite_toptruyen="SELECT *\n" +
                "  FROM truyen \n" +
                "  where id in (select truyen.id from truyen inner join thongke on truyen.id=thongke.idtruyen order by thongke.tongluotxem desc limit 5)";
        ArrayList<Truyen> toptruyen=db.getTruyen(lenhSqlite_toptruyen);
        rv_2=new TruyenAdapter(toptruyen,getActivity(),email);

        String lenhSqlite_truyenfull="SELECT *\n" +
                "  FROM truyen \n" +
                "  where trangthai=1 limit 5";
        ArrayList<Truyen> truyenfull=db.getTruyen(lenhSqlite_truyenfull);
        rv_3=new TruyenAdapter(truyenfull,getActivity(),email);

        rv.setAdapter(_rv);
        rv2.setAdapter(rv_2);
        rv3.setAdapter(rv_3);

        ActionBar();
        ActionViewFlipper();
        setOnClickListener();


        return view;
    }

    private void setOnClickListener(){
        bt_dnhome.setOnClickListener(this);
        tv_TimKemHome.setOnClickListener(this);
        tv_xephang.setOnClickListener(this);
        tv_theloai.setOnClickListener(this);
        tv_diemthuong.setOnClickListener(this);
        tv_diemdanh.setOnClickListener(this);
        bt_dxhome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.bt_dnhome:
                Intent dialog_box = new Intent(getActivity(), DangNhap.class);
                startActivity(dialog_box);
                getActivity().finish();
                break;
            case R.id.tv_TimKiemHome:
                Intent dialog_box1 = new Intent(getActivity(), TimKiem.class);
                dialog_box1.putExtra("email",email);
                startActivity(dialog_box1);
                break;
            case R.id.tv_xephang:
                Intent dialog_box2 = new Intent(getActivity(), XepHang.class);
                dialog_box2.putExtra("email",email);
                startActivity(dialog_box2);
                break;
            case R.id.tv_theloai:
                Intent dialog_box3 = new Intent(getActivity(), TheLoai.class);
                dialog_box3.putExtra("email",email);
                startActivity(dialog_box3);
                break;
            case R.id.tv_diemthuong:
                if(email!=null){
                    Intent dialog_box4 = new Intent(getActivity(), DiemThuong.class);
                    dialog_box4.putExtra("email",email);
                    startActivity(dialog_box4);
                }else {
                    Toast.makeText(getActivity(),"Vui lòng đăng nhập để sử dụng chức năng này!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_diemdanh:
            {
                if (tv_emailhome.getText().length() != 0) {
                    Boolean checkDiemDanh = db.checkDiemDanh(taiKhoan);
                    if (checkDiemDanh == false) {
                        int thu = db.getThu(taiKhoan);
                        if (thu == 2) {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 10);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 10, thu + 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(getActivity(), "Điểm danh thành công! +10 điểm", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Xảy ra lỗi, Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                            }
                        } else if (thu == 6) {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 15);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 15, thu + 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(getActivity(), "Điểm danh thành công! +15 điểm", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Xảy ra lỗi, Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                            }
                        } else if (thu == 7) {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 5);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 5, 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(getActivity(), "Điểm danh thành công! +5 điểm", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Xảy ra lỗi, Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 5);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 5, thu + 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(getActivity(), "Điểm danh thành công! +5 điểm", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Xảy ra lỗi, Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Hôm nay bạn đã điểm danh, chờ đến ngày mai nhé!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Vui lòng đăng nhập để điểm danh!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.bt_dxhome:
            {
                Intent intent = new Intent(getActivity(),TrangChu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(getActivity().getApplicationContext(),"Đăng xuất thành công",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    private void ActionViewFlipper() {
        ArrayList<String> arrGTSP = new ArrayList<>();
        arrGTSP.add("https://st.imageinstant.net/data/comics/172/tren-nguoi-ta-co-mot-con-rong.jpg");
        arrGTSP.add("https://st.imageinstant.net/data/comics/148/ta-bi-nhot-tai-cung-mot-ngay-muoi-van-na-5986.jpg");
        arrGTSP.add("https://st.imageinstant.net/data/comics/154/nghich-thien-ta-than.jpg");
        arrGTSP.add("https://st.imageinstant.net/data/comics/159/anh-sang-cuoi-con-duong.jpg");
        arrGTSP.add("https://st.imageinstant.net/data/comics/182/co-vo-hop-dong-bo-tron-cua-tong-giam-doc-2344.jpg");

        for(int i=0;i<arrGTSP.size();i++)
        {
            ImageView imageView=new ImageView(((AppCompatActivity)getActivity()).getApplicationContext());
            Picasso.with(((AppCompatActivity)getActivity()).getApplicationContext()).load(arrGTSP.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation anim_slide_in = AnimationUtils.loadAnimation(((AppCompatActivity)getActivity()).getApplicationContext(),R.anim.slide_in_right);
        Animation anim_slide_out = AnimationUtils.loadAnimation(((AppCompatActivity)getActivity()).getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(anim_slide_in);
        viewFlipper.setOutAnimation(anim_slide_out);
    }

    private void ActionBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.nav_d_op,R.string.nav_d_cl);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa(){

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vf);
        navigationView = (NavigationView) view.findViewById(R.id.nvv);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drlo);
//        button = (Button) findViewById(R.id.bt_dnhome);
        drawerLayout= (DrawerLayout) view.findViewById(R.id.drlo);

        headerLayout=navigationView.inflateHeaderView(R.layout.header);
        bt_dnhome= (Button) headerLayout.findViewById(R.id.bt_dnhome);

        tv_TimKemHome=(TextView) view.findViewById(R.id.tv_TimKiemHome);
        tv_xephang=(TextView) view.findViewById(R.id.tv_xephang);
        tv_theloai=(TextView) view.findViewById(R.id.tv_theloai);
        tv_diemthuong=view.findViewById(R.id.tv_diemthuong);
        tv_diemdanh=view.findViewById(R.id.tv_diemdanh);

        rv=view.findViewById(R.id.rv);
        rv2=view.findViewById(R.id.rv2);
        rv3=view.findViewById(R.id.rv3);

        menu=navigationView.getMenu();
        mn_it_chucnangquantri=menu.findItem(R.id.it_chucnangquantri);

        tv_emailhome=headerLayout.findViewById(R.id.tv_emailhome);
        bt_dxhome=headerLayout.findViewById(R.id.bt_dxhome);

    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.isDrawerOpen(GravityCompat.START);
        }
        else{
            ((AppCompatActivity)getActivity()).onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.it_quanlytaikhoan:
                Intent dialog_box = new Intent(getActivity(), QuanLyTaiKhoan.class);
                dialog_box.putExtra("email",taiKhoan.getEmail());
                startActivity(dialog_box);
                break;
            case R.id.it_quanlytruyen:
                Intent dialog_box1 = new Intent(getActivity(), QuanLyTruyen.class);
                dialog_box1.putExtra("email",taiKhoan.getEmail());
                startActivity(dialog_box1);
                break;
            case R.id.it_quanlybinhluan:
                Intent dialog_box2 = new Intent(getActivity(), QuanLyBinhLuan.class);
                dialog_box2.putExtra("email",taiKhoan.getEmail());
                startActivity(dialog_box2);
                break;
            case R.id.it_quanlythongke:
                Intent dialog_box3 = new Intent(getActivity(), QuanLyThongKe.class);
                dialog_box3.putExtra("email",taiKhoan.getEmail());
                startActivity(dialog_box3);
                break;
            case R.id.it_xephang:
                Intent dialog_box4 = new Intent(getActivity(), XepHang.class);
                dialog_box4.putExtra("email",taiKhoan.getEmail());
                startActivity(dialog_box4);
                break;
            case R.id.it_theloai:
                Intent dialog_box5 = new Intent(getActivity(), TheLoai.class);
                dialog_box5.putExtra("email",taiKhoan.getEmail());
                startActivity(dialog_box5);
                break;
        }
        return true;
    }

}