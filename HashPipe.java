import edu.princeton.cs.algs4.*;

public class HashPipe {
  private int size;
  private int maxLevels = 32;
  private Pipe headPipe;

  public HashPipe() {
    size = 0;
    headPipe = new Pipe(maxLevels, "", 0);
    StdOut.println("hashpipe was created");
  }

  // return the number of elements
  public int size() {
    StdOut.printf("\nSize is: %s", size);
    return size;
  }

  // put key-value pair into the table
  public void put(String key, Integer val) {
    Pipe[] updateVector = floorPipes(key); // Could be called with a height?
    // if key already exists - update the value of that entry //Use floor method?
    if (updateVector[0].rightReferences[0] != null && updateVector[0].rightReferences[0].key == key) {
      StdOut.printf("\nKey %s was equal to present key: %s", key, updateVector[0].rightReferences[0].key);
      updateVector[0].rightReferences[0].value = val;
      return;
    }

    // otherwise search and get an update vector

    for (int h = maxLevels-1; h>=0; h--) {
      StdOut.printf("\nUpdatevector at level %d has key %s and value %d", h, updateVector[h].key, updateVector[h].value);
    }

    //Calculate height of pipe
    int temp = key.hashCode();
    int height = Integer.numberOfTrailingZeros(temp) + 1;
    StdOut.printf("\nInserting key %s with height %d", key, height);

    //Create new pipe and insert new pipe by updating references
    Pipe newPipe = new Pipe(height, key, val);
    for (int j = height-1; j>=0; j--) {
      newPipe.rightReferences[j] = updateVector[j].rightReferences[j];
      updateVector[j].rightReferences[j] = newPipe;
      StdOut.printf("\nThe pipe with key: %s has a reference to: %s at level %d", updateVector[j].key, updateVector[j].rightReferences[j].key, j);
    }

    //Update size variable
    size++;
  }

  public String control(String key, int h) {
    Pipe[] tempArray = floorPipes(key);

    //Handle cases where h is bigger than heightOfPipe
    Pipe controlPipe = tempArray[0].rightReferences[0];
    if (h >= controlPipe.height ||controlPipe.rightReferences[h] == null) {
      StdOut.printf("\naControlling key: %s at height: %d, result is: %s", key, h, null);
      return null;
    } else {
      StdOut.printf("\nbControlling key: %s at height: %d, result is: %s", key, h, controlPipe.rightReferences[h].key);
      return controlPipe.rightReferences[h].key;
    }

    // //Handle cases where h is bigger than heightOfPipe
    // if (tempArray[h].rightReferences[h] == null) {
    //   return null;
    // } else if (tempArray[h].rightReferences[h].rightReferences[h] != null) {
    //   StdOut.printf("\naControlling key: %s at height: %d, result is: %s", key, h, tempArray[h].rightReferences[h].rightReferences[h].key);
    //   return tempArray[h].rightReferences[h].rightReferences[h].key;
    // } else {
    //   StdOut.printf("\nbControlling key: %s at height: %d, result is: %s", key, h, "null");
    //   return null;
    // }
  }

  // is only called if we know that the key is not already in the list or?
  // Returns an array with references to all the pipes smaller than some key at all levels from maxLevels to 0
  private Pipe[] floorPipes(String key) {
    Pipe[] updatePipes = new Pipe[maxLevels];

    Pipe current = headPipe;
    // Move down through the levels
    for (int i = maxLevels-1; i>=0; i--) {
      // If pointer is not null and the right key is smaller than searchKey, move right
      while (current.rightReferences[i] != null && current.rightReferences[i].key.compareTo(key) < 0) {
        //move right until a null ref is encountered or the key is equal to or bigger than searchKey
        //StdOut.println(current.rightReferences[i].key);
        current = current.rightReferences[i];
      }
      //StdOut.printf("\nyes, i: %d", i);
      updatePipes[i] = current;
    }

    return updatePipes; //Use updatePipes[0] to find insertion spot?
  }

  // value associated with key
  public Integer get(String key) {
    Pipe[] temp = floorPipes(key);
    StdOut.printf("\nGetting key: %s value: %d", key, temp[0].rightReferences[0].value);
    return temp[0].rightReferences[0].value;
  }

  // largest key less than or equal to key
  public String floor(String key) {
    Pipe[] temp = floorPipes(key);
    if (temp[0].rightReferences[0] != null && temp[0].rightReferences[0].key == key) {
      StdOut.printf("\nFloor method - Key %s was equal to present key: %s", key, temp[0].rightReferences[0].key);
      return temp[0].rightReferences[0].key;
    } else {
      StdOut.printf("\nFloor method - Key %s is not present so biggest key smaller is: %s", key, temp[0].key);
      return temp[0].key;
    }
  }

  private class Pipe {
    String key;
    int value;
    Pipe[] rightReferences;
    int height;

    public Pipe(int heightOfPipe, String key, int value) {
      this.key = key;
      this.value = value;
      this.height = heightOfPipe;
      rightReferences = new Pipe[heightOfPipe];
    }
  }

  public static void main(String[] args) {
    HashPipe hp = new HashPipe();
    hp.put("A", 4);
    hp.put("B", 10);
    hp.put("C", 11);
    hp.put("D", 21);
    hp.control("A",0);
    hp.control("A",1);
    hp.control("B",0);
    hp.control("B",1);
    hp.control("C",0);
    hp.control("C",1);
    hp.control("C",2);
    hp.control("D", 0);
    //StdOut.printf("\nSize of hashpipe: %d", hp.size());
    //hp.floor("C");
    //hp.get("C");
    //hp.put("C", 17);
    //hp.get("C");
    //hp.size();
    //hp.floor("C");
    //hp.floor("D");
    //hp.floor("E");

  }

}
