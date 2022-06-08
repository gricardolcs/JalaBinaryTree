package com.jalasoft.main;

import com.jalasoft.integration.Verification;
import com.jalasoft.interfaces.DeepCloneable;
import com.jalasoft.interfaces.IStack;
import java.io.Serializable;
import java.util.Objects;

public class LinkedStack<T> implements IStack<T>, Serializable {

    private LinkedNode<T> top;
    private Integer size;

    public LinkedStack() {
        this.top = null;
        this.size = 0;
        
        Verification.ensure(isEmpty());
        Verification.invariant(checkInvariant());
    }

    @Override
    public void push(final T elem) {
        Verification.invariant(checkInvariant());
        Verification.require(elem != null);
		int oldSize = size();
		
        LinkedNode<T> newNode = new LinkedNode<T>(elem);
        newNode.setNext(this.top);
        this.top = newNode;
        this.size++;

        Verification.ensure((peek() == elem) && !isEmpty() && (size() == oldSize + 1));
        Verification.invariant(checkInvariant());
    }

    @Override
    public void pop() {
        Verification.invariant(checkInvariant());
        Verification.require(!isEmpty());
		int oldSize = size();
		
        this.top = this.top.getNext();
        this.size--;

        Verification.ensure(size() == oldSize - 1);
        Verification.invariant(checkInvariant());
    }

    @Override
    public T peek() {
        Verification.invariant(checkInvariant());
        Verification.require(!isEmpty());
    	int oldSize = size();
    	
        T elem = this.top.getElem();

        Verification.ensure(size() == oldSize);
        Verification.invariant(checkInvariant());
        return elem;
    }

    @Override
    public Boolean isEmpty() {
        Verification.invariant(checkInvariant());
        Boolean condition = this.size == 0;

        Verification.ensure(condition == (this.size == 0));
        Verification.invariant(checkInvariant());
        return condition;
    }

    @Override
    public Integer size() {
        Verification.invariant(checkInvariant());
        Integer size = this.size;

        Verification.ensure(this.size >= 0);
        Verification.invariant(checkInvariant());
        return size;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        LinkedNode<T> temp = this.top;
        while (temp != null) {
            string.append(temp.getElem()).append(", ");
            temp = temp.getNext();
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        LinkedStack<T> that = (LinkedStack<T>) otherObject;

        if (!Objects.equals(this.size, that.size)) return false;

        LinkedNode<T> tempThis = this.top;
        LinkedNode<T> tempThat = that.top;
        while (tempThis != null) {
            if (!Objects.equals(tempThis.getElem(), tempThat.getElem()))
                return false;
            tempThis = tempThis.getNext();
            tempThat = tempThat.getNext();
        }
        return true;
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size + 1];
        array[0] = this.size;

        LinkedNode<T> temp = this.top;
        Integer i = 1;
        while (temp != null && i < array.length) {
            array[i] = temp.getElem();
            i++;
            temp = temp.getNext();
        }

        return Objects.hash(array);
    }

    @Override
    public IStack<T> deepCopy() {
        Verification.invariant(checkInvariant());
    	IStack<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new LinkedStack<>();
        }
        Verification.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Verification.invariant(checkInvariant());
        return deepCopy;
    }
    
    private boolean checkInvariant() {
        if (this.size < 0) {
            return false;
        }
        if (this.size == 0) {
            if (this.top != null) return false;
        }
        else if (this.size == 1) {
            if (this.top == null)      		return false;
            if (this.top.getNext() != null) return false;
        }
        else {
            if (this.top == null)      		return false;
            if (this.top.getNext() == null) return false;
        }

        int numberOfNodes = 0;
        for (LinkedNode<T> x = this.top; x != null && numberOfNodes <= this.size; x = x.getNext()) {
            numberOfNodes++;
        }
        if (numberOfNodes != this.size) return false;

        return true;
    }
}
