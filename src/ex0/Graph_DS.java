package ex0;

import java.util.*;

/**
 * this class represents an undirectional unweighted graph
 */
public class Graph_DS implements graph{

    private HashMap<Integer,node_data> graph;//Hash-Map structure represents the graph
    private int MC;//mode counter-count number of changes on graph
    private int edges;//number of edges in graph
    private int nodeSize;//number of nodes in graph

    /**
     * constructor for Graph_DS class
     */
    public Graph_DS()
    {
        this.graph=new HashMap<Integer, node_data>();
        this.MC=0;
        this.edges=0;
        this.nodeSize=0;
    }

    /**
     * copy-constructor for Graph_DS class
     * @param g
     */

    public Graph_DS (graph g)
    {
        this.MC=g.getMC();
        this.edges=g.edgeSize();
        this.nodeSize=g.nodeSize();
        this.graph=new HashMap<Integer, node_data>();
        Iterator<node_data> it=g.getV().iterator();
        while(it.hasNext())//loop the nodes in graph g and copy the nodes to the new copy object with unique keys
        {
            node_data current=it.next();
            int currentKey=current.getKey();
            this.graph.put(currentKey,current);
        }
    }

    /**
     * the method return node by giving key if the key not found return null
     * @param key - the node_id
     * @return node
     */
    @Override
    public node_data getNode(int key) {
        if(this.graph.containsKey(key))
        {
            return this.graph.get(key);//using the method get of Hash-Map Structure-give in O(1)
        }

        return null;
    }

    /**
     * the method return true if there is edge between node1 and node2 else return false
     * @param node1
     * @param node2
     * @return true if the edge exist else false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        boolean flag=true;
        NodeData n1= (NodeData) getNode(node1);//using the getNode method to get node1 by its key and cast it from node_data type
        //to object from type NodeData in order to use the implementations that NodeData class has
        NodeData n2= (NodeData) getNode(node2);
        if(n1!=null && n2!=null) {
            if (!n1.hasNi(n2.getKey()) || !n2.hasNi(n1.getKey())) //check if node1 isnt neighbor or node2 and vice versa(take O(1))
            {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * the method add node to this.graph
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        this.graph.put(n.getKey(),(NodeData) n);//using the put function of HashMap to insert the node in O(1)
        this.MC++;
        this.nodeSize++;
    }

    /**
     * the method create an edge between node1 and node2 and if there is an edge its do nothing
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2) {
        if ((node1==node2)) return;
        NodeData n1=(NodeData) (this.graph.get(node1));//make an NodeData object
        NodeData n2=(NodeData) (this.graph.get(node2));
        if(n1!=null && n2!=null) {
            if (!hasEdge(node1, node2)) {//check if the nodes are not neighbors
                n1.addNi(n2);//make n2 to neighbor of n1
                n2.addNi(n1);
                this.MC++;
                this.edges++;
            }
        }
    }

    /**
     * the method return a shallow copy of the graph
     * @return shallow copy
     */
    @Override
    public Collection<node_data> getV() {
        Collection<node_data> shallowCopy=this.graph.values();
        return shallowCopy;
    }

    /**
     * This method return a collection representing all the nodes connected to node_id
     * @param node_id
     * @return collection representing all the nodes connected to node_id
     */
    @Override
    public Collection<node_data> getV(int node_id) {
        NodeData n1=(NodeData)(getNode(node_id));//get the object node by node_id
        return n1.getNi();//using the function return the list of neighbors in O(1)
    }

    /**
     *the method remove a node from the graph
     * @param key
     * @return removal node
     */
    @Override
    public node_data removeNode(int key) {
        node_data removalNode=(getNode(key));//get the node by its key
        NodeData removalNodeObj = (NodeData) removalNode;//casting to NodeData object
        if(removalNode!=null) {
            Iterator<node_data> iterator = removalNodeObj.getNi().iterator();//create an iterator for loop on the neighbors list of the removal node
            while (iterator.hasNext()) {
                NodeData current=(NodeData)iterator.next();
               current.removeNode(removalNode);//remove the removal node from the neighbor list of each neighbor of the removal node in O(1)
            }
            this.edges -= removalNodeObj.getNi().size();//update the number of edges in the graph after the remove
            this.MC++;
            this.nodeSize--;
            this.graph.remove(key,removalNode);
        }
        return removalNode;
    }

    /**
     * the method remove the edge between node1 and node2
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        NodeData n1=(NodeData) getNode(node1);//get node1
        NodeData n2=(NodeData) getNode(node2);//get node2
        if(n1!=null && n2!=null) {
            if (hasEdge(node1, node2)) {
                n1.removeNode(n2);//remove node2 from the neighbors list of n1
                n2.removeNode(n1);//remove node1 from the neighbors list of n2
                this.MC++;
                this.edges--;
            }
        }
    }

    /**
     * the method return the num of the nodes in the graph
     * @return graph size
     */
    @Override
    public int nodeSize() {
        return this.graph.size();
    }

    /**
     *the method return num of edges in the graph
     * @return num of edges
     */
    @Override
    public int edgeSize() {
        return this.edges;
    }
    /**
     *the method return num of changing on the graph
     * @return MC
     */
    @Override
    public int getMC() {
        return this.MC;
    }
    

}
