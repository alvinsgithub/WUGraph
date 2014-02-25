package graph;
import list.*;

public class Vertex {
	
	protected Object item;
	protected DList edges;
	
	public Vertex(Object item) {
		this.item = item;
		edges = new DList();
	}
	
	public Object getItem() {
		return this.item;
	}
	
	public DList getEdges() {
		return edges;
	}
}