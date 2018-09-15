package io.github.artenes.speedbro.speedrun.com.models;

import java.util.List;

public class PagedResponse<T> {

    private List<T> data;
    private Pagination pagination;

    public List<T> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
