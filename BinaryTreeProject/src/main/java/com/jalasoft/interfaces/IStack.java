package com.jalasoft.interfaces;

public interface IStack<T> {
	
    void push(final T elem);
    
    void pop();

    T peek();

    Boolean isEmpty();

    Integer size();

    IStack<T> deepCopy();
}