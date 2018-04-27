package com.w3engineers.core.util.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.codekidlabs.storagechooser.StorageChooser;
import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.data.local.appconst.DirConst;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
*  ****************************************************************************
*  * Created by : Md. Hasnain on 04-06-18 at 4:52 PM.
*  * Email : ashik.pstu.cse@gmail.com
*  *
*  * Responsibility: All File Related Code
*  * Requirement to use:
*  1.implementation "com.github.codekidX:storage-chooser:2.0.3" in app build gradle
*  2. in root build gradle
*  * allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
*  *
*  * Last edited by : Md. Hasnain on 04-06-18
*  *
*  * Last Reviewed by : Md. Hasnain on 04-06-18
*  ****************************************************************************
*/
public class FileUtil {
    private static FileUtil fileUtil = null;
    private final int PROFILE_THUMB = 1, PROFILE_LARGE = 2;
    private HashMap<Integer, Uri> uriMapper;
    private StorageChooser chooser;

    private FileUtil() {
        uriMapper = new HashMap<>();
        directoryChecker();
    }

    /*
    * SingleTon object
    * Last Edit: 02/11/2017
    * By: Sudipta
    * */
    public static FileUtil on() {
        if (fileUtil == null)
            fileUtil = new FileUtil();
        return fileUtil;
    }

    public Uri getFileUri(String fileStringUri, int contactId) {

        Uri storedFileUri = getImageUri(contactId);

        if (fileStringUri == null && TextUtils.isEmpty(fileStringUri)) {
            return null;
        }

        if (storedFileUri == null) {
            Uri imageUri = Uri.parse(fileStringUri);
            uriMapper.put(contactId, imageUri);
            storedFileUri = imageUri;
        }

        return storedFileUri;
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

    private void openFileChooser(Activity activity, FragmentManager fragmentManager, String type,
                                 FileChooserListener listener) {
        if(type == null || type.isEmpty()) type = StorageChooser.FILE_PICKER;
        chooser = new StorageChooser.Builder()
                .withActivity(activity)
                .withFragmentManager(fragmentManager)
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(type) //Allow to choose any type of file
                //.actionSave(true)
                //.withPreference(sharedPreferences)
                .build();

        // get path that the user has chosen
        chooser.setOnSelectListener(path -> {
            Log.e("SELECTED_PATH", path);
            listener.onFileSelected(path);
        });

        chooser.setOnMultipleSelectListener(arrayList -> listener.onMultipleFileSelected(arrayList));

        chooser.setOnCancelListener(() -> listener.onChooserCanceled());

        //show the file chooser
        chooser.show();
    }

    public void selectFileChooser(Activity activity, FragmentManager fragmentManager, String type,
                                  FileChooserListener listener) {
        if (PermissionUtil.on(activity).request(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            openFileChooser(activity, fragmentManager, type, listener);
        } else {
            PermissionUtil.on(activity).request(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    public String getCompressedFilePath(String fileUrl) {
        long maxImageSize = 1 * 1024 * 1024;

        File file = new File(fileUrl);

        if (file.length() > maxImageSize) {
            fileUrl = ImageScaler.onScaler(AppController.getContext()).getCompressedImage(fileUrl);
            Logger.log("FileSize", "File si = " + new File(fileUrl).length());
            return getCompressedFilePath(fileUrl);
        } else {
            Logger.log("FileSize", "File si = " + file.length());
            return fileUrl;
        }
    }

    public String getFileExtension(String filePath){
        if(filePath == null || TextUtils.isEmpty(filePath)) return "unknown";

        String extension =  filePath.substring(filePath.lastIndexOf(".") + 1);

        if(extension == null || TextUtils.isEmpty(extension)) return "unknown";

        return extension;
    }

    public long getFileSize(String filePath){
        if(filePath == null || TextUtils.isEmpty(filePath)) return 0;

        File file = new File(filePath);

        if(!file.exists()) return -1;

        return file.length();
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
        try {
            //aFile = new RandomAccessFile(getCompressedFilePath(path), "r");
            aFile = new RandomAccessFile(path, "r");
            inChannel = aFile.getChannel();
            long fileSize = inChannel.size();
            buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);

            inChannel.close();
            aFile.close();
        } catch (IOException exc) {
        }
        return buffer.array();
    }

    @SuppressLint("StaticFieldLeak")
    public String saveFile(final Context context, String filePath) {
        byte[] fileInByte = getByteFromPath(filePath);

        File myDir = new File(DirConst.FILE_DIRECTORY);
        if(!myDir.exists()) myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname="File-" + n + "." + getFileExtension(filePath);

        //final Bitmap finalBitmap = decodeByteArray(fileInByte, 0, fileInByte.length);
        final File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {

            new AsyncTask<Void, Void, Void>() {
                /**
                 * Override this method to perform a computation on a background thread. The
                 * specified parameters are the parameters passed to {@link #execute}
                 * by the caller of this task.
                 * <p>
                 * This method can call {@link #publishProgress} to publish updates
                 * on the UI thread.
                 *
                 * @param params The parameters of the task.
                 * @return A result, defined by the subclass of this task.
                 * @see #onPreExecute()
                 * @see #onPostExecute
                 * @see #publishProgress
                 */
                @Override
                protected Void doInBackground(Void... params) {
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        out.write(fileInByte);
                        //finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                        //new Compressor(context).compressToFile(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return  fname;
    }

    @SuppressLint("StaticFieldLeak")
    public String downloadFile(byte[] fileInByte, String fname) {

        File myDir = new File(DirConst.ROOT_DIRECTORY_DOWNLOAD);
        if(!myDir.exists()) myDir.mkdirs();

        final File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {

            new AsyncTask<Void, Void, Void>() {
                /**
                 * Override this method to perform a computation on a background thread. The
                 * specified parameters are the parameters passed to {@link #execute}
                 * by the caller of this task.
                 * <p>
                 * This method can call {@link #publishProgress} to publish updates
                 * on the UI thread.
                 *
                 * @param params The parameters of the task.
                 * @return A result, defined by the subclass of this task.
                 * @see #onPreExecute()
                 * @see #onPostExecute
                 * @see #publishProgress
                 */
                @Override
                protected Void doInBackground(Void... params) {
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        out.write(fileInByte);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return  file.getPath();
    }

    public boolean deleteFile(String filePath){
        if(filePath == null || TextUtils.isEmpty(filePath)) return false;

        return deleteRecursive(new File(filePath));
    }

    private boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        return fileOrDirectory.delete();
    }

    public interface FileChooserListener{
        void onFileSelected(String path);
        void onMultipleFileSelected(ArrayList<String> allPath);
        void onChooserCanceled();
    }
}
