package io.github.artenes.speedbro.models;

/**
 * A state where you can load an object
 * and this loading process has a loading or error state
 * @param <T> the type of the object to load
 */
public class DataState<T> extends State {

    private boolean isLoading;
    private boolean hasError;
    private T data;

    public boolean isLoading() {
        return isLoading;
    }

    public DataState<T> setLoading(boolean loading) {
        isLoading = loading;
        return this;
    }

    public boolean hasError() {
        return hasError;
    }

    public DataState<T> setHasError(boolean hasError) {
        this.hasError = hasError;
        return this;
    }

    public T getData() {
        return data;
    }

    public DataState<T> setData(T data) {
        this.data = data;
        return this;
    }

}