package com.w3engineers.core.util.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Anjan Debnath on 11/27/2017.
 * Copyright (c) 2017, W3 Engineers Ltd. All rights reserved.
 */

public class ImageSaver {

    private String directoryName = "images";
    private String fileName = "image.png";
    private Context context;
    private boolean external;
    private OnImageSaveResponse onImageSaveResponse;
    public ImageSaver(Context context) {
        this.context = context;
    }

    public ImageSaver setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ImageSaver setExternal(boolean external) {
        this.external = external;
        return this;
    }

    public ImageSaver setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    public OnImageSaveResponse setOnImageSaverCallBack(OnImageSaveResponse callBack) {
        this.onImageSaveResponse = callBack;
        return onImageSaveResponse;
    }

    public void save(Bitmap bitmapImage, OnImageSaveResponse onImageSaveResponse) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    if (onImageSaveResponse != null) {
                        String directory = directoryName;
                        onImageSaveResponse.imageSaveSuccess(directory);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (onImageSaveResponse != null) {
                    onImageSaveResponse.imageSaveFailed();
                }
            }
        }
    }

    @NonNull
    private File createFile() {
        File directory;
        if (external) {
            directory = getAlbumStorageDir(directoryName);
        } else {
            directory = context.getDir(directoryName, Context.MODE_PRIVATE);
        }
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("ImageSaver", "Error creating directory " + directory);
        }

        return new File(directory, fileName);
    }

    private File getAlbumStorageDir(String albumName) {
        String baseDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        return new File(baseDirectory, albumName);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }

        return false;
    }

    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public interface OnImageSaveResponse {
        void imageSaveSuccess(String imagePath);

        void imageSaveFailed();
    }
}