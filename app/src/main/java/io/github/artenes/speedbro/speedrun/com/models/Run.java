package io.github.artenes.speedbro.speedrun.com.models;

import java.util.List;

public class Run {

    private Data<Game> game;
    private Data<Category> category;
    private Data<List<Player>> players;
    private Time times;

    public Data<Game> getGame() {
        return game;
    }

    public Data<Category> getCategory() {
        return category;
    }

    public Data<List<Player>> getPlayers() {
        return players;
    }

    public Time getTimes() {
        return times;
    }
}
