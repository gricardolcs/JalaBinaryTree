package com.jalasoft.interfaces;

import com.jalasoft.main.BinaryNode;

public interface IBinaryTree<T> {

    BinaryNode<T> getRoot();

    BinaryNode<T> getLeftChild();
    
    BinaryNode<T> getRightChild();

    Boolean isEmpty();

    void makeEmpty();

    Boolean isBalanced();

    Integer height();
    
    Integer size();

    Integer leaves();

    IList<T> preorder();

    IList<T> inorder();

    IList<T> postorder();

    IList<T> levelorder();

    IList<T> mergetree();

    IBinaryTree<T> deepCopy();
}