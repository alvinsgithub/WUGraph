package graph;
import list.*;

public class Edge {
	
	protected DListNode partner;
	protected VertexPair edge;
	protected int weight;
	
	public Edge(DListNode p, VertexPair e, int w) {
		partner = p;
		edge = e;
		weight = w;
	}
	
	public VertexPair getEdge() {
		return edge;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int w) {
		weight = w;
	}
	
	public DListNode getPartner() {
		return partner;
	}
	
	public void setPartner(DListNode p) {
		partner = p;
	}
	
	
}