## Weighted Graph
This project implement three different interfaces:

node_info ->
This interface represents the set of operations applicable on a node (vertex) in a weighted graph including:
* 0. getKey(); - Return the key (id) associated with a specific node.
* 1. getInfo(); -Return the remark (meta data) associated with a specific node.
* 2. setInfo(String s); - Allows changing the remark (meta data) associated with a specific node.
* 3. getTag(); - Temporal data (aka distance, color, or state) which hold the weight of edge.
* 4. setTag(double t); - set the weight for temporal marking an node

weighted_graph -> 
This interface represents an undirectional weighted graph.
It supports a large number of nodes (over 10^6, with average degree of 10).
The implementation is based on one Hashmap that represent the collection of vertexes and another hashmap that represent the collection of edges, including:
* 0. getNode(int key); - Return the node_data by the node_id.
* 1. hasEdge(int node1, int node2); - Return true iff (if and only if) there is an edge between node1 and node2.
* 2. getEdge(int node1, int node2); - Return the weight if the edge (node1, node1).
* 3. addNode(int key); - Add a new node to the graph with the given key.
* 4. connect(int node1, int node2, double w); - Connect an edge between node1 and node2, with an edge with weight >=0.
* 5. getV(); - Return a pointer (shallow copy) for a collection representing all the nodes in the graph.
* 6. getV(int node_id); - Returns a Collection containing all the nodes connected to node_id.
* 7. removeNode(int key); - Delete the node (with the given ID) from the graph and removes all edges which starts or ends at this node.
* 8. removeEdge(int node1, int node2); - Delete the edge from the graph.
* 9. nodeSize(); - Return the number of vertices (nodes) in the graph.
* 10. edgeSize(); - Return the number of edges (undirectional graph).
* 11. getMC(); - Return the Mode Count - for testing changes in the graph.


weighted_graph_algorithms ->
This interface represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy) - Compute a deep copy of this weighted graph.
 * 1. init(graph); - Init the graph on which this set of algorithms operates on.
 * 2. isConnected(); -  Returns true if and only if (iff) there is a valid path from EVREY node to each.
 * 3. dijkstraCounter(int source) - Dijkstra's algorithm - is an algorithm for finding the shortest paths between nodes in a graph, which may represent, for example, road networks.
 * 4. double shortestPathDist(int src, int dest); - Returns the length of the shortest path between src to dest.
 * 5.  List<node_data> shortestPath(int src, int dest); - Returns the shortest path between src to dest.
 * 6.  Save(file); - Saves this weighted (undirected) graph to the given file name.
 * 7. Load(file); - Load a graph to this graph algorithm.
    


WGraph_DS is a class that represents an (undirectional) weighted graph by implementing the interfaces: weighted_graph & node_info.
The graph supports a large number of nodes (over 10^6, with average degree of 10) and the
implementaition for this class bases on one Hashmap that represent the collection of vertexes on the graph.

WGraph_Algo represents the "regular" Graph Theory algorithms including:
A deep copy of the graph, initializing the graph, checkment if there is a valid path from every vertex to each
other vertex, Getting the length or a collection of the shortest path between two vertexes.

The implementaition for this class bases on Dijkstra's algorithm.
Dijkstra's algorithm is an algorithm for finding the shortest paths between nodes in a graph, which may represent, for example, road networks.

I prefered to choose an HashMap on both cases (NodeData & Graph_DS) because it can old a key and value at once and the program lifecycle phase is low.
Each key and value represented a vertex in the graph.
