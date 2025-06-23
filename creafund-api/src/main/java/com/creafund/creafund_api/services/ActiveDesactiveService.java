package com.creafund.creafund_api.services;

public interface ActiveDesactiveService<T,ID> {
    T ActiveDesactive(T entity, ID id);
}
