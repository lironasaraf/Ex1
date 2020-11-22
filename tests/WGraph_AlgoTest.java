import ex1.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_AlgoTest {
    @Test
    void isConnected() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(1,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(2,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(2,1,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        assertTrue(b);
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        double d = ag0.shortestPathDist(0,5);
        assertEquals(27.0, d);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,5);
        //double[] checkTag = {4.0,3.0,20.0};
        int[] checkKey = {0, 3, 1, 5}; // w=27
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

    private weighted_graph small_graph() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(6,0,1);
        g0.connect(0,3,4);
        g0.connect(0,5,100);
        g0.connect(1,3,3);

        g0.connect(1,4,1);
        g0.connect(1,5,20);
        g0.connect(2,0,3);
        g0.connect(2, 4,7);


        return g0;
    }
}
