package com.example.xm.util.gifmaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName GifUtil
 * @Author 史正龙
 * @date 2022.02.28 11:15
 */
public class GifUtil {
    public String createGif(List<Bitmap> pics, String file_name, int delay, final Context context) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        gifEncoder.start(baos);//start
        gifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        gifEncoder.setDelay(delay);
        gifEncoder.setTransparent(Color.BLACK);

        //【注意1】开始生成gif的时候，是以第一张图片的尺寸生成gif图的大小，后面几张图片会基于第一张图片的尺寸进行裁切
        //所以要生成尺寸完全匹配的gif图的话，应先调整传入图片的尺寸，让其尺寸相同
        //【注意2】如果传入的单张图片太大的话会造成OOM，可在不损失图片清晰度先对图片进行质量压缩
        for (int i = 0; i < pics.size(); i++) {
            // Bitmap localBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(pics.get(i)), 512, 512);
            gifEncoder.addFrame(pics.get(i));

        }
        gifEncoder.finish();//finish

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM");
        if (!file.exists()) file.mkdir();
        final String path = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + file_name + ".gif";
        try {
            FileOutputStream fos = new FileOutputStream(path);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
