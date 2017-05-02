import java.util.*;


/**
 * CS375: Project 5
 * RandomGraphGenerator and GraphNode
 * Monday, May 1, 2017
 * Jacob Adamson, He He, Josh Hew, Larry Patrizio
 */

/**
 * This class is responsible for generating a graph with n nodes and m edges (the edges
 * are randomly created and are made sure to be unique).
 */
public class RandomGraphGenerator {

    int numNodes;
    boolean isConnected;
    int numEdges;

    HashMap<Integer, GraphNode> currentGraph;

    /**
     * Constructor method for this class, not responsible for much
     */
    public RandomGraphGenerator() {
        isConnected = false;
        currentGraph = new HashMap<>();

    }

    /**
     * Most critical method for the class. It is responsible for triggering all of
     * this class's functionality. I do comments throughout this method to make clear
     * what is happening. In summary, generateGraph() takes in n nodes and m numEdges
     * and then determines if the internally generated graph is connected or not.
     * @param numNodes - number of nodes in the graph
     * @param numEdges - number of edges to be in the graph
     * @return - boolean indicating if the created graph is connected
     */
    public HashMap<Integer, GraphNode> generateGraph(int numNodes, int numEdges) {
        this.numNodes = numNodes;
        //Minor fail safe incase an impossible amount of numEdges is given
        if (numEdges <= (numNodes*(numNodes-1))/2)
            this.numEdges = numEdges;
        else
            this.numEdges = (numNodes*(numNodes-1))/2;

        //Generates the nodes, without any connections. Each node is assigned a
        //nodeID number for reference within the HashMap<Integer, GraphNode>
        for(int i = 0; i<numNodes; i++){
            GraphNode node = new GraphNode(i+1);
            currentGraph.put(node.getNodeID(), node);
        }

        ArrayList<ArrayList<Integer>> allEdges = getAllEdges();
        //randomly removes edges from the allEdges ArrayList. For example, if numNodes
        //is 5 and numEdges is 9, it will remove only 1 edge from allEdges.
        int maxPossibleEdges = (numNodes*(numNodes-1))/2;
        for(int i = 0; i < maxPossibleEdges-numEdges; i++){
            Collections.shuffle(allEdges);//uses Collections shuffle() to randomize
            allEdges.remove(allEdges.get(2));
        }
        //using the resulting proper edge arrayList, goes to each of those nodes
        //and establishes a connection
        allEdges.forEach(edge -> {
            GraphNode nodeOne = currentGraph.get(edge.get(0));
            GraphNode nodeTwo = currentGraph.get(edge.get(1));
            nodeOne.setEdge(edge.get(1), nodeTwo);
            nodeTwo.setEdge(edge.get(0), nodeOne);
        });

        //starts the recursively connectivity checking method
        return currentGraph;

    }

    /**
     * Correctly gets a list of unique edge combinations. For example, [1,2] is the
     * same edge as [2,1], therefore only one will be present in the returned
     * ArrayList
     * @return - ArrayList of unique edges
     */
    private ArrayList<ArrayList<Integer>> getAllEdges(){
        ArrayList<Integer> allIDs = new ArrayList<>();
        currentGraph.keySet().forEach(i->{
            allIDs.add(i);
        });
        ArrayList<ArrayList<Integer>> allEdges = new ArrayList<>();
        for (int i = 0; i < allIDs.size();i++){
            for(int j = i+1; j < allIDs.size(); j++){
                ArrayList<Integer> edge = new ArrayList<>();
                edge.add(allIDs.get(i));
                edge.add(allIDs.get(j));
                allEdges.add(edge);
            }
        }
        return allEdges;
    }
}


/**
 * GraphNode object that simply has a nice toString() method for testing, basic methods,
 * and a HashMap<Integer, GraphNode> to establish connects to other GraphNodes
 */
class GraphNode {

    int nodeID;
    String color;

    HashMap<Integer, GraphNode> edges;

    public GraphNode(int nodeID){
        this.edges = new HashMap<>();
        this.nodeID = nodeID;
        this.color = "No_Color";
    }

    public int getNodeID(){
        return this.nodeID;
    }

    public int getNumEdges(){return this.edges.size();}

    public String getColor(){return this.color;}

    public void setColor(String color){this.color = color;}

    public String toString(){
        return ("Node ID: "+getNodeID()+" | has "+edges.size()+" edges to: " + this.edges.keySet() + " | " + this.color);
    }

    public boolean areNeighborsAlreadyColored(String color){
        for (GraphNode neighbor : this.edges.values()) {
            if(neighbor.getColor().equals(color))
                return true;
        }
        return false;
    }

    public void setEdge(int nodeID, GraphNode node){
        edges.putIfAbsent(nodeID,node);
    }

}
