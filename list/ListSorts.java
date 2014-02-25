package list;
/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 100000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
	  LinkedQueue result = new LinkedQueue();
	  while (!q.isEmpty()) {
		  try {
			  Object currItem = q.dequeue();
			  LinkedQueue temp = new LinkedQueue();
			  temp.enqueue(currItem);
			  result.enqueue(temp);
		  } catch (QueueEmptyException e) {
			  System.out.println("I am throwing an empty queue exception.");
		  }
	  }
	  return result;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
	  LinkedQueue result = new LinkedQueue();
	  try {
		  Comparable item1 = (Comparable) q1.dequeue();
		  Comparable item2 = (Comparable) q2.dequeue();
		  while ((!q1.isEmpty()) && (!q2.isEmpty())) {
			  if (item1.compareTo(item2) <= 0) {
				  result.enqueue(item1);
				  item1 = (Comparable) q1.dequeue();
			  }
			  else {
				  result.enqueue(item2);
				  item2 = (Comparable) q2.dequeue();
			  }
		  }
		  if (q1.isEmpty() && q2.isEmpty()) {
			  if (item1.compareTo(item2) <= 0) {
				  result.enqueue(item1);
				  result.enqueue(item2);
			  }
			  else {
				  result.enqueue(item2);
				  result.enqueue(item1);
			  }
		  }
		  else if (q1.isEmpty()) {//if there's still item1 to be placed & q2.size >= 1
			  while (!q2.isEmpty()) {
				  if (item1.compareTo(item2) <= 0) {
					  result.enqueue(item1);
					  result.enqueue(item2);
					  result.append(q2);
					  return result;
				  }
				  else {
					  result.enqueue(item2);
					  item2 = (Comparable) q2.dequeue();
				  }
			  }
			  if (item1.compareTo(item2) <= 0) {
				  result.enqueue(item1);
				  result.enqueue(item2);
			  }
			  else {
				  result.enqueue(item2);
				  result.enqueue(item1);
			  }
		  }
		  else {
			  while (!q1.isEmpty()) {
				  if (item1.compareTo(item2) >= 0) {
					  result.enqueue(item2);
					  result.enqueue(item1);
					  result.append(q1);
					  return result;
				  }
				  else {
					  result.enqueue(item1);
					  item1 = (Comparable) q1.dequeue();
				  }
			  }
			  if (item1.compareTo(item2) <= 0) {
				  result.enqueue(item1);
				  result.enqueue(item2);
			  }
			  else {
				  result.enqueue(item2);
				  result.enqueue(item1);
			  }
		  }
		  
	  } catch (QueueEmptyException e) {
		  System.out.println("I am throwing exception from mergeSrotedQueues.");
	  }
	  return result;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
	  try {
		  Comparable curr = (Comparable) qIn.dequeue();
		  while (!qIn.isEmpty()) {
			  if (curr.compareTo(pivot) < 0) {
				  qSmall.enqueue(curr);
			  }
			  else if (curr.compareTo(pivot) == 0) {
				  qEquals.enqueue(curr);
			  }
			  else {
				  qLarge.enqueue(curr);
			  }
			  curr = (Comparable) qIn.dequeue();
		  }
		  if (curr.compareTo(pivot) < 0) {
			  qSmall.enqueue(curr);
		  }
		  else if (curr.compareTo(pivot) == 0) {
			  qEquals.enqueue(curr);
		  }
		  else {
			  qLarge.enqueue(curr);
		  }
	  } catch (QueueEmptyException e) {
		  System.out.println("exception from partition.");
	  }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
	  if (q.isEmpty()) {
		  return;
	  }
	  LinkedQueue queues = makeQueueOfQueues(q);
	  try {
		  while (queues.size() > 1) {
			  LinkedQueue queue1 = (LinkedQueue) queues.dequeue();
			  LinkedQueue queue2 = (LinkedQueue) queues.dequeue();
			  queues.enqueue(mergeSortedQueues(queue1, queue2));
		  }
		  q.append((LinkedQueue)queues.dequeue());
	  } catch (QueueEmptyException e) {
		  System.out.println("I am throwing exception from mergeSort");
	  }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
	  if (q.size() <= 1) {
		  return;
	  }
	  Comparable pivot = (Comparable) q.nth((int)((Math.random()*q.size()) + 1));
	  LinkedQueue qSmall = new LinkedQueue();
	  LinkedQueue qLarge = new LinkedQueue();
	  LinkedQueue qEquals = new LinkedQueue();
	  partition(q, pivot, qSmall, qEquals, qLarge);
	  quickSort(qSmall);
	  quickSort(qLarge);
	  q.append(qSmall);
	  q.append(qEquals);
	  q.append(qLarge);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }
}
