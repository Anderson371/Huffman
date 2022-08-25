/*
Anderson Lu
Cse 143 AN with May Wang
Homework 8, Huffman
This program creates and defines the tree
and the nodes in the tree. Defining and adding
values to the nodes in the tree.
*/
import java.util.*;
import java.io.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   //Creates the nodes for the left and right branches.
   public HuffmanNode left;
   public HuffmanNode right;
   //Creates the nodes for the frequency and characters.
   public char data;
   public int frequency;
   
   /*
   Takes in Character "data" and Integer "frequency" as
   parameters. Sets the node of the tree to the character and
   frequency.
   */
   public HuffmanNode(char data, int frequency) {
      this.data = data;
      this.frequency = frequency;
   }
   
   /*
   Takes in an Integer "frequency" as a parameter.
   Sets the node of the tree to the frequency.
   */
   public HuffmanNode(int frequency) {
      this.frequency = frequency;
   }
   
   /*
   Takes in Integer "frequency", HuffmanNode "left" and HuffmanNode "right"
   as parameters. Sets both nodes to either right or left branch, and
   the frequency of the node.
   */
   public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   /*
   Takes in a HuffmanNode "other" as a parameter. Compares the
   values of the current HuffmanNode and the other HuffmanNode
   requested. Returns the differences between the two nodes.
   */
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency;
   }
   
   /*
   Checks if the node in the tree is a leaf node.
   The node in the tree is a leaf node if there are
   no branches after the node. Returns true if there
   are no branches after node and false otherwise.
   */
   public boolean isLeafNode() {
      return left == null && right == null;
   }
}