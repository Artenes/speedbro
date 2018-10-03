package io.github.artenes.speedbro.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * A favorite run stored in the local database
 */
@Entity
public class FavoriteRun {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String game_id;

    @NonNull
    private String runner_id;

    private String runner_icon;
    private String runner_name;
    private String runner_flag;
    private String game_cover;
    private String game_title;
    private String category;
    private String place_icon;
    private String place;
    private String time;

    public String getId() {
        return id;
    }

    public String getRunner_icon() {
        return runner_icon;
    }

    public String getRunner_name() {
        return runner_name;
    }

    public String getRunner_flag() {
        return runner_flag;
    }

    public String getGame_cover() {
        return game_cover;
    }

    public String getGame_title() {
        return game_title;
    }

    public String getCategory() {
        return category;
    }

    public String getPlace_icon() {
        return place_icon;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    @NonNull
    public String getGame_id() {
        return game_id;
    }

    @NonNull
    public String getRunner_id() {
        return runner_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRunner_icon(String runner_icon) {
        this.runner_icon = runner_icon;
    }

    public void setRunner_name(String runner_name) {
        this.runner_name = runner_name;
    }

    public void setRunner_flag(String runner_flag) {
        this.runner_flag = runner_flag;
    }

    public void setGame_cover(String game_cover) {
        this.game_cover = game_cover;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPlace_icon(String place_icon) {
        this.place_icon = place_icon;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setGame_id(@NonNull String game_id) {
        this.game_id = game_id;
    }

    public void setRunner_id(@NonNull String runner_id) {
        this.runner_id = runner_id;
    }

}