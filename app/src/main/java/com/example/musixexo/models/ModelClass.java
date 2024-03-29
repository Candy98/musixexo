package com.example.musixexo.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.example.musixexo.homepages.HomepageActivity;

import java.util.ArrayList;

public class ModelClass {
   String activityName;
   int activityIcon;
   String activityLocation;
   String desc,dest;
    String vidDuration;
    String epUrl;
    String color;
    Drawable background;
    int resource;
    String imgUrl;
    Uri imgUri;
    Context context;
    Uri epImgUrl;

    public Uri getEpImgUrl() {
        return epImgUrl;
    }

    public void setEpImgUrl(Uri epImgUrl) {
        this.epImgUrl = epImgUrl;
    }

    public String getEpUrl() {
        return epUrl;
    }

    public void setEpUrl(String epUrl) {
        this.epUrl = epUrl;
    }



    public String getVidDuration() {
        return vidDuration;
    }

    public void setVidDuration(String vidDuration) {
        this.vidDuration = vidDuration;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {

        this.imgUrl = imgUrl;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getColor() {
        return color;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(int activityIcon) {
        this.activityIcon = activityIcon;
    }
}
