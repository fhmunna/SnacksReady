package com.w3engineers.core.util.helper;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;

import com.google.common.hash.Hashing;

import org.apache.commons.collections4.ListUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Created by LENOVO on 10-Oct-17.
 */

public final class DataUtil {

    private DataUtil() {
    }

    private static Random random = new Random();

    public static int randInt(int min, int max) {
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static int compare(byte[] left, byte[] right) {
        int i = 0;
        for (int j = 0; i < left.length && j < right.length; ++j) {
            int a = left[i] & 255;
            int b = right[j] & 255;
            if (a != b) {
                return a - b;
            }
            ++i;
        }
        return left.length - right.length;
    }

    public static void sort(List list) {
        sort(list, null);
    }

    public static void sort(List list, Comparator comparator) {
        if (list == null) return;
        if (comparator == null) {
            Collections.sort(list);
        } else {
            Collections.sort(list, comparator);
        }
    }

    public static <T extends Number> List<T> subtract(List<T> parents, List<T> childs) {
        if (parents == null) {
            return null;
        }
        if (childs == null) {
            return parents;
        }
        return ListUtils.subtract(parents, childs);
    }

    public static <T> T randomItem(Set<T> sets) {
        return randomItem(new ArrayList<T>(sets));
    }

    public static <T> T randomItem(List<T> list) {
        if (DataUtil.isEmpty(list)) return null;
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static boolean isEmpty(byte[] dataValue) {
        return Arrays.equals(dataValue, null);
    }

    public static boolean isEmpty(String dataValue) {
        return TextUtils.isEmpty(dataValue);
    }

    public static boolean isEmpty(Integer... dataValues) {
        for (Integer dataValue : dataValues) {
            if (isEmpty(dataValue)) return true;
        }
        return false;
    }

    public static boolean isEmpty(ContentValues values) {
        return (values == null || values.size() == 0);
    }

    public static boolean allEmpty(Integer... dataValues) {
        for (Integer dataValue : dataValues) {
            if (!isEmpty(dataValue)) return false;
        }
        return true;
    }


/*    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }*/

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static String getSmaller(String left, String right) {
        return (left.compareTo(right) > 0) ? right : left;
    }

    public static int getSmaller(int left, int right) {
        return (left > right) ? right : left;
    }

    public static String getGreater(String left, String right) {
        return (left.compareTo(right) > 0) ? left : right;
    }

    public static int alter(int value) {
        return -value;
    }

    public static int absolute(int value) {
        return Math.abs(value);
    }

    public static boolean isEmpty(CharSequence dataValue) {
        return TextUtils.isEmpty(dataValue);
    }

    public static boolean isEmpty(Integer dataValue) {
        return dataValue <= 0;
    }

    public static boolean isEmpty(Long dataValue) {
        return dataValue == 0L;
    }

    public static boolean isEmpty(Float dataValue) {
        return dataValue == 0.0f;
    }

    public static boolean isEmpty(Bitmap dataValue) {
        return dataValue == null;
    }

    public static boolean isEmpty(BitmapDrawable dataValue) {
        return dataValue == null;
    }

    public static boolean isEmpty(Uri dataValue) {
        return dataValue == null;
    }

    public static boolean isEmpty(PendingIntent dataValue) {
        return dataValue == null;
    }

    public static boolean equals(String left, String right) {

        if (isEmpty(left) && isEmpty(right)) return true; // both null or empty then equals

        if (isEmpty(left) || isEmpty(right))
            return false; // any one empty but not both then not equals

        return left.equals(right);
    }

    public static String getAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static <T> List<T> toList(Collection<T> collection) {
        if (DataUtil.isEmpty(collection)) return null;
        return new ArrayList<>(collection);
    }

    public static int readInt(ByteBuffer buffer) {
        return buffer.remaining() >= 4 ? buffer.getInt() : 0;
    }

    public static long readLong(ByteBuffer buffer) {
        return buffer.remaining() >= 8 ? buffer.getLong() : 0L;
    }

    public static byte[] readRemaining(ByteBuffer buffer) {
        if (!buffer.hasRemaining()) return null;
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return data;
    }

    public static String toString(byte[] value) {
        return new String(value);
    }

    public static long get256Hash() {
        String uuid = UUID.randomUUID().toString();
        return get256Hash(uuid);
    }


    public static long get256Hash(String value) {
        return get256Hash(value.getBytes());
    }

    public static long get256Hash(byte[] value) {
        return Math.abs(Hashing.sha256().newHasher().putBytes(value).hash().asLong());
    }

    // this api needs to be updated so that it can produce accurate hash of all values
    public static long sumOf(List<Long> values) {
        if (isEmpty(values)) {
            return 0L;
        }
        long sum = 0L;
        for (long value : values) {
            sum += value;
        }
        return sum;
    }

}
