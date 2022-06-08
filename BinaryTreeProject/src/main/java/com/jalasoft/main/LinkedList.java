package com.jalasoft.main;

import com.jalasoft.integration.Verification;
import com.jalasoft.interfaces.DeepCloneable;
import com.jalasoft.interfaces.IList;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements IList<T>, Serializable {

    private Integer size;
    private Integer modCount;
    private DoublyLinkedNode<T> head;
    private DoublyLinkedNode<T> tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.modCount = 0;
        
        Verification.ensure(isEmpty());
        Verification.invariant(checkInvariant());
    }

    @Override
    public void addFirst(final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(elem != null);
    	int oldSize = size();
    	
        this.head = new DoublyLinkedNode<T>(elem, this.head, null);
        if (this.tail == null)
            this.tail = this.head;
        this.size++;
        this.modCount++;
        
        Verification.ensure((getFirst() == elem) && (size() == (oldSize + 1)));
        Verification.invariant(checkInvariant());
    }

    @Override
    public void addLast(final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(elem != null);
    	int oldSize = size();
    	
        this.tail = new DoublyLinkedNode<T>(elem, null, this.tail);
        if (this.head == null)
            this.head = this.tail;
        this.size++;
        this.modCount++;
        
        Verification.ensure((getLast() == elem) && (size() == (oldSize + 1)));
        Verification.invariant(checkInvariant());
    }

    @Override
    public T getFirst() {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty());
    	int oldSize = size();
    	
    	T elem = this.head.getElem();
    	
    	Verification.ensure((elem == get(0)) && (size() == oldSize));
    	Verification.invariant(checkInvariant());
        return elem;
    }

    @Override
    public T getLast() {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty());
    	int oldSize = size();
    	
    	T elem = this.tail.getElem();
    	
    	Verification.ensure((elem == get(size() - 1)) && (size() == oldSize));
    	Verification.invariant(checkInvariant());
        return elem;
    }

    @Override
    public void removeFirst() {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty());
    	int oldSize = size();
    	
        DoublyLinkedNode<T> temp = this.head;
        this.head = this.head.getNext();
        if (this.head != null) {
            this.head.setPrevious(null);
        } else {
            this.tail = null;
        }
        temp.setNext(null);
        this.size--;
        this.modCount++;
        
        Verification.ensure(size() == oldSize - 1);
    	Verification.invariant(checkInvariant());
    }

    @Override
    public void removeLast() {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty());
    	int oldSize = size();
    	
        DoublyLinkedNode<T> temp = this.tail;
        this.tail = this.tail.getPrevious();
        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.setNext(null);
        }
        this.size--;
        this.modCount++;
        
        Verification.ensure(size() == oldSize - 1);
    	Verification.invariant(checkInvariant());
    }

    @Override
    public Boolean contains(final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(elem != null);
    	int oldSize = size();
    	
        DoublyLinkedNode<T> finger = this.head;
        while ((finger != null) && (!finger.getElem().equals(elem))) {
            finger = finger.getNext();
        }
        boolean condition = finger != null;
        
        Verification.ensure(size() == oldSize);
    	Verification.invariant(checkInvariant());
        return condition;
    }

    @Override
    public void remove(final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(elem != null);
    	int oldSize = size();
    	
    	DoublyLinkedNode<T> finger = this.head;
        while (finger != null && !finger.getElem().equals(elem)) {
            finger = finger.getNext();
        }
        if (finger != null) {
            if (finger.getPrevious() != null) {
                finger.getPrevious().setNext(finger.getNext());
            } else {
                this.head = finger.getNext();
            }
            if (finger.getNext() != null) {
                finger.getNext().setPrevious(finger.getPrevious());
            } else {
                this.tail = finger.getPrevious();
            }
            this.size--;
            this.modCount++;
        }
        
        Verification.ensure(!contains(elem) || size() == (oldSize - 1));
    	Verification.invariant(checkInvariant());
    }

    @Override
    public void clear() {
    	Verification.invariant(checkInvariant());
    	
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.modCount++;
        
        Verification.ensure(isEmpty());
    	Verification.invariant(checkInvariant());
    }

    @Override
    public T get(Integer index) {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty() && (index >= 0) && (index <= (size() - 1)));
    	int oldSize = size();
    	
        DoublyLinkedNode<T> finger = this.head;
        while (index > 0) {
            finger = finger.getNext();
            index--;
        }
        T elem = finger.getElem();
        
        Verification.ensure((elem != null) && (size() == oldSize));
    	Verification.invariant(checkInvariant());
        return elem;
    }

    @Override
    public void set(Integer index, final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty() && (elem != null) && (index >= 0) && (index <= (size() - 1)));
    	int oldSize = size();
    	
        DoublyLinkedNode<T> finger = this.head;
        while (index > 0) {
            finger = finger.getNext();
            index--;
        }
        finger.setElem(elem);
        
        Verification.ensure(size() == oldSize);
    	Verification.invariant(checkInvariant());
    }

    @Override
    public void add(Integer index, final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require((elem != null) && (index >= 0) && (index <= size()));
    	int oldSize = size();
    	
        if (index == 0)
            this.addFirst(elem);
        else if (index == this.size())
            this.addLast(elem);
        else {
            DoublyLinkedNode<T> before = null;
            DoublyLinkedNode<T> after = this.head;

            while (index > 0) {
                before = after;
                after = after.getNext();
                index--;
            }

            DoublyLinkedNode<T> current =
                    new DoublyLinkedNode<T>(elem, after, before);
            this.size++;
            this.modCount++;

            before.setNext(current);
            after.setPrevious(current);
        }
        
        Verification.ensure((size() == (oldSize + 1)) && (contains(elem) == true));
    	Verification.invariant(checkInvariant());
    }

    @Override
    public void remove(Integer index) {
    	Verification.invariant(checkInvariant());
    	Verification.require(!isEmpty() && (index >= 0) && (index <= (size() - 1)));
    	int oldSize = size();

        if (index == 0)
            this.removeFirst();
        else if (index == this.size() - 1)
            this.removeLast();
        else {
            DoublyLinkedNode<T> previous = null;
            DoublyLinkedNode<T> finger = this.head;

            while (index > 0) {
                previous = finger;
                finger = finger.getNext();
                index--;
            }

            previous.setNext(finger.getNext());
            finger.getNext().setPrevious(previous);
            this.size--;
            this.modCount++;
        }
        
        Verification.ensure(size() == (oldSize - 1));
    	Verification.invariant(checkInvariant());
    }

    @Override
    public Integer indexOf(final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(elem != null);
    	int oldSize = size();
    	
        Integer i = 0;
        DoublyLinkedNode<T> finger = head;
        Integer index = 0;

        while (finger != null
                && !finger.getElem().equals(elem)) {
            finger = finger.getNext();
            i++;
        }

        if (finger == null) {
            index = -1;
        } else {
            index = i;
        }
        
        Verification.ensure((size() == oldSize) && ((index == -1) || (contains(elem))));
    	Verification.invariant(checkInvariant());
        return index;
    }

    @Override
    public Integer lastIndexOf(final T elem) {
    	Verification.invariant(checkInvariant());
    	Verification.require(elem != null);
    	int oldSize = size();
    	
        Integer i = this.size() - 1;
        DoublyLinkedNode<T> finger = this.tail;
        Integer index = 0;

        while (finger != null &&
                !finger.getElem().equals(elem)) {
            finger = finger.getPrevious();
            i--;
        }

        if (finger == null) {
            index = -1;
        } else {
            index = i;
        }
        
        Verification.ensure((size() == oldSize) && ((index == -1) || (contains(elem))
        		&& ((index == -1) || (index >= indexOf(elem)))));
    	Verification.invariant(checkInvariant());
        return index;
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
    public IList<T> deepCopy() {
    	Verification.invariant(checkInvariant());
    	IList<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new LinkedList<>();
        }
        Verification.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Verification.invariant(checkInvariant());
        return deepCopy;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        for (T elem: this) {
            string.append(elem).append(", ");
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        LinkedList<T> that = (LinkedList<T>) otherObject;

        if (!Objects.equals(this.size, that.size))
            return false;

        DoublyLinkedNode<T> tempOther = that.head;

        for (T elem: this) {

            if (!Objects.equals(elem, tempOther.getElem()))
                return false;

            tempOther = tempOther.getNext();
        }

        return true;
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size + 1];
        array[0] = this.size;

        Integer i = 1;
        for (T elem: this) {
            array[i] = elem;
            i++;
        }

        return Objects.hash(array);
    }
    
    private boolean checkInvariant() {
        if (this.size < 0) {
            return false;
        }
        if (this.size == 0) {
            if (this.head != null) return false;
            if (this.tail != null) return false;
        }
        else if (this.size == 1) {
            if (this.head == null)     	return false;
            if (this.head != this.tail) return false;
        }
        else {
            if (this.head == null)      		 return false;
            if (this.tail == null) 				 return false;
            if (this.head.getPrevious() != null) return false;
            if (this.tail.getNext() != null) 	 return false;
        }

        int numberOfNodes = 0;
        for (DoublyLinkedNode<T> x = this.head; x != null && numberOfNodes <= this.size; x = x.getNext()) {
            numberOfNodes++;
        }
        if (numberOfNodes != this.size) return false;

        return true;
    }
    
    private class LinkedListIterator implements Iterator<T> {

        private DoublyLinkedNode<T> current;
        private DoublyLinkedNode<T> lastVisited;
        private T lastElementReturned;
        private Integer expectedModCount;
        private Integer index;

        public LinkedListIterator() {
            this.current = LinkedList.this.head;
            this.lastVisited = null;
            this.expectedModCount = LinkedList.this.modCount;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if (this.expectedModCount != LinkedList.this.modCount)
                throw new ConcurrentModificationException();

            return this.index < LinkedList.this.size();
        }

        @Override
        public T next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            this.lastElementReturned = this.current.getElem();
            this.lastVisited = this.current;
            this.current = this.current.getNext();
            this.index++;
            return this.lastElementReturned;
        }

        @Override
        public void remove() {
            if (this.expectedModCount != LinkedList.this.modCount)
                throw new ConcurrentModificationException();
            if (this.lastVisited == null)
                throw new IllegalStateException();

            LinkedList.this.remove(this.lastElementReturned);
            this.lastVisited = null;
            this.index--;
            this.expectedModCount++;
        }
    }
}
