package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class testForGraph {
    private static Random _rnd = null;

    //This Method check the size of the graph after removing some nodes
    @Test
    void nodeSize() {
        weighted_graph g1 = createGraph();
        int s = g1.nodeSize();
        g1.removeNode(9);
        s = g1.nodeSize();
        assertEquals(9,s);
        g1.removeNode(5);
        s = g1.nodeSize();
        assertEquals(8,s);
        g1.removeNode(8);
        s = g1.nodeSize();
        assertEquals(7,s);
    }
    //This test update w to other weight and connect again at the opposite direction
    @Test
    void edgeSize() {
        weighted_graph g1 = createGraph();
        int es =  g1.edgeSize();
        assertEquals(14, es);
        assertEquals(g1.getEdge(0,1), g1.getEdge(1,0));
        assertEquals(g1.getEdge(0,2), 11);
    }

    @Test
    void getV() {
        weighted_graph g1 = createGraph();
        int s = g1.nodeSize();
        Collection<node_info> v = g1.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            Assertions.assertNotNull(n);
        }
    }

    @Test
    void hasEdge() {
        weighted_graph g1 = createGraph();
        assertTrue(g1.hasEdge(0,1));
        assertTrue(g1.hasEdge(1,6));
        assertTrue(g1.hasEdge(2,6));
        assertTrue(g1.hasEdge(0,2));

        assertFalse(g1.hasEdge(4,2));
        assertFalse(g1.hasEdge(4,0));
        assertFalse(g1.hasEdge(2,2));

    }

    @Test
    void connect() {
        weighted_graph g1 = createGraph();
        int v = g1.nodeSize();
        g1.removeEdge(0,1);
        assertFalse(g1.hasEdge(1,0));
        g1.connect(0,1,10);
        assertFalse(g1.hasEdge(1,10)); //there's no edge
        g1.removeEdge(2,1);  //there's no edge

        double w = g1.getEdge(9,8);
        assertEquals(w,22);
    }


    @Test
    void removeNode() {
        weighted_graph g1 = createGraph();
        g1.removeNode(2);
        assertFalse(g1.hasEdge(6,2));
        g1.removeNode(0);
        assertFalse(g1.hasEdge(0,2));
        int e = g1.edgeSize();
        assertEquals(9,e);
        int v = g1.nodeSize();
        assertEquals(8,v);
    }

    @Test
    void removeEdge() {
        weighted_graph g1 = createGraph();
        g1.removeEdge(0,1);
        double w = g1.getEdge(0,1);
        assertEquals(w,-1);
    }

    @Test
    void isConnected (){
        weighted_graph UnconnectedGraph = createGraph();
        UnconnectedGraph.addNode(70);
        weighted_graph_algorithms algo = new WGraph_Algo();

        algo.init(UnconnectedGraph);

        assertFalse(algo.isConnected());
    }


    @Test
    void shortestPathDist() {
        weighted_graph g0 = createGraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0,6);
        assertEquals(d, 26);
    }


    @Test
    void shortestPath() {
        weighted_graph g0 = createGraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,6);
        int[] checkKey = {0, 1, 6};
        int i = 0;
        for(node_info n: sp) {
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
    }

    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }

    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
    public static weighted_graph createGraph(){
        weighted_graph g0 = new WGraph_DS();
        for (int i=0; i<10; i++) {
            g0.addNode(i);
        }

        g0.connect(0,1,10);
        g0.connect(0,2,11);
        g0.connect(0,3,12);
        g0.connect(1,6,16);
        g0.connect(2,5,14);
        g0.connect(2,6,15);
        g0.connect(3,4,20);
        g0.connect(3,5,13);
        g0.connect(4,5,19);
        g0.connect(4,8,21);
        g0.connect(5,7,18);
        g0.connect(6,7,17);
        g0.connect(7,9,23);
        g0.connect(8,9,22);


        return g0;
    }
}
