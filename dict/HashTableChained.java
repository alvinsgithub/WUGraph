/* HashTableChained.java */

package dict; 
import list.*; 

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  protected DList[] table; 
  protected int size, entries; 


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    int buckets = (int) (sizeEstimate / .75); 
    table = new DList[buckets];
    size = buckets;
    entries = 0;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
    entries = 0;
    size = 101;
    table = new DList[size];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    int hashVal = ((127 * code + 994013) % 16908799) % size;
    if (hashVal < 0) { 
      hashVal += size;
    }
    return hashVal; 
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    Entry data = new Entry(); 
    data.key = key; 
    data.value = value; 
    int hashCode = compFunction(key.hashCode());
    if (table[hashCode] == null) {
    	table[hashCode] = new DList();
    }
    table[hashCode].insertFront(data); 
    entries++;
    return data;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    int hashCode = compFunction(key.hashCode());
    if (table[hashCode] == null) {
    	return null;
    }
    if (table[hashCode].length() > 0) {
      try { 
        ListNode n = table[hashCode].front(); 
        while(n.isValidNode()) { 
        	Entry found = (Entry) n.item();
        	if(found != null && key.equals(found.key)) {
        		return found;
        	}
        n = n.next();
        }
      } catch (InvalidNodeException e) {
      }
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    int hashCode = compFunction(key.hashCode()); 
    if (table[hashCode] == null) {
    	return null;
    }
    ListNode node = table[hashCode].front();
    try { 
    	while (!(((Entry) node.item()).key).equals(key)) {
    		node = node.next();
    	}
        Entry data = (Entry) node.item(); 
        node.remove(); 
        entries--; 
        return data; 
      } catch (InvalidNodeException e) { 
        System.out.println(e); 
      }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    size = 0; 
    for (int i = 0; i < size; i++) { 
      table[i] = new DList(); 
    }
  }

  public void hashstats() { 
    for (int i = 0; i < size; i++) { 
      int l = table[i].length(); 
      System.out.print("Bucket[" + i + "]: "); 
      for (int j = 0; j < l; j++) {
        System.out.print("*"); 
      }
      System.out.print("\n"); 
    }
  }


}
