package io.github.artenes.speedbro.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;

@Entity
public class CachedResponse {

    @PrimaryKey
    @NonNull
    private String url;

    @NonNull
    private String response;

    @NonNull
    private long expires_in;

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getResponse() {
        return response;
    }

    public void setResponse(@NonNull String response) {
        this.response = response;
    }

    @NonNull
    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(@NonNull long expires_in) {
        this.expires_in = expires_in;
    }

    public Calendar asCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(expires_in);
        return calendar;
    }

}