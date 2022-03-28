package com.example.xm.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.xm.Adapter.PhotoAdapter;
import com.example.xm.R;
import com.example.xm.util.gifmaker.GifUtil;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GIFFragment extends Fragment {

    private List<Bitmap> bitmaps = new ArrayList<>();
    private int delayTime;
    private AlertDialog alertDialog;
    private PhotoAdapter adapter;
    private File file;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 0) {
                alertDialog.dismiss();
                Toast.makeText(requireContext(), "制作成功", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.fromFile(file);
                requireActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            } else {
                alertDialog.dismiss();
            }
            return false;
        }
    });
    private Toolbar mTitleToolbar;
    private AppCompatTextView mTitleTv;
    private RecyclerView mGifRecycle;
    private AppCompatEditText mFileText;
    private AppCompatTextView mDelayText;
    private SeekBar mDelayBar;
    private LinearLayoutCompat mBottomView;
    private Button mGenerate;
    private Button mClear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gif_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        mTitleTv.setText("GIF制作");
        alertDialog = new AlertDialog.Builder(requireContext()).setView(new ProgressBar(requireContext()))
                .setMessage("正在生成GIF图片")
                .create();
        adapter = new PhotoAdapter(requireContext(), bitmaps);
        mGifRecycle.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mGifRecycle.setAdapter(adapter);
        delayTime = mDelayBar.getProgress();

        //从相册获取图片
        adapter.setmListener((view1, position) -> photoPick());

        mDelayBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                delayTime = i;
                mDelayText.setText(MessageFormat.format("帧间隔时长：{0}秒", i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setClick();
    }

    private void setClick() {
        mGenerate.setOnClickListener(view -> {
            alertDialog.show();
            new Thread(() -> {
                String file_name = Objects.requireNonNull(mFileText.getText()).toString();
                GifUtil gifUtil = new GifUtil();
                String path = gifUtil.createGif(bitmaps, TextUtils.isEmpty(file_name) ? "demo" : file_name, delayTime, requireContext());
                requireActivity().runOnUiThread(() -> {
                    file = new File(path);
                    handler.sendEmptyMessageAtTime(0, 1000);
                });
            }).start();
        });

        mClear.setOnClickListener(view -> clearData());
    }

    private void clearData() {
        bitmaps.clear();
        adapter.setList(null);
    }

    //相册选取图片
    public void photoPick() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 0x21);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Uri localUri = data.getData();
            String[] arrayOfString = {"_data"};
            Cursor localCursor = requireContext().getContentResolver().query(localUri, arrayOfString, null, null, null);
            localCursor.moveToFirst();
            @SuppressLint("Range") String str = localCursor.getString(localCursor.getColumnIndex(arrayOfString[0]));
            Bitmap bitmap = BitmapFactory.decodeFile(str);
            localCursor.close();
            bitmaps.add(bitmap);
            adapter.setList(bitmaps);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alertDialog.dismiss();
    }

    private void initView() {
        mTitleToolbar = getView().findViewById(R.id.title_toolbar);
        mTitleTv = getView().findViewById(R.id.title_tv);
        mGifRecycle = getView().findViewById(R.id.gif_recycle);
        mFileText = getView().findViewById(R.id.file_text);
        mDelayText = getView().findViewById(R.id.delay_text);
        mDelayBar = getView().findViewById(R.id.delay_bar);
        mBottomView = getView().findViewById(R.id.bottom_view);
        mGenerate = getView().findViewById(R.id.generate);
        mClear = getView().findViewById(R.id.clear);
    }
}
