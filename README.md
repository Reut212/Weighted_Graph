## Graph

    An implementation of an (undirectional) unweighted graph in Java.

NodeData is a class that represents the set of operations applicable on a node (vertex) in an (undirectional) unweighted graph.
Part of the operations are:
Getting an ID for a specific vertex.
Removing a specific vertex from the graph
Getting a collection of neighbor's vertexs of a specific vertex.
Getting the info and tag of a specific vertex and apply them.

The implementaition for this class bases on one Hashmap that represent the collection of neighbors.

Graph_DS is a class that represents an (undirectional) unweighted graph by:
beeing able to get a vertex by his ID, adding a new vertex, connecting two vertexes,
getting a collection of vertexes on a graph, removing a vertex or an edge from the graph,
updating the number of vertexes and edges on the graph and follows the number of changes in the graph.

The graph supports a large number of nodes (over 10^6, with average degree of 10) and the
implementaition for this class bases on one Hashmap that represent the collection of vertexes on the graph.

Graph_Algo represents the "regular" Graph Theory algorithms including:
A deep copy of the graph, initializing the graph, checkment if there is a valid path from every vertex to each
other vertex, Getting the length or a collection of the shortest path between two vertexes.

The implementaition for this class bases on Breadth-First Search (BFS).
BFS is an algorithm for traversing or searching tree or graph data structures.

I prefered to choose an HashMap on both cases (NodeData & Graph_DS) because it can old a key and value at once and the program lifecycle phase is low.
Each key and value represented a vertex in the graph.
