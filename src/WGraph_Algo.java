package ex1.src;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {

    private static weighted_graph g;

    private HashMap<Integer, Double> dist;
    private HashMap<Integer, Integer> prev;

    public WGraph_Algo() {
        g = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
        this.dist = new HashMap<>();
        this.prev = new HashMap<Integer, Integer>();
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return g;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph n = new WGraph_DS(); //make a new graph
        for (node_info m : getGraph().getV()) { //go over all vertexes in the graph
            n.addNode(m.getKey()); //copy all vertexes to the new graph (n)
        }
        //Tester for Deep - copy
//        g.getNode(1).setTag(1);
//        System.out.println(g.getNode(1).getTag());
//        System.out.println(n.getNode(1).getTag());

        for (node_info m : getGraph().getV()) { //go over all vertexes in the graph
            //go over all neighbors of graph's vertexes
            for (node_info neighbor : getGraph().getV(m.getKey())) {
                //and make the connection on the new graph (n)
                n.getEdge(m.getKey(), neighbor.getKey());
            }
        }
        return n; //return the new graph (n) - a copy of the old graph (g)
    }
    /**
     * Dijkstra's algorithm - is an algorithm for finding the shortest paths between nodes in a graph,
     * which may represent, for example, road networks.
     * @return
     */
    public void dijkstraCounter(int source) {
    	
    	PriorityQueue<node_info> queue = new PriorityQueue<>();
    	
//    	weighted_graph g1 = new WGraph_DS();
//    	weighted_graph g2 = new WGraph_DS();
//    	
//    	if (g1 == g2) //i dont know.
//    		System.out.println("equals");

        for (node_info vertex : getGraph().getV()) {
        	if (vertex.getKey() == source) {
        		this.dist.put(source, 0.0);
        		vertex.setTag(0.0);
        	}
        	else {
        		vertex.setTag(Double.MAX_VALUE);
        		this.dist.put(vertex.getKey(), Double.MAX_VALUE);
        		this.prev.put(vertex.getKey(), null);
        	}
        	
        	queue.add(vertex);
        }
        
        
        while (!queue.isEmpty()) {
        	
        	node_info u = queue.poll(); //remove and return
        	
        	for (node_info neighbor : this.g.getV(u.getKey())) {
				
        		double alt = this.dist.get(u.getKey()) + this.g.getEdge(u.getKey(), neighbor.getKey());
        		
        		if (alt < this.dist.get(neighbor.getKey())) {
        			
        			this.dist.put(neighbor.getKey(), alt);
        			this.prev.put(neighbor.getKey(), u.getKey());
        			
        			//decrease priority: remove + add
        			queue.remove(neighbor);
        			
        			neighbor.setTag(alt); //update priority.
        			
        			queue.add(neighbor);
        		}
			}
        	
        }
    }
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
    @Override
    public boolean isConnected() {
    	
        if (getGraph().nodeSize() == 0 || getGraph().nodeSize() == 1) //if there is no Vertexes on the graph or there is only one - return true
            return true;
     
        node_info node = getGraph().getV().iterator().next(); //let's choose a Vertex from the graph
        dijkstraCounter(node.getKey()); //c=the number of vertexes BFS algorithm met
        
        //if the number of vertexes on the graph is equal to the number of vertexes BFS algorithm met
        //the graph is connected, if not the graph is not connected
        
//        return this.dist.containsValue(Double.MAX_VALUE);
        
        for (Double dist : this.dist.values()) {
			if (dist == Double.MAX_VALUE)
				return false;
		}
        
        return true;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
    	dijkstraCounter(src);
    	return this.dist.get(dest);
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
    	
    	dijkstraCounter(src);
    	
    	List<node_info> temp = new ArrayList<>();
    	List<node_info> reverse = new ArrayList<>();
    	
    	int father = dest;
    	
    	temp.add(this.g.getNode(dest));
    	
    	while (true) {
    		
    		if (father == src) break;
    		
    		temp.add( this.g.getNode( this.prev.get(father) ) );
    		father = this.prev.get(father);
    		
    	}
    	
    	for (int i = temp.size()-1; i >= 0; i--) {
			reverse.add(temp.get(i));
		}
    	
        return reverse;
    }
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {

    	try {

			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

			objectOut.writeObject(getGraph());

			objectOut.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
            System.out.println("File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return false;
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
    	
    	try {
    		
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			this.g = (weighted_graph) objectIn.readObject();
			
			objectIn.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return false;
    }
}

