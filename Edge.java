public class Edge implements Comparable {
	protected Object v1, v2;
	protected int weight;
	
	public Edge(Object o1, Object o2, int w) {
		v1 = o1;
		v2 = o2;
		weight = w;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public Object get1() {
		return v1;
	}
	
	public Object get2() {
		return v2;
	}
	
	public boolean sameEdge(Object v1, Object v2) {
		return ((v1 == this.v1 && v2 == this.v2) || (v2 == this.v1 && v1 == this.v2));
	}
	
	public int compareTo(Object e) {
		return this.getWeight() - ((Edge)e).getWeight();
	}
}