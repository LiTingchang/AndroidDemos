package com.bitmask.android.demos.retrofit;

import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("event_date_utc")
    private String eventDateUtc;
    @SerializedName("event_date_unix")
    private Integer eventDateUnix;
    @SerializedName("flight_number")
    private Integer flightNumber;
    @SerializedName("details")
    private String details;
    @SerializedName("links")
    private LinksDTO links;

    public static class LinksDTO {
        @SerializedName("reddit")
        private Object reddit;
        @SerializedName("article")
        private String article;
        @SerializedName("wikipedia")
        private String wikipedia;
    }
}
