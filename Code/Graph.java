import java.util.*;
public class Graph {
  private ArrayList<Vertex> vertices;
  private ArrayList<String> vertices_str;
	public Graph(int numberVertices){
		vertices = new ArrayList<Vertex>(numberVertices);
		vertices_str = new ArrayList<String>(numberVertices);
	}
	public void addVertex(String vertex_str) {
		Vertex vertex = new Vertex(vertex_str);
		if(vertices_str.indexOf(vertex_str) == -1) {
			vertices.add(vertex);
			vertices_str.add(vertex_str);
		}
	}
	
	public void addEdge(int src, int dest, int weight){
		Vertex s = vertices.get(src);
		Edge new_edge = new Edge(vertices.get(dest),weight);
		s.neighbours.add(new_edge);
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	public Vertex getVertex(int vert){
		return vertices.get(vert);
	}
}
