import edu.princeton.cs.algs4.*;

public class HashPipe {
  private int size;
  private final int maxLevels = 32;
  private Pipe rootPipe;

  public HashPipe() {
    size = 0;
    rootPipe = new Pipe(maxLevels, null, 0);
    StdOut.println("hashpipe was created");
  }

  // return the number of elements
  public int size() {
    StdOut.printf("\nSize is: %s", size);
    return size;
  }

  // put key-value pair into the table
  public void put(String key, Integer value) {
    Pipe[] updateVector = getFloorPipes(key);

    // if key already exists - update the value of that entry
    Pipe duplicatePipe = updateVector[0].rightReferences[0];    //Potential duplicate pipe
    if (duplicatePipe != null && duplicatePipe.key == key) {
      StdOut.printf("\nKey %s was equal to present key: %s", key, duplicatePipe.key);
      duplicatePipe.value = value;
      return;
    }

    //Calculate height of pipe
    int hashValue = key.hashCode();
    int pipeHeight = Integer.numberOfTrailingZeros(hashValue) + 1;
    StdOut.printf("\nInserting key %s with height %d", key, pipeHeight);

    //Create new pipe and insert it by updating references in the updateVector
    Pipe newPipe = new Pipe(pipeHeight, key, value);
    for (int j = pipeHeight-1; j>=0; j--) {
      newPipe.rightReferences[j] = updateVector[j].rightReferences[j];      //Set new pipes right references to floorPipes right references
      updateVector[j].rightReferences[j] = newPipe;                         //Set floorPipes right references to point to the new pipe
      StdOut.printf("\nThe pipe with key: %s has a reference to: %s at level %d", updateVector[j].key, updateVector[j].rightReferences[j].key, j);
    }

    //Update size variable
    size++;
  }

  public String control(String key, int h) {
    Pipe[] floorPipes = getFloorPipes(key);

    //Handle cases where h is bigger than heightOfPipe
    Pipe controlPipe = floorPipes[0].rightReferences[0];
    if (h >= controlPipe.height ||controlPipe.rightReferences[h] == null) {
      StdOut.printf("\naControlling key: %s at height: %d, result is: %s", key, h, null);
      return null;
    } else {
      StdOut.printf("\nbControlling key: %s at height: %d, result is: %s", key, h, controlPipe.rightReferences[h].key);
      return controlPipe.rightReferences[h].key;
    }
  }

  // Returns an array with references to all the pipes with keys smaller 'key' at all levels
  private Pipe[] getFloorPipes(String key) {
    Pipe[] floorPipes = new Pipe[maxLevels];

    Pipe current = rootPipe;
    // Move down through the levels
    for (int i = maxLevels-1; i>=0; i--) {
      // If pointer is not null and the right key is smaller than searchKey, move right
      while (current.rightReferences[i] != null && current.rightReferences[i].key.compareTo(key) < 0) {
        //move right until a null ref is encountered or the key is equal to or bigger than searchKey
        current = current.rightReferences[i];
      }
      floorPipes[i] = current;
    }

    return floorPipes;
  }

  // gets value associated with key
  public Integer get(String key) {
    Pipe[] floorPipes = getFloorPipes(key);
    if (floorPipes[0].rightReferences[0] != null && floorPipes[0].rightReferences[0].key == key) {
      StdOut.printf("\nGetting key: %s value: %d", key, floorPipes[0].rightReferences[0].value);
      return floorPipes[0].rightReferences[0].value;
    } else {
      StdOut.printf("\nKey was not found");
      return null;
    }
  }

  private boolean contains(String key) {
    Pipe[] floorPipes = getFloorPipes(key);
    if (floorPipes[0].rightReferences[0] != null && floorPipes[0].rightReferences[0].key == key) {
      return true;
    }
    return false; 
  }

  // largest key less than or equal to key
  public String floor(String key) {
    Pipe[] floorPipes = getFloorPipes(key);
    if (floorPipes[0].rightReferences[0] != null && floorPipes[0].rightReferences[0].key == key) {
      StdOut.printf("\nFloor method - Key %s was equal to present key: %s", key, floorPipes[0].rightReferences[0].key);
      return floorPipes[0].rightReferences[0].key;
    } else {
      StdOut.printf("\nFloor method - Key %s is not present so biggest key smaller is: %s", key, floorPipes[0].key);
      return floorPipes[0].key;
    }
  }

  private class Pipe {
    String key;
    int value;
    int height;
    Pipe[] rightReferences;

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
    StdOut.printf("\nSize of hashpipe: %d", hp.size());
    hp.floor("C");
    hp.get("C");
    hp.put("C", 17);
    hp.get("C");
    hp.size();
    hp.floor("C");
    hp.floor("D");
    hp.floor("E");
    hp.get("E");

  }

}
