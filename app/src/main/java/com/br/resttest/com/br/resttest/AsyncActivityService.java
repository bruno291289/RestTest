package com.br.resttest.com.br.resttest;

public interface AsyncActivityService<T> {
    void startOperation();
    void endOperation(T content, String error);
}
