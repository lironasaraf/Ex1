package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;

    public WGraph_Algo() {
        graph = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {

        this.graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {

        return this.graph;
    }
    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(this.graph);
    }

    private void initNodesTag(double tag) {
        for (node_info v : this.graph.getV()) {
            v.setTag(tag);
        }
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        int vSize = this.graph.getV().size();
        if (vSize == 0 || vSize == 1) { // are connected
            return true;
        }
        List<List<node_info>> connectivityComponents = this.getConnectivityComponents();
        if (connectivityComponents.size() != 1){ //if there is more than one list the graph is not connected
            return false;
        }
        return connectivityComponents.get(0).size() == vSize; // same size means all the vertices connected
    }

    private List<List<node_info>> getConnectivityComponents() {
        this.initNodesTag(1); // all the nodes we didn't visit
        List<List<node_info>> graphComponents = new ArrayList<>();
        for (node_info v : this.graph.getV()) {
            if (v.getTag() == 1) {
                List<node_info> component = this.dfs(v); // put them in the stack of visited
                graphComponents.add(component);
            }
        }
        return graphComponents;
    }

    private List<node_info> dfs(node_info srcNode) {
        List<node_info> component = new ArrayList<>();
        Stack<node_info> visited = new Stack<>();
        visited.push(srcNode);
        // white = 1, gray = 2, black = 3
        srcNode.setTag(2);
        while (!visited.empty()) {
            node_info v = visited.pop();
            component.add(v);
            for (node_info u : this.graph.getV(v.getKey())) {
                if (u.getTag() == 1) {
                    u.setTag(2);
                    visited.push(u);
                }
            }
            v.setTag(3);
        }
        return component;

    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        List<node_info> path = shortestPath(src,dest);
        // s -> v1 -> v2 - > v3 /....... ->dest
        if(path.size()<=1) return 0;
        double cost = 0;
        int node1 = src;
        for(int i = 1; i < path.size(); i++){
            int node2 = path.get(i).getKey();
            cost += this.graph.getEdge(node1,node2);
            node1 = node2;
        }
        return cost;
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
        HashMap<node_info, node_info> fathers = new HashMap<>();
        PriorityQueue<node_info> queue = new PriorityQueue<>(new Compare());
        Set<node_info> visited = new HashSet<>();
        node_info start = this.graph.getNode(src);
        node_info goal = this.graph.getNode(dest);
        this.initNodesTag(Double.POSITIVE_INFINITY);
        start.setTag(0);
        queue.add(start);
        while(!queue.isEmpty()){
            node_info current = queue.poll();
            visited.add(current);
            if(current == goal)
                break;

            for(node_info child: this.graph.getV(current.getKey())){
                double optionalCost = current.getTag() + graph.getEdge(current.getKey(),child.getKey());
                if(visited.contains(child)) continue;
                // not visited
                if(queue.contains(child)){
                    if(optionalCost < child.getTag()) {
                        child.setTag(optionalCost);
                        queue.remove(child);
                        queue.offer(child);
                        fathers.put(child,current);
                    }
                }else{
                    queue.add(child);
                    child.setTag(optionalCost);
                    fathers.put(child,current);
                }
            }

        }
        if(goal.getTag() == -1) return new ArrayList<>();

        List<node_info> result = new ArrayList<>();


        node_info temp =  goal;
        while (temp != start){
            result.add(0,temp);
            temp = fathers.get(temp);
        }
        result.add(0,temp);

        return result;
    }
    public static class Compare implements Comparator<node_info> {
        @Override
        public int compare(node_info node1, node_info node2) {
            double d1 =node1.getTag();
            double d2 =node2.getTag();
            return Double.compare(d1,d2);
            //return Integer.compare(0, Double.compare(d1, d2));
        }
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param filename - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String filename) {


        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.graph);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param filename - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String filename) {
        weighted_graph object1 = null;

        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object1 = (weighted_graph) in.readObject();
            //
            this.graph = object1;

            in.close();
            file.close();
            return true;
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            return false;

        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
            return false;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_Algo that = (WGraph_Algo) o;
        return Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}

