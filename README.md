# Example - Binary Tree Project
On this project are crated a complex data structure.

##Project technology
#### java 17
#### maven

Author: Ricardo Gonzalez


##IBinaryTree
First I define the below Interface: IBinaryTree, where are define many method:

#### .getRoot() 
        Return the tree root
#### .getLeftChild()
        return the left node son
#### .getRightChild()
        return the right node son
#### .isEmpty()
        Verify if the tree is empty
#### .makeEmpty()
        clear the tree
#### .isBalanced()
        is the tree is ok balanced return TRUE else return false
#### .height()
        check the height of the tree
#### .size()
        check how many node have the tree
#### .leaves()
        check how many leafs have the tree
#### .preorder()
        return a list of the tree node 
#### .inorder()
        return a list of the tree node in order
#### .postorder()
        return a lit of the tree node on post order
#### .levelorder()
        return a list of the tree node checking each tree level


##BinaryNode
This class is a linked structure that is build into the binary node

##BinaryTree
This class implements the binary tree.

##Project description
On this project I did a recursive structure and, that contain three elements. One element data 
saevd into a left binary tree and one element saved into a right binary tree.

On special I describe the cases that run on deep into preorder, inorder and postrorder.

We know that the recursive method is better because is more sort and more clearly but, if the 
the recursive deep is elevated, maybe is necesary performance the recursive way or use directly 
the interactive method.


## Interfaces.
I defined few interface that I describe before:

###DeepClonable:
the objective if this interface is do a object clone.

###Ilist:
This interface extend from Iterable and implement few methods.

###IQueque:
This interfaces have method that do a queque task

###IStack:
how the name describe, that do a stack work


##Verificaction class

This clase manage the exceptions error into the application.


##Dummy Binary Tree.
This small class show how to merge to binary tree. The objective of this run a code without the 
complex structure that I did in the other structure. For run it just giv it from the class ###Run

##Run and test.
To run those code just type mvn clean install and, it will run the test code.

