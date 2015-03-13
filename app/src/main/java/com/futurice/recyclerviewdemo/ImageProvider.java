package com.futurice.recyclerviewdemo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vera Izrailit on 11/03/15.
 */
public class ImageProvider {

    public static List<Uri> getImages(Context context, int amount) {
        String[] mProjection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mProjection, null, null, MediaStore.Images.Media.DATE_ADDED);
        int i = 0;

        if (cursor != null) {
            int uriColumn = cursor.getColumnIndex(
                    MediaStore.Images.Media.DATA);

            List<Uri> images = new ArrayList<Uri>();
            while (cursor.moveToNext() && i < amount) {
                images.add(Uri.parse(cursor.getString(uriColumn)));
                i++;
            }
            cursor.close();
            return images;
        } else {
            return null;
        }
    }
}
