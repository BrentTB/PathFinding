/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PathFindingAlgorithm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author Brent
 */
public class PathFindingAlgorithm {

    static int numPaths = 10;
//    static int numPaths = 9;

    public static void main(String[] args) throws IOException {
        //make a path finding algorithm that given a set of 1 way / 2 way paths, will get you from a start point to an end point
        PathFindingAlgorithm temp = new PathFindingAlgorithm();
//        temp.start("a", "b");//go from a to b
        temp.start("1", "628");

    }

    public void setUpFile() throws IOException {//used to add distances to a list of nodes seporated by a space ina  txt file named "nodes.txt"

        Scanner read = new Scanner(new FileReader("nodes.txt"));
        StringBuilder build = new StringBuilder();
        while (read.hasNextLine()) {
            Scanner read1 = new Scanner(read.nextLine()).useDelimiter(" ");
            int rand = (int) Math.ceil(Math.random() * 1000 + 1);
            build.append(read1.next()).append(" ").append(read1.next()).append(" ").append(rand).append("\n");
            read1.close();
        }
        read.close();

        PrintWriter write = new PrintWriter(new FileWriter("nodes.txt", false));
        write.append(build);

    }

    public void start(String start, String end) throws FileNotFoundException {

        HashMap paths = new HashMap(); // takes a start +""+ end point and returns the distance
        ArrayList<String> nodes = new ArrayList<>(); // stores the names of all the nodes

        graph[] verticies = putVertcicies();//stores all paths and their distances
        //   System.out.println(numPaths);
        putHashMap(paths, verticies);
        int[] endNode = getnodes(verticies, nodes, end, start);

        int[] distFromStart = new int[nodes.size()];
        String[] path = new String[nodes.size()];
        for (int i = 0; i < distFromStart.length; i++) {
            distFromStart[i] = 0;
        }
        
        
        
//        getPathNormal(start, nodes, paths, distFromStart, path, 0, start);
        getPathDjakstra(endNode[0], nodes, paths, distFromStart, path);

        System.out.println(distFromStart[endNode[1]] + "\n" + path[endNode[1]]);
    }

    public graph[] putVertcicies() throws FileNotFoundException {//save all distances and nodes. all distances must be >0

numPaths=0;
        Scanner read = new Scanner(new FileReader("nodes.txt"));
       ArrayList<String> temp = new ArrayList<>();
        while(read.hasNextLine()){
        temp.add(read.nextLine());
        }
        numPaths = temp.size();
        graph[] vert = new graph[numPaths];
        for (int i = 0; i < numPaths; i++) {
            String[] temp2 = temp.get(i).split(" ");
         vert[i] = new graph(temp2[0], temp2[1], Integer.parseInt(temp2[2]), false);
        }
        
        
        
        
        
//        numPaths = 10;
//        graph[] vert = new graph[numPaths];
//        vert[0] = new graph("f", "a", 40, true);
//        vert[1] = new graph("a", "e", 14, false);
//        vert[2] = new graph("c", "e", 12, true);
//        vert[3] = new graph("b", "f", 9, false);
//        vert[4] = new graph("d", "i", 15, true);
//        vert[5] = new graph("g", "a", 6, false);
//        vert[6] = new graph("g", "f", 20, true);
//        vert[7] = new graph("h", "b", 12, true);
//        vert[8] = new graph("a", "h", 17, false);
//        vert[9] = new graph("a", "i", 2, true);//shortest a - b is: ahb 29

        
        
        
//        numPaths=9;
//        graph[] vert = new graph[numPaths];
//        vert[0]= new graph("a","b",7,false);
//        vert[1]= new graph("a","c",9,false);
//        vert[2]= new graph("a","f",14,false);
//        vert[3]= new graph("b","c",10,false);
//        vert[4]= new graph("b","d",15,false);
//        vert[5]= new graph("c","d",11,false);
//        vert[6]= new graph("c","f",2,false);
//        vert[7]= new graph("d","e",6,false);
//        vert[8]= new graph("e","f",9,false);
        return vert;
    }

    public static void putHashMap(HashMap hash, graph[] vert) {
        for (int i = 0; i < numPaths; i++) {
            hash.put(vert[i].getP1() + "" + vert[i].getP2(), vert[i].getDistance());

            if (vert[i].isTwoWay()) {
                hash.put(vert[i].getP2() + "" + vert[i].getP1(), vert[i].getDistance());

            }

        }

    }//gets all the node distances in a hashmap

    public int[] getnodes(graph[] vert, ArrayList nodes, String end, String start) {
        int[] endNode = new int[2];
        int startnode;
        for (int i = 0; i < numPaths; i++) {
            if (!nodes.contains(vert[i].getP1())) {
                nodes.add(vert[i].getP1());
                if (vert[i].getP1().equals(end)) {
                    endNode[1] = nodes.size() - 1;//the node of the end destrination is the last one added to the ArrayList
                }
                if (vert[i].getP1().equals(start)) {
                    endNode[0] = nodes.size() - 1;//the node of the end destrination is the last one added to the ArrayList
                }

            }
            if (!nodes.contains(vert[i].getP2())) {
                nodes.add(vert[i].getP2());
                if (vert[i].getP2().equals(end)) {
                    endNode[1] = nodes.size() - 1;
                }
                if (vert[i].getP2().equals(start)) {
                    endNode[0] = nodes.size() - 1;//the node of the end destrination is the last one added to the ArrayList
                }
            }

        }
        return endNode;
    }//gets all the nodes in an arraylist and returns the number of the final node

    public class graph {

        private String p1, p2;
        private int distance;
        private boolean twoWay;

        public graph(String p1, String p2, int distance, boolean twoWay) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = distance;
            this.twoWay = twoWay;
        }

        public String getP1() {
            return p1;
        }

        public String getP2() {
            return p2;
        }

        public int getDistance() {
            return distance;
        }

        public boolean isTwoWay() {
            return twoWay;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

    }

    public void getPathNormal(String nodeOn, ArrayList nodes, HashMap paths, int[] distFromStart, String[] pathFromStart, int dist, String path) {

        for (int i = 0; i < nodes.size(); i++) {
            if (!path.contains("" + nodes.get(i))) {
                if (paths.get(nodeOn + "" + nodes.get(i)) != null) {//if there is a path between the node we are on and the next node
                    dist += Integer.parseInt("" + paths.get(nodeOn + "" + nodes.get(i)));//add that distance to the tally
                    path += "->" + nodes.get(i);

                    if (dist < distFromStart[i] || distFromStart[i] == 0) {//see if the new dist is smaller than the one we have saved
                        distFromStart[i] = dist;
                        pathFromStart[i] = path;
                    }
                    getPathNormal("" + nodes.get(i), nodes, paths, distFromStart, pathFromStart, dist, path);
                    dist -= Integer.parseInt("" + paths.get(nodeOn + "" + nodes.get(i)));
                    path = path.substring(0, path.length() - 3);
                }
            }

        }

    }

    public void getPathDjakstra(int nodeOn, ArrayList nodes, HashMap paths, int[] distFromStart, String[] pathFromStart) {//try to optimise?
        boolean[] checked = new boolean[nodes.size()];
        boolean[] needToBeChecked=new boolean[nodes.size()];
        pathFromStart[nodeOn] = "" + nodes.get(nodeOn);  
        int nodeNum=nodeOn;
        while (nodeNum != -1) {

            for (int i = 0; i < nodes.size(); i++) {

                if (paths.get(nodes.get(nodeOn) + "" + nodes.get(i)) != null && !checked[i]) {
                    int path = Integer.parseInt("" + paths.get(nodes.get(nodeOn) + "" + nodes.get(i)));
                    needToBeChecked[i]=true;
                    if (distFromStart[i] > path || distFromStart[i] == 0) {
                        distFromStart[i] = distFromStart[nodeOn] +path;
                        pathFromStart[i] = pathFromStart[nodeOn] + "->" + nodes.get(i);
                    }

                }

            }
            checked[nodeOn] = true;//the int of the start num
            needToBeChecked[nodeOn]=false;
            
            
        
            
            
            int min=-1;
             nodeNum=-1;
            for (int i = 0; i < nodes.size(); i++) {
                if(needToBeChecked[i] && (distFromStart[i]<min || min==-1 )){
                min=distFromStart[i];
                nodeNum=i;
                }
                
            }
            
            
            
            
            nodeOn = nodeNum;
                  
        }
    }

}
