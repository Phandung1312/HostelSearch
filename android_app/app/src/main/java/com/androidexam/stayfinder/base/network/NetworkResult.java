package com.androidexam.stayfinder.base.network;

public abstract class NetworkResult<T extends Object> {
    public static final class Success<T extends Object> extends NetworkResult<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Error extends NetworkResult<Object> {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}