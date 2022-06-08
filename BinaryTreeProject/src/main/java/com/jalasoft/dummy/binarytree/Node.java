package com.jalasoft.dummy.binarytree;

public class Node
{
    int data;
    Node left, right;

    public Node(int data, Node left, Node Right){
        this.data = data;
        this.left = left;
        this.right = right;
    }
    static Node newNode(Integer data){
        return new Node(data, null, null);
    }

    static void inorder(Node node) {
        if (node == null)
            return;
        inorder(node.left);
        System.out.printf("%d ", node.data);
        inorder(node.right);
    }

    static Node MergedTrees(Node t1, Node t2){
        if(t1 == null)
            return t2;
        if(t2 == null)
            return t1;
        t1.data = t2.data;
        t1.left = MergedTrees(t1.left, t2.left);
        t1.right = MergedTrees(t1.right, t2.right);
        return t1;
    }

    public static void main(String[] args){
        Node root1 = newNode(1);
        root1.left = newNode(2);
        root1.right = newNode(3);
        root1.left.left = newNode(4);
        root1.left.right = newNode(5);
        root1.right.right = newNode(6);

        Node root2 = newNode(4);
        root2.left = newNode(1);
        root2.right = newNode(7);
        root2.left.left = newNode(3);
        root2.right.left = newNode(2);
        root2.right.right = newNode(6);

        Node root3 = MergedTrees(root1, root2);;
        System.out.printf("The merged binry tree is:\n");
        inorder(root3);

    }
}
