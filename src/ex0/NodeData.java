package ex0;


import java.util.Collection;
import java.util.HashMap;

/**
 * represents the set of operations applicable on a node (vertex) in an (undirectional) unweighted graph.
 */
public class NodeData implements node_data{
    public static int counter=0;//A public counter that provides unique keys
    private int key;//unique key for each node
    private int tag;//tag value for temporal marking an node
    private String info;//remark (meta data) associated with this node
    private HashMap<Integer,node_data> ni;//hash-Map structure for storage neighbors of node_data

    /**
     * constructor for NodeData class
     */
    public NodeData()
    {
        this.key=counter;
        this.ni=new HashMap<Integer, node_data>();
        counter++;
    }
    /**
     * the method return the node's key
     * @return key
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     *the method return collection with all the neighbors of this.node
     * @return collection contains neighbors of this.node
     */
    @Override
    public Collection<node_data> getNi() {

        return this.ni.values();
    }

    /**
     * the method get key and return true if node-key is neighbor of this.node
     * @param key
     * @return true if node-key is neighbor of this.node else return true
     */
    @Override
    public boolean hasNi(int key) {
        boolean flag=false;
            if(this.ni.containsKey(key)) //check if the key is exist in the hash-Map that structure of the neighbors
            {
                flag=true;
            }

        return flag;
    }

    /**
     * the method add t node to the list of the neighbors of this.node
     * @param t
     */
    @Override
    public void addNi(node_data t) {
        if(this.key!=t.getKey()) {
            this.ni.put(t.getKey(), t);
        }
    }

    /**
     * the method get node and remove it from neighbors list
     * @param node
     */
    @Override
    public void removeNode(node_data node) {
            if (this.ni.containsKey(node.getKey()))
                this.getNi().remove(node);
    }

    /**
     * the method return this.info
     * @return info
     */
    @Override
    public String getInfo() {

        return this.info;
    }

    /**
     * the method set info of this.node
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    /**
     * the method return this.tag
     * @return tag
     */
    @Override
    public int getTag() {

        return this.tag;
    }

    /**
     * the method set tag of node
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;
    }

}
