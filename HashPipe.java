import edu.princeton.cs.algs4.*;

public class HashPipe {
  public static void main(String[] args) {
    String input = StdIn.readString();
    int hashedInput = input.hashCode();
    int height = Integer.numberOfTrailingZeros(hashedInput) + 1;
    StdOut.println(height);
    StdOut.println("Hello");
  }

  private int size;
  private int maxLevels = 32;


  // create an empty symbol table
  public HashPipe() {
    size = 0;


  }

  // return the number of elements
  public int size() {
    return size;
  }

  // put key-value pair into the table
  public void put(String key, Integer val) {
    // Find location and store reference so references here can be updated


    //Calculate height of pipe
    int temp = key.hashCode() + 1;
    int height = Integer.numberOfTrailingZeros(temp);

    //Insert new pipe


    //Update references

    size++;
  }

  // value associated with key
  public Integer get(String key) {
    Integer test = 0;
    return test;
  }

  // largest key less than or equal to key
  public String floor(String key) {
    String test = "";
    return test;
  }

  private class Node {
    Node right;
    Node down;
    String key;
    int value;

    public Node(String key, int value) {
      this.key = key;
      this.value = value;
    }
  }
  private class Pipe {
    //This is a linked list
    public Pipe(int heightOfPipe, String key, int value) {
      Node first = new Node(key, value);
      for (int i=0; i<heightOfPipe; i++) {
        Node newFirstNode;
      }
    }

  }


  private class Key {

  }

  private Node floorNode(Key key) {
    Node test = new Node("a", 13);
    return test;

  }

  // public Iterable<String> keys(String lo, String hi) {
  //
  // }


}
