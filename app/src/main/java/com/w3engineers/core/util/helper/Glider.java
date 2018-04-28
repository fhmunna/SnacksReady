package com.w3engineers.core.util.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.w3engineers.core.snacksready.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutionException;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/15/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose:
* *
* * Last Edited by : Md. Hasnain on 04/11/18
* * History:
* * 1: New function added for loading Drawable
* * 2:
* *
* * Last Reviewed by : Md. Hasnain on 04/11/18
* ****************************************************************************
*/

public class Glider {
    static Context thisContext;

    //Init Glider class with context
    public static void init(Context context) {
        thisContext = context;
    }

    public static void show(String location, ImageView imageView) {
        try {
            if (location != null && !location.isEmpty() && imageView != null && thisContext != null) {
                Glide.with(thisContext)
                        .load(location)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_app)
                        .into(imageView);
            }
        } catch (Exception e) {
        }
    }

    public static void showBitmap(Bitmap bitmap, ImageView imageView) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(thisContext)
            .load(stream.toByteArray()).asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .skipMemoryCache(true)
            .into(imageView);
    }

    public static void showGallery(String location, ImageView imageView) {
        try {
            if (location != null && !location.isEmpty() && imageView != null && thisContext != null) {
                Glide.with(thisContext)
                        .load(location)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .placeholder(R.drawable.ic_app)
                        .into(imageView);
            }
        } catch (Exception e) {
        }
    }

    public static void showDrawable(int drawable, ImageView imageView) {
        Glide.with(thisContext)
                .load("")
                .placeholder(drawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imageView);
    }

    public static void showGif(Drawable drawable, ImageView imageView) {
        Glide.with(thisContext)
                .load(drawable)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imageView);
    }

    public static void showCircular(final ImageView imageView, Object location) {
        if (location != null && imageView != null && thisContext != null) {

            Glide.with(thisContext)
                    .load(location)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(resource);
                        }
                    });
        }
    }

    public static void loadUserAvatarWithoutAnimation(String path, final ImageView imageView) {
        if (imageView == null) return;
        Glide.with(thisContext.getApplicationContext())
                .load(path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap arg0, GlideAnimation<? super Bitmap> arg1) {
                        // TODO Auto-generated method stub
                        imageView.setImageBitmap(arg0);
                    }
                });

        //.override(18, 18)
    }

    public static void showCircularWithClearCache(ImageView imageView, Object location) {
        if (location != null && imageView != null && thisContext != null) {

            Glide.with(thisContext)
                    .load(location)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(imageView);
        }
    }

    public static void showWithPlaceholder(ImageView imageView, String location) {
        if (location != null && imageView != null && thisContext != null) {
            Glide.with(thisContext)
                    .load(location)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_app)
                    .into(imageView);

        }
    }

    public static void loadUserAvatar(String path, ImageView imageView) {
        if (imageView == null) return;
        Glide.with(thisContext.getApplicationContext())
                .load(path)//passing your url to load image.
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//just set override like this
                .error(R.drawable.ic_app)
                .centerCrop()
                .into(imageView);

        //.override(18, 18)
    }

    public static void loadUserAvatarInMessagePage(String path, ImageView imageView) {
        if (imageView == null) return;
        File file = new File(path);

        Glide.with(thisContext.getApplicationContext())
                .load(file)//passing your url to load image.
                .diskCacheStrategy(DiskCacheStrategy.ALL)//just set override like this
                .error(R.drawable.ic_app)
                .centerCrop()
                .signature(new StringSignature(String.valueOf(file.lastModified())))
                .into(imageView);

        //.override(18, 18)
    }

    public static void loadMenuImage(final Context context, String imageUri, final MenuItem menuItem) {

        Glide.with(context)
                .load(imageUri)
                .asBitmap()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        if (menuItem != null)
                            menuItem.setIcon(circularBitmapDrawable);
                    }
                });
    }

    public static Bitmap getBitmap(Uri imageUri, int width, int height) {
        try {
            return Glide.with(thisContext)
                    .load(imageUri)
                    .asBitmap()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void clearCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}