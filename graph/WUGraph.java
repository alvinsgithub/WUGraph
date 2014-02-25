/* WUGraph.java */

package graph;
import list.*;
//import java.util.Hashtable;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	
	protected DList vertices;
    protected HashTableChained vHash;
    protected HashTableChained eHash;
	protected int numEdges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
	  vertices = new DList();
	  vHash = new HashTableChained();
	  eHash = new HashTableChained();
	  numEdges = 0;
	  
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
	  return vertices.length();
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
	  return numEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
	  Object[] result = new Object[vertexCount()];
	  DListNode curr = (DListNode) vertices.front();
	  for (int i = 0 ; i < vertexCount() ; i++) {
		  try {
			result[i] = ((Vertex) (curr.item())).getItem();
			curr = (DListNode)curr.next();
			
		} catch (InvalidNodeException e) {}
	  }
	  return result;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
	  if (!isVertex(vertex)) {
		  vertices.insertBack(new Vertex(vertex));
		  vHash.insert(vertex, vertices.back());
	  }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
	  if (isVertex(vertex)) {
		  DList list = eList(vertex);
		  DListNode curr = (DListNode) list.front();
		  VertexPair edge;
		  Object otherV;
		  while (list.length() > 0) {
			  try {
				edge = ((Edge) curr.item()).getEdge();
				otherV = edge.other(vertex);
				removeEdge(vertex, otherV);
				curr = (DListNode) list.front();
			} catch (InvalidNodeException e) {}
		  }
		  DListNode node = ((DListNode) vHash.find(vertex).value());
		  vHash.remove(vertex);
		  vertices.remove(node);
	  }
  }
  
  private DList eList(Object vertex) {
	  DList result = new DList();
	  if (!isVertex(vertex)) {
		  return null;
	  } else {
		  DListNode node = (DListNode) (vHash.find(vertex).value());
		  try {
			result = ((Vertex)node.item()).getEdges();
		} catch (InvalidNodeException e) {}
	  }
	  return result;
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
	  return vHash.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
	  if (isVertex(vertex)) {
		  return eList(vertex).length();
	  } else {
		  return 0;
	  }
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
	  if (isVertex(vertex) && degree(vertex) > 0) {
		  Neighbors result = new Neighbors();
		  result.weightList = new int[degree(vertex)];
		  result.neighborList = new Object[degree(vertex)];
		  DList list = eList(vertex);
		  DListNode node = (DListNode) list.front();
		  Edge edge;
		  for (int i = 0 ; i < degree(vertex) ; i++) {
			  try {
				edge = (Edge) node.item();
				result.weightList[i] = edge.getWeight();
				result.neighborList[i] = edge.getEdge().other(vertex);
				node = (DListNode) node.next();
			} catch (InvalidNodeException e) {}
		  }
		  return result;
	  } else {
		  return null;
	  }
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
	  if (!isVertex(u) || !isVertex(v)) {
		  return;
	  }
	  DListNode uNode, vNode;
	  if (isEdge(u, v)) {
		  removeEdge(u,v);
	  }
	  VertexPair edge = new VertexPair(u, v);
	  Edge uEdge = new Edge(null, edge, weight);
	  Edge vEdge = new Edge(null, edge, weight);
	  DList uList = eList(u);
	  DList vList = eList(v);
	  if (uList == vList) {
		  uList.insertFront(uEdge);
		  uNode = (DListNode) uList.front();
	  } else {
		  uList.insertFront(uEdge);
		  vList.insertFront(vEdge);
		  uNode = (DListNode) uList.front();
		  vNode = (DListNode) vList.front();
		  try {
			((Edge) (uNode.item())).setPartner(vNode);
			((Edge) (vNode.item())).setPartner(uNode);
		  } catch (InvalidNodeException e) {}
	  }
	  eHash.insert(edge, uNode);
	  numEdges++;
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
	  if (isVertex(u) && isVertex(v) && isEdge(u, v)) {
		  VertexPair edge = new VertexPair(u, v);
		  DListNode node = (DListNode) eHash.find(edge).value();
		  try {
			  DListNode pNode = ((Edge) node.item()).getPartner();
			  eHash.remove(edge);
			  DList uList = eList(u);
			  DList vList = eList(v);
			  if (pNode == null) {
				  //System.out.println("HAHAHAHAHAHHAHAHAHAHA");
				  uList.remove(node);
			  } else {
				  uList.remove(node);
				  vList.remove(node);
				  vList.remove(pNode);
				  uList.remove(pNode);
			  }
		  } catch (InvalidNodeException e) {}
		  numEdges--;
	  }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
	  VertexPair edge = new VertexPair(u, v);
	  return eHash.find(edge) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
	  if (isEdge(u, v)) {
		  VertexPair edge = new VertexPair(u, v);
		  DListNode node = (DListNode) eHash.find(edge).value();
		try {
			return ((Edge) node.item()).getWeight();
		} catch (InvalidNodeException e1) {}
	  }
	  return 0;
  }

}
