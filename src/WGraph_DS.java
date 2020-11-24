package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class WGraph_DS implements weighted_graph ,Serializable {


    public class Node_Info implements node_info, Comparable<node_info>, Serializable {

        private int nodeID;
        private String info;
        private double tag;

        public Node_Info(int id, double tag, String info) {
            this.nodeID = id;
            this.tag = tag;
            this.info = info;
        }


        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return this.nodeID;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */

        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * The compareTo() method compares the given node with current node.
         * It returns positive number, negative number or 0.
         * @param  o
         * @return 1,-1,0
         */
        @Override
        public int compareTo(node_info o) {
            // TODO Auto-generated method stub
            Double w1 = this.getTag();
            Double w2 = o.getTag();

            if (w1 > w2) // o1 > o2
                return 1;
            else if (w1 < w2) // o1 < o2
                return -1;
            return 0; // o1 == o2
        }

        /**
         * The method determines whether the Number object that invokes the method is equal to the object that is passed as an argument.
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            node_info nodeInfo = (node_info) o;

            return nodeID == nodeInfo.getKey();
        }
        @Override
        public int hashCode() {
            return nodeID;
        }
    }

    private final HashMap<Integer, node_info> collectionOfVertexes;
    private final HashMap<Integer, HashMap<node_info, Double>> edges; // { entry<k, v>, ,entry<k1, v1>, <k2, v2> }
    private int MC;
    private int sizeOfEdge;


    public WGraph_DS() {
        collectionOfVertexes = new HashMap<Integer, node_info>();
        edges = new HashMap<Integer, HashMap<node_info, Double>>();
        MC = 0;
        sizeOfEdge = 0;
    }


    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (collectionOfVertexes.containsKey(key))
            return collectionOfVertexes.get(key);
        return null;
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        node_info firstNode = getNode(node1);
        node_info secondNode = getNode(node2);

        if (collectionOfVertexes.containsKey(node1) && collectionOfVertexes.containsKey(node2) && node1 != node2
                && firstNode != null && secondNode != null)
            return edges.get(node1).containsKey(secondNode) && edges.get(node2).containsKey(firstNode);

        return false;
    }

    /**
     * return the weight if the edge (node1, node2). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        node_info firstNode = getNode(node1);
        node_info secondNode = getNode(node2);

        if (collectionOfVertexes.containsKey(node1) && collectionOfVertexes.containsKey(node2) && node1 != node2
                && firstNode != null && secondNode != null) {

            if (hasEdge(node1, node2) && hasEdge(node2, node1)) {
                double d = edges.get(node1).get(secondNode);
                return d;
            }
        }

        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {

//        node_info newNode = getNode(key); // null

        if (!collectionOfVertexes.containsKey(key)) {

            collectionOfVertexes.put(key, new Node_Info(key, -1, ""));
            edges.put(key, new HashMap<node_info, Double>());
            MC++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        node_info firstNode = getNode(node1);
        node_info secondNode = getNode(node2);

        //if those nodes not null, appear in the graph, not with the same key and there is no an edge between them
        if (collectionOfVertexes.containsKey(node1) && collectionOfVertexes.containsKey(node2) && node1 != node2 && w >= 0
                && firstNode != null && secondNode != null) {

            if (!hasEdge(node1, node2) && !hasEdge(node2, node1)) {

                edges.get(node1).put(secondNode, w);
                edges.get(node2).put(firstNode, w);
                sizeOfEdge++;
                MC++;

            } else {//if (getEdge(node1, node2) != w)

                MC++;
                edges.get(node1).put(secondNode, w);
                edges.get(node2).put(firstNode, w);
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return new ArrayList<>(collectionOfVertexes.values());
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {

        if (edges.containsKey(node_id)) {
            return new ArrayList<>(edges.get(node_id).keySet());

        } else return null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        node_info removeKey = getNode(key);

        if (collectionOfVertexes.containsKey(key) && removeKey != null) { //if the vertex is in the graph

            for (node_info d : getV(removeKey.getKey())) {
                removeEdge(key, d.getKey()); //go to each vertex on the graph and remove all edges which starts or ends
            }                                // at this node.//update the sum changes
            MC++;//update the sum changes

            this.edges.remove(key);
            return collectionOfVertexes.remove(key);//remove the vertex from the graph
        }
        return null;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        node_info firstNode = getNode(node1);
        node_info secondNode = getNode(node2);
        //check if those nodes are in the graph
        //and check if there is an edge between them

        if (collectionOfVertexes.containsKey(node1) && collectionOfVertexes.containsKey(node2) && node1 != node2
                && firstNode != null && secondNode != null) {

            if (hasEdge(node1, node2) && hasEdge(node2, node1)) {
                edges.get(node1).remove(secondNode);
                edges.get(node2).remove(firstNode);
                MC++; //update the sum changes
                sizeOfEdge--; //update the size of edges
            }
        }
    }
    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return collectionOfVertexes.size();
    }
    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return sizeOfEdge;
    }
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * The method determines whether the Number object that invokes the method is equal to the object that is passed as
     * an argument.
     * @param o
     * @return
     */
    @Override
        public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        if(nodeSize() != wGraph_ds.nodeSize() && sizeOfEdge == wGraph_ds.sizeOfEdge)
            return false;
        for (int i : collectionOfVertexes.keySet() ) {
            if(!edges.get(i).equals(wGraph_ds.edges.get(i)))
                return false;
        }
        return true;
    }

}

