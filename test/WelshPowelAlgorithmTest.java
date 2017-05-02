/**
 * CS375: Project 5
 * WelshPowelAlgorithm
 * Monday, May 1, 2017
 * Jacob Adamson, He He, Josh Hew, Larry Patrizio
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * J-Unit class that uses RandomGraphGenerator to find the chromatic number of a graph
 * using our implementation of the WelshPowelAlgorithm
 */
public class WelshPowelAlgorithmTest {

    RandomGraphGenerator randomGraphGenerator = new RandomGraphGenerator();

    ArrayList<GraphNode> vertexes = new ArrayList<>();
    ArrayList<GraphNode> vertexesCopy;
    int currentColor = 0;
    String[] colors = {"Red","Blue","Green","Yellow","Purple","Pink","Orange"};
    @Test
    /**
     * Runs the WelshPowel Algorithm on a randomly generated graph
     */
    public void test() throws Exception{
        //Generates a random graph with n number of nodes and m number of edges
        int n = 10;
        int m = 7;


        randomGraphGenerator.generateGraph(n,m).forEach((integer, graphNode) ->
        {
            vertexes.add(graphNode);
        });
        this.vertexesCopy = (ArrayList<GraphNode>) vertexes.clone();
        welshPowelAlgorithm();
        this.vertexesCopy.forEach(graphNode -> System.out.println(graphNode));
        System.out.println("\nThe graph's chromatic number is: " + currentColor);
    }

    private void welshPowelAlgorithm(){
        //(1) Find the valence for each vertex.

        //Handled by the GraphNode.getNumEdges() method

        //(2) List the vertices in order of largest to smallest valence.
        Collections.sort(vertexes, new Comparator<GraphNode>() {
            @Override
            public int compare(GraphNode node1, GraphNode node2) {
                return -(node1.getNumEdges()-node2.getNumEdges());
            }
        });

        //(3) Color the first vertex in the list (the vertex with the highest valence) with color1 1.
        //(4) Go down the list and color every vertex not connected to the colored vertices
        // above the same color. Then cross out all colored vertices in the list.
        //(5) Repeat the process on the uncolored vertices with a new color - always
        // working in descending order of valence until all the vertices have been colored.
        welshPowelAlgorithmRecursive();
    }

    private void welshPowelAlgorithmRecursive() {
        //Color the first vertex in the list (the vertex with the highest valence) with color1 1.
        vertexes.get(0).setColor(colors[currentColor]);

        //Go down the list and color every vertex not connected to the colored vertices
        // above the same color. Then cross out all colored vertices in the list.
        ArrayList<GraphNode> verticesToRemove = new ArrayList<>();
        vertexes.remove(0);
        vertexes.forEach(graphNode -> {
            if(!graphNode.areNeighborsAlreadyColored(colors[currentColor])) {
                graphNode.setColor(colors[currentColor]);
                verticesToRemove.add(graphNode);
            }
        });
        currentColor++;
        vertexes.removeAll(verticesToRemove);

        //enter recursion again if any vertexes remain
        if(vertexes.size()>0)
            welshPowelAlgorithmRecursive();
    }
}
