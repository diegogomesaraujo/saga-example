package com.example.events.converter;

public interface DataConverter<T, E> {

    public T convert(E value);

}
