/* Kruskal.java */

import list.*;
import graph.*;
import dict.*;
import set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g) {
	  WUGraph minSpanTree = new WUGraph();
	  Object[] vertices = g.getVertices();
	  HashTableChained table = new HashTableChained();
	  for (int i = 0 ; i < vertices.length ; i++) {
		  minSpanTree.addVertex(vertices[i]);
		  table.insert(vertices[i],  new Integer(i));
	  }
	  LinkedQueue edges = sortEdges(g);
	  DisjointSets sets = new DisjointSets(vertices.length);
	  while (edges.size() > 0) {
		  Edge edge;
		  try {
			  edge = (Edge) edges.dequeue();
			  int v1 = (Integer) table.find(edge.get1()).value();
			  int v2 = (Integer) table.find(edge.get2()).value();
			  if (sets.find(v1) != sets.find(v2)) {
				  minSpanTree.addEdge(edge.get1(), edge.get2(), edge.getWeight());
				  sets.union(sets.find(v1), sets.find(v2));
			  }
		  } catch (QueueEmptyException e) {}
	  }
	  return minSpanTree;
  	}

	private static LinkedQueue sortEdges(WUGraph g){
		Object[] vertices = g.getVertices();
		LinkedQueue q = new LinkedQueue();
		for(Object vertex: vertices){
			Neighbors n = g.getNeighbors(vertex);
			for(int a = 0; a < n.neighborList.length ; a++){
				Edge edge = new Edge(vertex, n.neighborList[a], n.weightList[a]);
				q.enqueue(edge);
			}
		}
		ListSorts.quickSort(q);
		return q;
	}
}