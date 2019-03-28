
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Prim { 

    public static class Edge{
        int src, dest;
        double weight;
        
        Edge(int s, int d, double w){
            src = s;
            dest = d;
            weight = w;
        }
    }
    /*
     * Prims Algorithm to generate the Minimum Spanning Tree (MST)
     * @param G - input Graph 
     */
    public static ArrayList<Edge> Prims(ArrayList<ArrayList<Edge>> graph){
        if(graph.isEmpty())throw new NullPointerException("The Graph is empty");
        
        ArrayList<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>((Object o1, Object o2) -> {
            Edge first = (Edge)o1;
            Edge second = (Edge)o2;
            
            if(first.weight<second.weight)return -1;
            else if(first.weight>second.weight)return 1;
            else return 0;
        });
        
        for(Edge e:graph.get(0)){
            pq.add(e);
        } 
        
        boolean[] marked = new boolean[graph.size()];
        marked[0] = true;
        while(!pq.isEmpty()){
            Edge e = pq.peek();
            
            pq.poll();
            if(marked[e.src] && marked[e.dest])continue;
            marked[e.src] = true;  
            for(Edge edge:graph.get(e.dest)){
                if(!marked[edge.dest]){
                    pq.add(edge);  
                }
            }
            marked[e.dest] = true; 
            mst.add(e);
            
        }
        return mst;
    }
    
    /*
     * Create Graph using edges
     * @param edges - Array of edges
     */
    public static ArrayList<ArrayList<Edge>> createGraph(Edge[] edges){
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        int length = edges.length*2;
        for(int i=0;i<length;++i){
            graph.add(new ArrayList<>());
        }
        
        for(Edge e:edges){
            Edge other = new Edge(e.dest, e.src, e.weight);
            graph.get(e.src).add(e);
            graph.get(e.dest).add(other);
            //System.out.println("Added edge ["+e.from+", "+e.to+" : "+e.weight+"] "+"["+e.to+", "+e.from+" : "+e.weight+"]");
        }
        
        return graph; 
    }
    public static ArrayList<String> nodes = new ArrayList<String>();
    public static void main(String[] args){
        
    	Edge[] edges = null;
    	
		String line = null;
        Integer linenum=0,vertices=0,weight=0, no_edges=0;
        String src=null, dest=null, sourceNode=null;
        
        
        try {
            FileReader fileReader = new FileReader("src\\input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            int index=0;
            while((line = bufferedReader.readLine()) != null) {
                linenum++;
                if(linenum==1){
                    String[] splited = line.split("\\s+");
                    vertices=Integer.parseInt(splited[0]);
                    no_edges=Integer.parseInt(splited[1]);
                    
            		// Create a new graph.
                    edges = new Edge[no_edges];
                    
                    continue;
                }
                if(linenum==no_edges+2){
                    sourceNode=line;
                    continue;
                }
                if(linenum <= no_edges+1) {
                	String[] edgesLine = line.split("\\s+");
                	src=edgesLine[0];
                	dest=edgesLine[1];
                	weight=Integer.parseInt(edgesLine[2]);
                	nodes.add(src);
                	nodes.add(dest);
                	edges[index++] = new Edge(nodes.indexOf(dest), nodes.indexOf(src), weight);
                	
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
		
        ArrayList<ArrayList<Edge>> graph = createGraph(edges);
        ArrayList<Edge> MST = Prims(graph);
        double total_weight = 0;
        System.out.println("\nEdges of the Tree : ");
        for(Edge e:MST){
            System.out.println("Edge => "+nodes.get(e.src)+" - "+nodes.get(e.dest)+" \nCost :  "+e.weight+"\n");
            total_weight = total_weight + e.weight;
        } 
        System.out.println("\nTotal Cost : "+total_weight);
    }
}