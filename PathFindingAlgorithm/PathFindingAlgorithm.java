/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PathFindingAlgorithm;

import java.io.*;
import java.util.*;

/**
 *
 * @author Brent
 */
public class PathFindingAlgorithm {

    static int numPaths;

    public static void main(String[] args) throws IOException {
        //make a path finding algorithm that given a set of 1 way / 2 way paths, will get you from a start point to an end point
        PathFindingAlgorithm temp = new PathFindingAlgorithm();
//        temp.start("a", "g");//go from a to g
        temp.start("1", "639");

    }

    public void setUpFile() throws IOException {//used to add distances to a list of nodes seporated by a space in a txt file named "nodes.txt"

        
        String TXTFile="nodes.txt";
        
        
        Scanner read = new Scanner(new FileReader(TXTFile));
        StringBuilder build = new StringBuilder();
        while (read.hasNextLine()) {
            Scanner read1 = new Scanner(read.nextLine()).useDelimiter(" ");
            int rand = (int) Math.ceil(Math.random() * 1000);//all path lengths are between 1 and 1000 
            build.append(read1.next()).append(" ").append(read1.next()).append(" ").append(rand).append("\n");
            read1.close();
        }
        read.close();

        PrintWriter write = new PrintWriter(new FileWriter(TXTFile, false));
        write.append(build);
write.close();
    }

    public void start(String start, String end) throws FileNotFoundException {

        HashMap paths = new HashMap(); // takes a start +""+ end point and returns the distance between them
        ArrayList<String> nodes = new ArrayList<>(); // stores the names of all the nodes

        graph[] verticies = putVertcicies();// stores all paths and their distances
        putHashMap(paths, verticies); // setting up the hashmap
        int[] endNode = getnodes(verticies, nodes, end, start);//setting up the nodes, and getting the array index of the start and end node

        int[] distFromStart = new int[nodes.size()];
        String[] path = new String[nodes.size()];
        for (int i = 0; i < distFromStart.length; i++) {
            distFromStart[i] = 0;
        }
        
//        getPathNormal(start, nodes, paths, distFromStart, path, 0, start);//un commenting one of these will decide which algorithm to use
        getPathDjakstra(endNode[0], nodes, paths, distFromStart, path);

        System.out.println(distFromStart[endNode[1]] + "\n" + path[endNode[1]]);//print out the distance from the start to end and the path
        
        
        
        
        
        
        int max=0;
        int iNum=0;
        for (int i = 0; i < numPaths; i++) {
            if(verticies[i].getDistance()>max){
            max = verticies[i].getDistance();
            iNum=i;
            }
        }
        
            System.out.println(verticies[iNum].getDistance());
        
        
        
        
        
        
    }

    public graph[] putVertcicies() throws FileNotFoundException {//save all distances and nodes. all distances must be >0

numPaths=0;//read the nodes and paths from a text file


        String TXTFile="nodes.txt";
        Scanner read = new Scanner(new FileReader(TXTFile));
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
        
        
        
        //2 sets of nodes and distances to use as a test
        
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
        for (int i = 0; i < numPaths; i++) {
            if (!nodes.contains(vert[i].getP1())) {
                nodes.add(vert[i].getP1());
                if (vert[i].getP1().equals(start)) {
                    endNode[0] = nodes.size() - 1;//the node of the start destination is the last one added to the ArrayList at this point
                }
                if (vert[i].getP1().equals(end)) {
                    endNode[1] = nodes.size() - 1;//the node of the end destination is the last one added to the ArrayList at this point
                }

            }
            if (!nodes.contains(vert[i].getP2())) {
                nodes.add(vert[i].getP2());
                if (vert[i].getP2().equals(start)) {
                    endNode[0] = nodes.size() - 1;//the node of the start destination is the last one added to the ArrayList at this point
                }
                if (vert[i].getP2().equals(end)) {
                    endNode[1] = nodes.size() - 1;//the node of the end destination is the last one added to the ArrayList at this point
                }
            }

        }
        return endNode;
    }//gets all the nodes in an arraylist and returns the number of the final node

    public class graph {

        private final String p1, p2;
        private int distance;
        private final boolean twoWay;

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

        public void setDistance(int distance) {//only used when setting up a TXT file
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
                    getPathNormal("" + nodes.get(i), nodes, paths, distFromStart, pathFromStart, dist, path);//check the distaces from this new node to all other nodes
                    dist -= Integer.parseInt("" + paths.get(nodeOn + "" + nodes.get(i)));//undo any changes that happened in the recursive function
                    path = path.substring(0, path.length() - 3);
                }
            }

        }

    }

    public void getPathDjakstra(int nodeOn, ArrayList nodes, HashMap paths, int[] distFromStart, String[] pathFromStart) {//try to optimise / reduce code
        boolean[] checked = new boolean[nodes.size()];//all the nodes that have been checked (all paths from that node have been covered)
        boolean[] needToBeChecked=new boolean[nodes.size()];//all the nodes that need to be checked
        pathFromStart[nodeOn] = "" + nodes.get(nodeOn);
        int nodeNum=nodeOn;//nodeNum is used to find the next node to check, at the beginning it must be the starting node
        while (nodeNum != -1) {//while there are more nodes to be checked

            for (int i = 0; i < nodes.size(); i++) {//check for each node

                if (paths.get(nodes.get(nodeOn) + "" + nodes.get(i)) != null && !checked[i]) {//if there is a path between the node were on and the node given by the loop
                    int path = Integer.parseInt("" + paths.get(nodes.get(nodeOn) + "" + nodes.get(i)));
                    needToBeChecked[i]=true;
                    if (distFromStart[i] > path || distFromStart[i] == 0) {//if the new node has no distance ( == 0) or the distance is bigger than our current path
                        distFromStart[i] = distFromStart[nodeOn] +path;//give the new node the current nodes distance plus the distance between them
                        pathFromStart[i] = pathFromStart[nodeOn] + "->" + nodes.get(i);//the same with the path
                    }

                }

            }
            checked[nodeOn] = true;//the current node has now been checked
            needToBeChecked[nodeOn]=false;//and it doesn't need to be checked 
            
            
            int min=-1;
             nodeNum=-1;//set as -1 to see when there are no more nodes
            for (int i = 0; i < nodes.size(); i++) {//find the node with the smallest distance, of all nodes that must be checked
                if(needToBeChecked[i] && (distFromStart[i]<min || min==-1 )){
                min=distFromStart[i];
                nodeNum=i;
                }
                
            }
            
            
            
            
            nodeOn = nodeNum;
                  
        }
    }

}
