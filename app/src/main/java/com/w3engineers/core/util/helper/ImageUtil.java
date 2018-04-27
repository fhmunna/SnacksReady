package com.w3engineers.core.util.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.data.local.appconst.DirConst;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

/**
 * Created by LENOVO process 10/18/2017.
 */
/*
*  ****************************************************************************
*  * Created by : Mimo Saha on 19-10-17 at 4:02 PM.
*  * Email : mimosaha@w3engineers.com
*  *
*  * Responsibility: All Image Related Code
*  *
*  * Last edited by : Md. Hasnain on 04/11/18
*  * History: New functions added for getting byte array from image file
*  *
*  * Last Reviewed by : Md. Hasnain on 04/11/18
*  ****************************************************************************
*/
public class ImageUtil {
    private static ImageUtil imageUtil = null;
    private final int PROFILE_THUMB = 1, PROFILE_LARGE = 2;
    private HashMap<Integer, Uri> uriMapper;

    private ImageUtil() {
        uriMapper = new HashMap<>();
        directoryChecker();
    }

    /*
    * SingleTon object
    * Last Edit: 02/11/2017
    * By: Sudipta
    * */
    public static ImageUtil on() {
        if (imageUtil == null)
            imageUtil = new ImageUtil();
        return imageUtil;
    }

    public Uri getImageUri(String imageStringUri, int contactId) {

        Uri storedImageUri = getImageUri(contactId);

        if (storedImageUri == null && TextUtils.isEmpty(imageStringUri)) {
            return null;
        }

        if (storedImageUri == null) {
            Uri imageUri = Uri.parse(imageStringUri);
            uriMapper.put(contactId, imageUri);
            storedImageUri = imageUri;
        }

        return storedImageUri;
    }


    private Uri getImageUri(int contactId) {
        return uriMapper.get(contactId);
    }

    private void directoryChecker() {
        Logger.error("dirChecker() Method", "Fired");
        File avatarDirectory = new File(DirConst.AVATAR_DIRECTORY);

        if (avatarDirectory.exists())
            return;

        File rootDirectory = new File(DirConst.ROOT_DIRECTORY);

        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }

        avatarDirectory.mkdirs();
    }

    private Bitmap processImageSize(int imageType, Bitmap bitmap) {

        if (bitmap == null)
            return null;

        float imageHeight = 0;
        float imageWidth = 0;

        switch (imageType) {
            case PROFILE_LARGE:
                imageWidth = 1260;
                imageHeight = 1060;
                break;
            case PROFILE_THUMB:
                imageWidth = 80;
                imageHeight = 80;
                break;
        }

        float width = bitmap.getWidth();
        float height = bitmap.getHeight();

        float scaleWidth = (width > imageWidth) ? imageWidth / width : 1;
        float scaleHeight = (height > imageHeight) ? imageHeight / width : 1;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, false);

    }

    public void processImage(Bitmap bitmap) {
        bitmapLargeSave(bitmap);
        bitmapThumbSave(bitmap);
    }

    public Bitmap getProcessedLargeBitmap(Bitmap bitmap) {
        return processImageSize(PROFILE_LARGE, bitmap);
    }

    private void bitmapThumbSave(Bitmap bitmap) {

        String fileName = DirConst.AVATAR_DIRECTORY + DirConst.MY_AVATAR_THUMB;

        Bitmap compressedBitmap = processImageSize(PROFILE_THUMB, bitmap);
        bitmapSaveFunctionality(compressedBitmap, fileName);
    }

    private void bitmapLargeSave(Bitmap bitmap) {

        String fileName = DirConst.AVATAR_DIRECTORY + DirConst.MY_AVATAR_LARGE;

        Bitmap compressedBitmap = processImageSize(PROFILE_LARGE, bitmap);
        bitmapSaveFunctionality(compressedBitmap, fileName);
    }

    private void bitmapSaveFunctionality(Bitmap imageBitmap, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBase64String(String imagePath) {

        File imageFilePath = new File(imagePath);

        if (!imageFilePath.exists()) return null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Remove options from decode file during decode it is providing null bitmap
        // Might be the low size image the options are optional for decoding image path
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath.getAbsolutePath());
        if (bitmap == null) return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();

        if (byteArray != null) {
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return null;
    }

    public String storeImageFromBase64String(String directoryName, String filename, String source) {

        String filePath = null;
        if (source != null && source.length() > 20) {
            Logger.error("Image_s", "Image string not null");
            byte[] decodedString = Base64.decode(source, Base64.DEFAULT);
            File sdIconStorageDir = new File(directoryName);
            if (!sdIconStorageDir.exists()) {
                sdIconStorageDir.mkdirs();
            }

            try {
                if (filename.contains(".jpg")) {
                    filePath = sdIconStorageDir.toString() + "/" + filename;
                } else {
                    filePath = sdIconStorageDir.toString() + "/" + filename + ".jpg";
                }

                FileOutputStream fos = new FileOutputStream(filePath);
                fos.write(decodedString);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return filePath;
            }
        }
        return filePath;
    }

    public String saveFriendsImage(String peerId, String imageString) {
        String imagePath = "";

        if (PermissionUtil.on(AppController.getContext()).isAllowed(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            try {
                if (!TextUtils.isEmpty(imageString)) {
                    imagePath = ImageUtil.on().storeImageFromBase64String(DirConst.AVATAR_DIRECTORY, "" + peerId, imageString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return imagePath;
    }

    private void openGallery(Activity activity, int requestCode) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, requestCode);
    }

    public void selectPhotoChooser(Activity activity, int requestCode) {
        if (PermissionUtil.on(activity).request(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            openGallery(activity, requestCode);
        } else {
            PermissionUtil.on(activity).request(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    public String getCompressedImagePath(String fileUrl) {
        long maxImageSize = 1 * 1024 * 1024;

        File file = new File(fileUrl);

        if (file.length() > maxImageSize) {
            fileUrl = ImageScaler.onScaler(AppController.getContext()).getCompressedImage(fileUrl);
            Logger.log("FileSize", "File si = " + new File(fileUrl).length());
            return getCompressedImagePath(fileUrl);
        } else {
            Logger.log("FileSize", "File si = " + file.length());
            return fileUrl;
        }
    }

    public String getCompressedImagePathForMissingRes(String fileUrl) {
        long maxImageSize = 1 * 512 * 1024;

        File file = new File(fileUrl);

        if (file.length() > maxImageSize) {
            fileUrl = ImageScaler.onScaler(AppController.getContext()).getCompressedImage(fileUrl);
            Logger.log("FileSize", "File si = " + new File(fileUrl).length());
            return getCompressedImagePath(fileUrl);
        } else {
            Logger.log("FileSize", "File si = " + file.length());
            return fileUrl;
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public byte[] getByteFromPath(String path) {
        RandomAccessFile aFile;
        FileChannel inChannel;
        ByteBuffer buffer = null;
        MappedByteBuffer mapbuffer = null;
        try {
            aFile = new RandomAccessFile(getCompressedImagePath(path), "r");
            inChannel = aFile.getChannel();
            long fileSize = inChannel.size();
            buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);

//            mapbuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
//            mapbuffer.load();

            inChannel.close();
            aFile.close();
        } catch (IOException exc) {
        }
        return buffer.array();
        //return mapbuffer.array();
    }
}
