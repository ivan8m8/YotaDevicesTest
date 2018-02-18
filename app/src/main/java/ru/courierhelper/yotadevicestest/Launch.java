package ru.courierhelper.yotadevicestest;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Ivan on 14.02.2018.
 */

public class Launch {
    private String rocketName;
    private long launchDate;
    private String missionPatchUrl;
    private String details;

    private Bitmap bitmap;

    private String articleLink;
    private String videoLink;

    public Launch(String rocketName, long launchDate, String missionPatchUrl, String details, String articleLink, String videoLink) {
        this.rocketName = rocketName;
        this.launchDate = launchDate;
        this.missionPatchUrl = missionPatchUrl;
        this.details = details;
        this.articleLink = articleLink;
        this.videoLink = videoLink;
    }

    public String getRocketName() {
        return rocketName;
    }

    public void setRocketName(String rocketName) {
        this.rocketName = rocketName;
    }

    public long getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(long launchDate) {
        this.launchDate = launchDate;
    }

    public String getMissionPatchUrl() {
        return missionPatchUrl;
    }

    public void setMissionPatchUrl(String missionPatchUrl) {
        this.missionPatchUrl = missionPatchUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
