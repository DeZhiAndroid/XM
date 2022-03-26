package com.example.xm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.xm.Adapter.CameraAdapter;
import com.example.xm.Fragment.GIFFragment;
import com.example.xm.Fragment.HomeFragment;
import com.example.xm.Fragment.StickerFragment;

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
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "我被长按了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
                switch (position){
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
public void click(View view){
        switch (view.getId()){
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
    private long time = 0;

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();/**  获取第一次按键时间*/
        if ((mNowTime - time) > 1000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            time = mNowTime;

        } else {
            finish();
            System.exit(0);
        }
    }
    private void initView() {
        linearLayout=findViewById(R.id.line1);
        iv1 = findViewById(R.id.iv_1);
        tv1 = findViewById(R.id.tv_1);
        iv2 = findViewById(R.id.iv_2);
        tv2 = findViewById(R.id.tv_2);
        iv3 = findViewById(R.id.iv_3);
        tv3 = findViewById(R.id.tv_3);
        viewPager2 = findViewById(R.id.viewPager2);
    }
}