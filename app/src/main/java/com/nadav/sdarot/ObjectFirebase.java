package com.nadav.sdarot;

/**
 * Created by Nadav on 10/06/2016.
 */
public class ObjectFirebase {

    public String nameOfSeries;
    public String numOfSeason;
    public String numOfEpisode;
    public String imgUrl;
    public String Date;
    public String pauseTime;
    public String nameOfEpisode;
    public String plot;

    public ObjectFirebase() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ObjectFirebase(String nameOfSeries, String numOfSeason, String numOfEpisode, String imgUrl, String Date, String pauseTime, String nameOfEpisode, String plot) {
        this.nameOfSeries = nameOfSeries;
        this.numOfSeason = numOfSeason;
        this.numOfEpisode = numOfEpisode;
        this.imgUrl = imgUrl;
        this.Date = Date;
        this.pauseTime = pauseTime;
        this.plot = plot;
        this.nameOfEpisode = nameOfEpisode;
    }

}