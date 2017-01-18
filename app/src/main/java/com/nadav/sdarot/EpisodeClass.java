package com.nadav.sdarot;

public class EpisodeClass {

    private int fruit_resource;
    private String name_of_series;
    private String num_of_season;
    private String num_of_episode;
    public String title;
    public boolean FlagIfSwipe;
    public String Date;
    public String plot;
    public Double RANK;
    public String imageUrl;
    public String nameOfEpisode;
    public String NextNumOfSeason;
    public String NextNumOfEpisode;
    public String pausetime;


    public EpisodeClass() {

    }

    public EpisodeClass(int fruit_resource, String name_of_series, String num_of_season, String num_of_episode) {
        super();

        this.setImageUrl("none");
        this.setNameOfseries(name_of_series);
        this.setNumOfSeason(num_of_season);
        this.setNumOfEpisode(num_of_episode);
        this.setDate("");
    }

    public EpisodeClass(String nameOfSeries, String numOfSeason, String numOfEpisode, String imgUrl, String Date, String pauseTime, String nameOfEpisode, String plot) {
        this.name_of_series = nameOfSeries;
        this.num_of_season = numOfSeason;
        this.num_of_episode = numOfEpisode;
        this.imageUrl = imgUrl;
        this.Date = Date;
        this.pausetime = pauseTime;
        this.plot = plot;
        this.nameOfEpisode = nameOfEpisode;
    }



    public EpisodeClass(String imageUrl, String name_of_series, String num_of_season, String num_of_episode, String Date) {
        super();
        this.setImageUrl(imageUrl);
        this.setNameOfseries(name_of_series);
        this.setNumOfSeason(num_of_season);
        this.setNumOfEpisode(num_of_episode);
        this.setDate(Date);
    }

    public EpisodeClass(String imageUrl, String name_of_series, String plot, Double rank) {
        super();
        this.setImageUrl(imageUrl);
        this.setNameOfseries(name_of_series);
        this.setPlot(plot);
        this.setRank(rank);
    }

    public EpisodeClass(String imageUrl, String name_of_series) {
        super();
        this.setImageUrl(imageUrl);
        this.setNameOfseries(name_of_series);

    }

    public int getFruit_resource() {
        return fruit_resource;
    }
    public void setFruit_resource(int fruit_resource) {
        this.fruit_resource = fruit_resource;
    }

    public String getName_of_series() {
        return name_of_series;
    }
    public void setNameOfseries(String name_of_series) {
        this.name_of_series = name_of_series;
    }
    public String getNum_of_season() {
        return num_of_season;
    }
    public void setNumOfSeason(String num_of_season) {
        this.num_of_season = num_of_season;
    }
    public String getNum_of_episode() {
        return num_of_episode;
    }
    public void setNumOfEpisode(String num_of_episode) {
        this.num_of_episode = num_of_episode;
    }
    public void setImageUrl(String ImageUrl) {
        this.imageUrl = ImageUrl;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public void setRank(Double rank) {
        this.RANK = rank;
    }
    public void setDate(String date) {
        this.Date = date;
    }
    public void setNameOfEpisode(String nameOfEpisode) {
        this.nameOfEpisode = nameOfEpisode;
    }

    public void setNextNumOfSeason(String NextNumOfSeason) {
        this.NextNumOfSeason = NextNumOfSeason;
    }
    public void setNextNumOfEpisode(String NextNumOfEpisode) {
        this.NextNumOfEpisode = NextNumOfEpisode;
    }

    public void setPausetime(String pausetime) {
        this.pausetime = pausetime;
    }



}
