package ex0;

import java.util.*;

public class Graph_Algo implements graph_algorithms{
    private graph ga;//represent graph

    public Graph_Algo()
    {
    }
    /**
     *this method init the graph to be ready for algorithms
     * @param g
     */
    @Override
    public void init(graph g) {
        this.ga=g;
    }

    /**
     * the method return a deep copy of the graph
     * @return deep copy of graph
     */
    @Override
    public graph copy() {
        graph copy=new Graph_DS(this.ga);//create the copy graph via copy constructor
        return copy;
    }

    /**
     * the method initialize all the info fields to be null
     */
    private void initializeInfo()
    {
        Iterator<node_data> it=this.ga.getV().iterator();
        while (it.hasNext())
        {
            it.next().setInfo(null);
        }
    }
    /**
     * the method returns true iff there is a valid path from EVREY node to each
     *      * other node.
     * @return true if the graph is linked else return false
     */
    @Override
    public boolean isConnected() { //i got help from the site https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
        if(this.ga.edgeSize()<this.ga.nodeSize()-1) return false;
        initializeInfo();//initialize all the info fields to be null for the algorithm to work
        if(this.ga.nodeSize()==0||this.ga.nodeSize()==1) return true;//if there is not node or one its connected
        Graph_DS copy = (Graph_DS) (copy());//create a copy graph that the algorithm will on it
        LinkedList<node_data> qValues = new LinkedList<>();//create linked list that will storage all nodes that we didn't visit yet
        int firstNodeKey = copy.getV().iterator().next().getKey();//first key for get the first node(its doesnt matter which node
        node_data first = copy.getNode(firstNodeKey);//get the node
        qValues.add(first);//without limiting generality taking the last node added to graph
        int counterVisitedNodes = 0;//counter the times we change info of node to "visited"
        while (qValues.size() != 0) {
            node_data current = qValues.removeFirst();
            if (current.getInfo() != null) continue;//if we visit we can skip to the next loop because we have already marked
            current.setInfo("visited");//remark the info
            counterVisitedNodes++;

            Collection<node_data> listNeighbors = copy.getV(current.getKey());//create a collection for the neighbors list
            LinkedList<node_data> Neighbors = new LinkedList<>(listNeighbors);//create the neighbors list
            if (Neighbors == null) continue;
            for (node_data n : Neighbors) {
                if (n.getInfo() == null) {//if there is a node we didn't visited it yet, we will insert it to the linkedList
                    qValues.add(n);
                }
            }
        }
        if (this.ga.nodeSize() != counterVisitedNodes) return false;//check that we visited all of the nodes

        return true;
    }


    /**
     *the method initialize all the node_tag to 0
     */
    private void initializeTag()
    {
        Iterator<node_data> it=this.ga.getV().iterator();
        while(it.hasNext())
        {
            it.next().setTag(-1);
        }
    }

    /**
     *the method compute the distance of the shortest path from src to dest in the graph
     * @param src - start node
     * @param dest - end (target) node
     * @return distance of shortest path src-->dest
     */
    @Override
    public int shortestPathDist(int src, int dest) {
        if(shortestPath(src,dest)==null) return -1;
        return (shortestPath(src,dest).size()-1);
    }

    /**
     *the method return a list represent the path from source to destination in the graph
     * @param src - start node
     * @param dest - end (target) node
     * @return List<node_data>
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        //case-no path
        if(!isConnectedHelp(src,dest)) return null;
        List<node_data> path =new ArrayList<node_data>();
        //case-src is dest
        if(src==dest)
        {
            node_data n=this.ga.getNode(src);
            path.add(0,n);
            return path;
        }
        //the other case
        ArrayList<node_data> arrPath=bfsHelp(src,dest);//get the nodes that the algorithm remark "visited" to arrayList
        ArrayList<node_data> arrPathUpdate=new ArrayList<node_data>();//the arrPath after we will delete the un-wanted nodes to the shortest path
        int minTag=Integer.MAX_VALUE;//minTag
        node_data nodeToInsert= arrPath.get(arrPath.size()-1);//save the node to insert to arrPathUpdate each loop
        for(int i = arrPath.size()-1; i>=0; i--)//run on the arrPath
        {
            if(nodeToInsert.getKey()==src)//we finished!
            {
                arrPathUpdate.add(nodeToInsert);//inserting source node to the arrPathUpdate
                break;//exit the for
            }
            arrPathUpdate.add(nodeToInsert);//inserting the wanted node that passed all the test to be in the shortest way
            List<node_data> ni = new ArrayList<node_data>(nodeToInsert.getNi());//get all neighbors of the element that was inserted by BFS algorithm
            for(int j=i-1;j>=0;j--)//this loop finding the next appropriate node to the updatePath
            {

                if(ni.contains(arrPath.get(j)))
                {
                    if (arrPath.get(j).getTag() < minTag) {//cheking minimum tag
                        minTag = arrPath.get(j).getTag();
                        nodeToInsert = arrPath.get(j);//update the appropriate node that will insert to the update-path
                    }
                }
            }
        }
        for(int i=arrPathUpdate.size()-1;i>=0;i--)
        {
            path.add(arrPathUpdate.get(i));//create the list in reverse because in the first we did the algorithm in reverse
        }
        return path;
    }

    /**
     * the method return an array list with all the nodes that Bfs algorithm pass from src to des
     * @param src
     * @param dest
     * @return
     */
    private ArrayList<node_data> bfsHelp(int src,int dest)
    {
        int index=0;//for the arrayList
        ArrayList<node_data> arrPath=new ArrayList();
        int counterTag=0;
        initializeTag();
        initializeInfo();//initialize all the info fields
        Graph_DS copy = (Graph_DS) (copy());//create a copy graph that the algorithm will on it
        LinkedList<node_data> qValues = new LinkedList<>();//create linked list that will storage all nodes that we didn't visit yet
        node_data first = copy.getNode(src);//get the node
        qValues.add(first);
        while (qValues.size() != 0) {
            node_data current = qValues.removeFirst();
            arrPath.add(index,current);//insert the nodes who visited until dest
            index++;//increase the index
            if (current.getInfo() != null) continue;//if we visit we can skip to the next loop because we have already marked
            current.setTag(counterTag);
            current.setInfo("visited");//remark the info
            if(current.getKey()==dest) return arrPath;//src is connected to dest
            Collection<node_data> listNeighbors = copy.getV(current.getKey());//create a collection for the neighbors list
            LinkedList<node_data> Neighbors = new LinkedList<>(listNeighbors);//create the neighbors list
            if (Neighbors == null) continue;
            for (node_data n : Neighbors) {
                if (n.getInfo() == null) {//if there is a node we didn't visited yet, we will insert it to the linkedList
                    counterTag++;//increase the tag
                    n.setTag(counterTag);//update the tag
                    qValues.add(n);
                }
            }
        }
        return arrPath;
    }


    /**
     * the method return true if there is path from src to dest
     * @param src
     * @return if exist path src-->des
     */

    private boolean isConnectedHelp(int src,int dest)
    {
        initializeInfo();//initialize all the info fields
        Graph_DS copy = (Graph_DS) (copy());//create a copy graph that the algorithm will on it
        LinkedList<node_data> qValues = new LinkedList<>();//create linked list that will storage all nodes that we didn't visit yet
        node_data first = copy.getNode(src);//get the node
        qValues.add(first);
        while (qValues.size() != 0) {
            node_data current = qValues.removeFirst();
            if(current.getKey()==dest) return true;//src is connected to dest
            if (current.getInfo() != null) continue;//if we visit we can skip to the next loop because we have already marked
            current.setInfo("visited");//remark the info
            Collection<node_data> listNeighbors = copy.getV(current.getKey());//create a collection for the neighbors list
            LinkedList<node_data> Neighbors = new LinkedList<>(listNeighbors);//create the neighbors list
            if (Neighbors == null) continue;
            for (node_data n : Neighbors) {
                if (n.getInfo() == null) {//if there is a node we didn't visited yet, we will insert it to the linkedList
                    qValues.add(n);
                }
            }
        }
        return false;
    }
}
