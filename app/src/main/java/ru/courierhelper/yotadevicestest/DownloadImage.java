package ru.courierhelper.yotadevicestest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Ivan on 17.02.2018.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;

    public interface OnImageLoadedListener {
        void onImageLoadedListener(Bitmap bitmap);
    }

    OnImageLoadedListener onImageLoadedListener = null;

    public DownloadImage(ImageView imageView, OnImageLoadedListener onImageLoadedListener) {
        this.imageView = imageView;
        this.onImageLoadedListener = onImageLoadedListener;
    }

    public DownloadImage(OnImageLoadedListener onImageLoadedListener) {
        this.onImageLoadedListener = onImageLoadedListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap;
        try {
            InputStream inputStream = new URL(strings[0]).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        onImageLoadedListener.onImageLoadedListener(bitmap);
    }

//    private static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight){
//
//        int width = options.outWidth;
//        int height = options.outHeight;
//        int inSampleSize = 1;
//
//        if (width > reqWidth || height > reqHeight) {
//
//            int halfWidth = width / 2;
//            int halfHeight = height / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and
//            // keeps both height and width larger than the requested ones
//            while ((halfWidth / inSampleSize) >= reqWidth
//                    && (halfHeight / inSampleSize) >= reqHeight) {
//                inSampleSize *= 2;
//            }
//        }
//        return inSampleSize;
//    }
//
//    @Override
//    protected Bitmap doInBackground(String... strings) {
//
//        Bitmap bitmap = null;
//
//        try {
//            InputStream inputStream = new URL(strings[0]).openStream();
//
//            // First decode with inJustDecodeBounds=true to check the image dimensions
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(inputStream, null, options);
//
//            options.inSampleSize = calculateInSampleSize(options, 400, 400);
//
//            options.inJustDecodeBounds = false;
//
//            return BitmapFactory.decodeStream(inputStream, null, options);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
