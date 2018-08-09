package book.store.common;

public class Result<T> {
    private boolean success;
    private String error;
    private T model;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public Result(boolean success, T model) {
        this.success = success;
        this.model = model;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
