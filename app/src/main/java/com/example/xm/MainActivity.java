package com.example.xm;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.xm.Adapter.CameraAdapter;
import com.example.xm.Fragment.GIFFragment;
import com.example.xm.Fragment.HomeFragment;
import com.example.xm.Fragment.StickerFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ImageView iv1;
    private TextView tv1;
    private ImageView iv2;
    private TextView tv2;
    private ImageView iv3;
    private TextView tv3;
    private ViewPager2 viewPager2;
    private DrawerLayout line1;
    private NavigationView mainNav;
    private ImageView ivFh;
    private TextView title1;
    private TextView tvGrzx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ivFh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (line1.isDrawerOpen(GravityCompat.START)) {
                    line1.closeDrawer(GravityCompat.START);
                } else {
                    line1.openDrawer(GravityCompat.START);
                }
            }
        });
        verifyStoragePermissions(this);
        fragments.add(new HomeFragment());
        fragments.add(new StickerFragment());
        fragments.add(new GIFFragment());
        CameraAdapter adapter = new CameraAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        iv1.setImageResource(R.mipmap.sy2);
                        tv1.setTextColor(Color.parseColor("#827B7B"));
                        iv2.setImageResource(R.mipmap.gd1);
                        tv2.setTextColor(Color.parseColor("#ffffff"));
                        iv3.setImageResource(R.mipmap.gr1);
                        tv3.setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case 1:
                        iv1.setImageResource(R.mipmap.sy1);
                        tv1.setTextColor(Color.parseColor("#ffffff"));
                        iv2.setImageResource(R.mipmap.gd2);
                        tv2.setTextColor(Color.parseColor("#827B7B"));
                        iv3.setImageResource(R.mipmap.gr1);
                        tv3.setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case 2:
                        iv1.setImageResource(R.mipmap.sy1);
                        tv1.setTextColor(Color.parseColor("#ffffff"));
                        iv2.setImageResource(R.mipmap.gd1);
                        tv2.setTextColor(Color.parseColor("#ffffff"));
                        iv3.setImageResource(R.mipmap.gr2);
                        tv3.setTextColor(Color.parseColor("#827B7B"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_1:
                iv1.setImageResource(R.mipmap.sy2);
                tv1.setTextColor(Color.parseColor("#827B7B"));
                iv2.setImageResource(R.mipmap.gd1);
                tv2.setTextColor(Color.parseColor("#ffffff"));
                iv3.setImageResource(R.mipmap.gr1);
                tv3.setTextColor(Color.parseColor("#ffffff"));
                viewPager2.setCurrentItem(0);
                break;
            case R.id.iv_2:
                iv1.setImageResource(R.mipmap.sy1);
                tv1.setTextColor(Color.parseColor("#ffffff"));
                iv2.setImageResource(R.mipmap.gd2);
                tv2.setTextColor(Color.parseColor("#827B7B"));
                iv3.setImageResource(R.mipmap.gr1);
                tv3.setTextColor(Color.parseColor("#ffffff"));
                viewPager2.setCurrentItem(1);
                break;
            case R.id.iv_3:
                iv1.setImageResource(R.mipmap.sy1);
                tv1.setTextColor(Color.parseColor("#ffffff"));
                iv2.setImageResource(R.mipmap.gd1);
                tv2.setTextColor(Color.parseColor("#ffffff"));
                iv3.setImageResource(R.mipmap.gr2);
                tv3.setTextColor(Color.parseColor("#827B7B"));
                viewPager2.setCurrentItem(2);
                break;
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限  √
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long time = 0;

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();/* 获取第一次按键时间*/
        if ((mNowTime - time) > 1000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            time = mNowTime;

        } else {
            finish();
            System.exit(0);
        }
    }

    private void initView() {
        iv1 = findViewById(R.id.iv_1);
        tv1 = findViewById(R.id.tv_1);
        iv2 = findViewById(R.id.iv_2);
        tv2 = findViewById(R.id.tv_2);
        iv3 = findViewById(R.id.iv_3);
        tv3 = findViewById(R.id.tv_3);
        viewPager2 = findViewById(R.id.viewPager2);
        line1 = findViewById(R.id.line1);
        mainNav = findViewById(R.id.mainNav);
        ivFh = findViewById(R.id.iv_fh);
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
    }
}