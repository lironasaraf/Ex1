package ex1;
import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private Map<Integer, node_info> nodesMap =  new Hashtable<>();
    private Set<node_info> vertices = new HashSet<>();
    private Map<Integer, HashMap<Integer,Double>> edges = new HashMap<>();
    private int modeCount = 0;
    private int edgesSize = 0;

    public WGraph_DS(weighted_graph graph){
        //adding nodes
        for(node_info node: graph.getV()){
            this.addNode(node.getKey());
        }
        //adding edges
        for(Integer key: this.nodesMap.keySet()){
            for(node_info node: graph.getV(key)){
                this.connect(key,node.getKey(),graph.getEdge(key,node.getKey()));
            }
        }
    }

    public WGraph_DS() {

    }

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return this.nodesMap.get(key);
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
        if (!edges.containsKey(node1) || !edges.containsKey(node2)) return false;
        if (node1 == node2) return false;
        return edges.get(node1).containsKey(node2);
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!this.hasEdge(node1,node2)) return -1;
        return this.edges.get(node1).get(node2);

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
        if (this.nodesMap.containsKey(key)) return;
        node_data newNode = new node_data(key);
        this.nodesMap.put(key,newNode);
        this.vertices.add(newNode);
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {

        if (node1 == node2) return;
        //is valid nodes
        if (!this.nodesMap.containsKey(node1) || !this.nodesMap.containsKey(node2))
            return;
        //update weight
        if (this.hasEdge(node1, node2)){
            this.edges.get(node1).replace(node2,w);
            this.edges.get(node2).replace(node1,w);
            return;
        }
        //There isn't an edge
        HashMap<Integer,Double> ni1 = this.edges.get(node1);
        HashMap<Integer,Double> ni2 = this.edges.get(node2);
        if (ni1 == null) {
            ni1 = new HashMap<>();
            this.edges.put(node1, ni1);
        }
        if (ni2 == null) {
            ni2 = new HashMap<>();
            this.edges.put(node2, ni2);

        }
        ni1.put(node2,w);
        ni2.put(node1,w);
        this.modeCount++;
        this.edgesSize++;
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
        Set<node_info> copy = new HashSet<>();
        copy.addAll(this.vertices);
        return copy;
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        HashMap<Integer,Double> mapNi = edges.get(node_id);
        if(mapNi == null)return new ArrayList<>();
        Set<Integer> ni = mapNi.keySet();
        Set<node_info> result = new HashSet<>();

        for(Integer key:ni){
            result.add(this.nodesMap.get(key));
        }

        return result;
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
        if (!this.nodesMap.containsKey(key)) {
            return null;
        }
        this.modeCount++;
        node_info v = this.getNode(key);
        this.vertices.remove(v);
        this.nodesMap.remove(key);
        if (this.edges.containsKey(key)) {
            HashMap<Integer,Double> neighbors = this.edges.get(key);
            for (Integer u : neighbors.keySet()) {
                this.edges.get(u).remove(key);
                this.edgesSize--;
            }
        }
        return v;
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
        if (!this.vertices.contains(this.getNode(node1)) || !this.vertices.contains(this.getNode(node2))) return;
        if (!this.hasEdge(node1, node2)) return;

        this.edges.get(node1).remove(node2);
        this.edges.get(node2).remove(node1);
        this.edgesSize--;
        this.modeCount++;

    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.vertices.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edgesSize;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.modeCount;
    }

    class node_data implements node_info, Serializable{
        private int key;
        private double tag = 0;
        private String nodeData ="";

        public node_data(int key){
            this.key = key;
        }
        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.nodeData;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.nodeData= s;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            node_data node_data = (node_data) o;
            return key == node_data.key &&
                    Double.compare(node_data.tag, tag) == 0 &&
                    Objects.equals(nodeData, node_data.nodeData);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, tag, nodeData);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return modeCount == wGraph_ds.modeCount &&
                edgesSize == wGraph_ds.edgesSize &&
                Objects.equals(nodesMap, wGraph_ds.nodesMap) &&
                Objects.equals(vertices, wGraph_ds.vertices) &&
                Objects.equals(edges, wGraph_ds.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodesMap, vertices, edges, modeCount, edgesSize);
    }
}
