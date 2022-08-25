/*
Anderson Lu
Cse 143 AN with May Wang
Homework 8, Huffman
This program uses the Huffman code to compress files and
store them in a compressed way. Compressing the data of the
files by a priority queue to prioritizing the most frequent
characters in the files to the least used characters.
*/
import java.util.*;
import java.io.*;

public class HuffmanTree {
   //Creates a tree to hold the variables.
   private HuffmanNode root;
   
   /*
   Takes in an Int[] "count" as a parameter.
   This method creates a tree to hold the frequencies and
   characters of the array by a priority queue.
   */
   public HuffmanTree(int[] count) {
      //Creates a priority queue to sort and store the nodes.
      Queue<HuffmanNode> pQueue = new PriorityQueue<>();
      //Goes through the array and checks if the ascii value
      //has a frequency of 1 or more.
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            //Converts the ascii value to a character.
            char letter = (char) (i);
            //Creates a node to store the character and frequency.
            HuffmanNode nNode = new HuffmanNode(letter, count[i]);
            //Adds node to the queue.
            pQueue.add(nNode);
         }
      }
      //Adds the eof character to the queue.
      int eof = 256;
      pQueue.add(new HuffmanNode((char) eof, 1));
      //Sends priority queue to create a single tree
      //that stores all the values.
      root = createTree(pQueue);
   }
   
   /*
   Takes in a Queue<HuffmanNode> "pQueue" as a parameter.
   Adds all the nodes in the priority queue to a single node,
   to create a single tree to hold all the values.
   */
   private HuffmanNode createTree(Queue<HuffmanNode> pQueue) {
      //Goes through the priority queue and
      //Combines the first two nodes together.
      while (pQueue.size() > 1) {
         HuffmanNode left = pQueue.remove();
         HuffmanNode right = pQueue.remove();
         int totFrequency = left.frequency + right.frequency;
         HuffmanNode leaf = new HuffmanNode(totFrequency, left, right);
         //Adds combined node to priority queue.
         pQueue.add(leaf);
      }
      //Returns the last node in the priority queue as the
      //overall tree for the values.
      return pQueue.remove();
   }
   
   /*
   Takes in a PrintStream "output" as a parameter.
   Prints out the tree to a file in the tree format.
   */
   public void write(PrintStream output) {
      //Sends the PrintStream and root to print out the tree.
      String path = "";
      recurWrite(output, root, path);
   }
   
   /*
   Takes in PrintStream "output", HuffmanNode "cur" and String "path"
   as parameters. Recurs through the tree to print the path and the
   value at the end of the path.
   */
   private void recurWrite(PrintStream output,
   HuffmanNode cur, String path) {
      if (cur != null) {
         //Checks if the node is a leaf node.
         if (cur.isLeafNode()) {
            //Adds the character and path to that node to a PrintStream.
            output.println((int) cur.data);
            output.println(path);
         } else {
            //Recurs through the tree for the left and right path.
            recurWrite(output, cur.left, path + "0");
            recurWrite(output, cur.right, path + "1");
         }
      }
   }
   
   /*
   Takes in a Scanner "input" as a parameter. Recreates
   a tree from the file given.
   */
   public HuffmanTree(Scanner input) {
      root = new HuffmanNode(0);
      //Goes through all the data in the file.
      while (input.hasNextLine()) {
         //Creates the character and pathway from the file.
         char character = (char) Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         root = recreateTree(character, code, root);
      }
   }
   
   /*
   Takes in Character "character", String "code" and HuffmanNode "cur"
   as parameters. And recreates a tree from the file given. Makes
   nodes and returns them to create a whole tree.
   */
   private HuffmanNode recreateTree(char character,
   String code, HuffmanNode cur) {
      //Returns a node with the character if no pathway.
      if (cur == null || code.length() == 0) {
         return new HuffmanNode(character, 0);
      } else {
         //Creates a variable to keep track of pathway.
         char path = code.charAt(0);
         //Traverses down the left side of tree.
         if (path == '0') {
            //Creates left node if left side is empty.
            if (cur.left == null) {
               cur.left = new HuffmanNode(0);
            }
            //Recurs through the left side, following the pathway
            cur.left = recreateTree(character,
            code.substring(1), cur.left);
            //Traverses down the right side of tree.
         } else if (path == '1') {
            //Creates right node if right side is empty.
            if (cur.right == null) {
               cur.right = new HuffmanNode(0);
            }
            //Recurs through the right side, following the pathway
            cur.right = recreateTree(character,
            code.substring(1), cur.right);
         }
         return cur;
      }
   }
   
   /*
   Takes in BitInputStream "input", PrintStream "output" and int "eof"
   as parameters. Reads the bits from the "input" and prints the
   characters to the "output". Stops printing and reading the
   characters when a character equal to "eof" is encountered.
   */
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int value = root.data;
      while (value != eof) {
         value = decodeHelper(input, output, root, value);
      }
   }
   
   /*
   Takes in BitInputStream "input", PrintStream "output", HuffmanNode
   "cur", and Integer "value" as parameters. This method recurs through
   the tree until a leaf node is found. Setting the value to the
   leaf node value and writing the value to the PrintStream. Returns the
   value of the leaf node and the while loop checks if value is the "eof".
   */
   private int decodeHelper(BitInputStream input, PrintStream output,
   HuffmanNode cur, int value) {
      //Checks for leaf node.
      if (cur.isLeafNode()) {
         //Sets value to the leaf node value.
         //Writes vale to the PrintStream.
         output.write(cur.data);
         value = cur.data;
         return value;
      }
      //Checks the pathway of the tree form the bits.
      int code = input.readBit();
      //Right side tree.
      if (code == 1) {
         cur = cur.right;
         //Left side tree.
      } else if (code == 0) {
         cur = cur.left;
      }
      return decodeHelper(input, output, cur, value);
   }
}
