import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Dijkstra{
	public static ArrayList<String> nodes = new ArrayList<String>();
    
	public static void main(String[] arg){
		
		Dijkstra obj = new Dijkstra();
		Graph g = null;
		
		String line = null;
        Integer linenum=0,vertices=0,edges=0, weight=0;
        boolean isUnDirected = true;
        String src=null, dest=null, sourceNode=null;
        
        try {
            FileReader fileReader = new FileReader("src\\input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                linenum++;
                if(linenum==1){
                    String[] splited = line.split("\\s+");
                    vertices=Integer.parseInt(splited[0]);
                    edges=Integer.parseInt(splited[1]);
                    isUnDirected = splited[2].equals("U")? true: false;
                    if(isUnDirected)
                    	System.out.println("Undirected Graph: ");
                    else
                    	System.out.println("Directed Graph: ");
            		// Create a new graph.
                    g = new Graph(vertices);  
                    continue;
                }
                if(linenum==edges+2){
                    sourceNode=line;
                    continue;
                }
                if(linenum <= edges+1) {
                	String[] edgesLine = line.split("\\s+");
                	src=edgesLine[0];
                	dest=edgesLine[1];
                	weight=Integer.parseInt(edgesLine[2]);
                	g.addVertex(src);
                	g.addVertex(dest);
                	//Adding edges to the graph
                	if(nodes.indexOf(src) == -1) {
                		nodes.add(""+src);
                	}
                	if(nodes.indexOf(dest) == -1) {
                		nodes.add(""+dest);
                	}
                	g.addEdge(nodes.indexOf(src), nodes.indexOf(dest), weight);
                	//If it is undirected graph, connect the edge from dest to src also to make it bidirectional.
                	if(isUnDirected) {
                		g.addEdge(nodes.indexOf(dest), nodes.indexOf(src), weight);
                	}
                	System.out.println(line);
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
            System.out.println(ex);
        }
        catch(IOException ex) {
            System.out.println("Error reading file '");
            ex.printStackTrace();
        }
		
		// Calculate Minimum distance using Dijkstra.
		obj.calculate(g.getVertex(nodes.indexOf(sourceNode)));	

		// Print the minimum Distance from source to destination.
		for(Vertex v:g.getVertices()){
			System.out.print("\nNode - "+v+" \nCost - "+ ((int)v.minDistance)+" \nPath - ");
			for(Vertex pathvert:v.path) {
				System.out.print(pathvert+" ");
			}
			System.out.println(""+v);
		}
	}

	public void calculate(Vertex source){
		
		source.minDistance = 0;
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		queue.add(source);

		while(!queue.isEmpty()){

			// 1. Take the unvisited node with minimum weight.
			Vertex u = queue.poll();
			// Visit all its neighbours.
			for(Edge neighbour:u.neighbours){
				Double newDist = u.minDistance+neighbour.weight;
				//Update the distances for all the neighbours (In the Priority Queue).
				if(neighbour.target.minDistance>newDist){
					// Remove the node from the queue to update the distance value.
					queue.remove(neighbour.target);
					neighbour.target.minDistance = newDist;
					
					// Take the path visited till now and add the new node.s
					neighbour.target.path = new LinkedList<Vertex>(u.path);
					neighbour.target.path.add(u);
					
					//Re-enter the node with new distance.
					queue.add(neighbour.target);					
				}
			}
		}
	}

}

