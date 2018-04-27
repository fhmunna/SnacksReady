package com.w3engineers.core.util.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.w3engineers.core.AppController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by LENOVO on 05-Oct-17.
 */

public final class AndroidUtil {
    private AndroidUtil() {
    }

    public static String getUserName(Context context) {
        Cursor c = context.getApplicationContext().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        String name = null;
        if (c != null && c.moveToFirst()) {
            name = c.getString(c.getColumnIndex("display_name"));
        }
        if (c != null) {
            c.close();
        }
        return name;
    }

    /*public static String getDeviceName() {
        String deviceName = DeviceName.getDeviceName();
        return deviceName;
    }*/

    public static long getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return DataUtil.get256Hash(androidId);
    }

    // Creating
    public static <T extends Fragment> T newFragment(Class<T> fragmentClass) {
        try {
            return fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Checking and Getting
    private static TextToSpeech textToSpeech;

    public static void initTextToSpeech(Context context) {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.getDefault());
                    }
                }
            });
        }
    }

    public static void speak(String text) {
        if (textToSpeech != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public static void stopTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    public static boolean needThread(Thread thread) {
        return (thread == null || !thread.isAlive());
    }

    public static boolean isEmpty(Bitmap dataValue) {
        return dataValue == null;
    }

    public static boolean isEmpty(Uri dataValue) {
        return dataValue == null;
    }

    public static Thread createThread(Runnable runnable) {
        return new Thread(runnable);
    }

    public static Thread createThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable);
        thread.setName(name);
        thread.setDaemon(true);
        return thread;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    public static boolean hasCursor(Cursor cursor) {

        if (cursor == null || cursor.isClosed()) return false;

        if (cursor.getCount() <= 0) {
            closeCursor(cursor);
            return false;
        }

        return true;
    }

    public static void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public static String getString(Cursor cursor, String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }

    // Builds information
    public static String getApplicationId(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.packageName;
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static String toTitleCase(String text) {

        if (TextUtils.isEmpty(text)) return text;

        StringBuilder titleCase = new StringBuilder(text.length());

        boolean nextTitleCase = true;

        for (char c : text.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }
        return titleCase.toString();
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException nameException) {
            return null;
        }
    }

    public static String randomHash() {
        String uuid = UUID.randomUUID().toString();
        return md5Hash(uuid);
    }

    public static String md5Hash(String value) {

        if (value == null) return null;

        try {

            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(value.getBytes());

            return toHexString(digest.digest());

        } catch (NoSuchAlgorithmException e1) {
            return Integer.toHexString(value.hashCode());
        }
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //Database


    // Handler

    public static String getString(Context context, int resourceId) {
        return context.getString(resourceId);
    }

    public static String getString(Context context, int resourceId, Object... formatArgs) {
        return context.getString(resourceId, formatArgs);
    }

    public static String[] getStringArray(Context context, int arrayId) {
        return context.getResources().getStringArray(arrayId);
    }

    public static int getColor(Context context, int colorId) {
        return ContextCompat.getColor(context, colorId);
    }

/*    public static String getAppName(Context context) {
        return Iterables.getLast(Splitter.on(".").trimResults().split(context.getPackageName()));
    }*/

/*    public static long get256Hash(File file) {
        if (file == null || !file.exists()) return 0L;
        try {
            return MediaStore.Files.hash(file, Hashing.sha256()).asLong();
        } catch (IOException e) {
            return 0L;
        }
    }*/

/*
    public static long get256Hash() {
        String uuid = UUID.randomUUID().toString();
        return get256Hash(uuid);
    }
*/

/*
    public static long get256Hash(String value) {
        return get256Hash(value.getBytes());
    }
*/

/*    public static long get256Hash(byte[] value) {
        return Math.abs(Hashing.sha256().newHasher().putBytes(value).hash().asLong());
    }*/

    private static final Handler uiHandler = new Handler(Looper.getMainLooper());
    private static final long defaultDelay = 270L;

    public static void post(Runnable runnable) {
        uiHandler.post(runnable);
    }

    public static void post(Runnable runnable, long time) {
        uiHandler.postDelayed(runnable, time);
    }

    public static void postDelay(Runnable runnable) {
        post(runnable, defaultDelay);
    }

    public static void remove(Runnable runnable) {
        uiHandler.removeCallbacks(runnable);
    }

    private static void setLanguage(String localeName) {
        Locale myLocale = new Locale(localeName);
        Resources res = AppController.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}