import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import bridges.connect.Bridges;
import bridges.base.SLelement;

//import edu.uncc.cs.bridgesV2.base.Edge;
//import edu.uncc.cs.bridgesV2.base.GraphList;
//import edu.uncc.cs.bridgesV2.base.SLelement;
//import edu.uncc.cs.bridgesV2.connect.Bridges;



public class Driver423 {

	static int numOfEdges = 0;
	static int numOfVertices = 0;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub


		Bridges<String> bridges = new Bridges<String>(7, "509600458590", "mwill320");

		// read in file
		BufferedReader data = new BufferedReader(new FileReader(new File("data.txt")));
		String line;
		// read the first line
		line = data.readLine();
		GraphList<String> gl3 = new GraphList<String>();

		HashMap<String, GraphList<String>> graph_al = 
				new HashMap<String, GraphList<String> >();


		//		MySLelement<String> prevVertex = new MySLelement<String>("source","source");
		MySLelement<String> prevVertex = null;
		HashMap <String,String> pathMap = new HashMap<String, String>();
		// total number of lines read for fast visualization
		int limit = 550;

		// loop through file until there are no more lines of text 
		// OR until a certain amount (limit) of lines are read
		while(line != null && limit > 0){
			// split the data into actor name and movie name
			String [] dataLine = line.split(" ");
			String actor = dataLine[0];
			String movie = dataLine[1];


			//before you create  a new movie or
			// actor vertex, you must check the hashmap if it already exists; if
			// it does, then you add to its edge list. Else you will end up with
			// a ton  of duplicate movies and actors and the graph will be huge

			// from actor to movie
			// if the actor is already in the Hashmap add an edge
			if(graph_al.containsKey(actor)){
				graph_al.get(actor).addEdge(new Edge(1, movie));
				numOfEdges++;
			}else{// add actor to the HashMap; set as a vertex; and add an movie edge
				graph_al.put(actor, new GraphList<String>());
				gl3 = graph_al.get(actor);
				MySLelement<String> sourceVertex = new MySLelement<String>(actor, actor, 0, prevVertex);
				pathMap.put(movie,actor);
				prevVertex = sourceVertex;
				gl3.setSourceVertex(sourceVertex);
				gl3.addEdge(new Edge(1, movie));
				numOfEdges++;
				numOfVertices++;
			}

			// from movie to actor
			// if the movie is already in the Hashmap add an edge
			if(graph_al.containsKey(movie)){
				graph_al.get(movie).addEdge(new Edge(1, actor));
				//				prevMap.put(movie, graph_al.get(movie));
				numOfEdges++;
			}else{// add movie to the HashMap; set as a vertex; and add an actor edge
				graph_al.put(movie, new GraphList<String>());
				gl3 = graph_al.get(movie);
				MySLelement<String> sourceVertex = new MySLelement<String>(movie, movie, 0, prevVertex);
				gl3.setSourceVertex(sourceVertex);
				gl3.addEdge(new Edge(1, actor));
				numOfEdges++;
				numOfVertices++;
			}


			// read next line
			line = data.readLine();
			// decrement the total number of lines read from file for faster visualization
			//			limit--;
		}

		System.out.println("numOfVertices: "+ numOfVertices+"\nnumOfEdges: "+numOfEdges);

		/**      Start graphTraversal    **/
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first actor's name:");
		String startVertex = sc.nextLine(); // set start vertex
		System.out.println("Enter second actor's name:");
		String targetVertex = sc.nextLine(); // set target vertex
		sc.close();
		
//		System.out.println("SmallList of actors:\n"+"Kevin_Bacon_(I), Al_Pacino, Billy_Crystal, John_Cusack, Peter_Cushing, Rodney_Dangerfield,"
//				+ "Erik_Jan_deBoer, Kave_Deymar, Andy_Dick, Christian_Slater, Catherine_Zeta-Jones");
		HashMap<String,Integer> distanceMap = new HashMap<String,Integer>();// map that stores the distance of each node
		HashMap<String,Boolean> hasBeenVisted = new HashMap<String,Boolean>(); // map that stores whether or not a node has been visited

		// loop through each GraphList object from the HashMap of actors and movies
		for(GraphList<String> gl :graph_al.values()){
			String sourceVertexLabel = gl.getSourceVertex().getLabel();
			// set each node to unvisited
			hasBeenVisted.put(sourceVertexLabel, false);
			// initialize Dijkstra
			// set the distance of each vertex to oo
			distanceMap.put(gl.getSourceVertex().getLabel(), Integer.MAX_VALUE);
		}

		distanceMap.put(startVertex,0); // set start vertex's distance to 0

		for(GraphList<String> gl :graph_al.values()){
			String sourceVertexLabel = gl.getSourceVertex().getLabel();
			if(hasBeenVisted.get(sourceVertexLabel) == false){
				// start BFS

				/** Start of Breadth first (queue-based) search */
				LQueue<String> queueOfVertices = new LQueue<String>(); // queue of vertices
				queueOfVertices.enqueue(startVertex); // enqueue start vertex
				hasBeenVisted.put(startVertex, true); // mark start vertex as visited


				while(queueOfVertices.length() > 0){// Process each vertex on queueOfVertices
					String vertex = queueOfVertices.dequeue(); 
					// preVisit
					graph_al.get(vertex).getSourceVertex().getVisualizer().setColor("blue");// highlight nodes visited
					// create a list of edges from String vertex
					ArrayList <SLelement<Edge>> currentEdgeList = new ArrayList<SLelement<Edge>>(); // store the edges into something iterable
					SLelement<Edge> currentEdgeVertices = graph_al.get(vertex).getAdjacencyList(); // get the list of edges
					while(currentEdgeVertices != null){ // loop until there are no more edges
						currentEdgeList.add(currentEdgeVertices); // add current edge to list						 
						currentEdgeVertices =  currentEdgeVertices.getNext(); // get the next edge of the AdjacencyList
					}				 


					// loop through the closes vertices(AdjacencyList) of String vertex
					// loop through each edge of the current vertex
					for(SLelement<Edge> edge :currentEdgeList){
						String currentEdgeString = edge.getValue().getVertex();
						// check to see if the currentEdge hasn't been visited
						if(hasBeenVisted.containsKey(currentEdgeString) && hasBeenVisted.get(currentEdgeString) == false){
							hasBeenVisted.put(currentEdgeString, true); // then set to visited
							graph_al.get(currentEdgeString).getSourceVertex().getVisualizer().setColor("blue");// nodes that are visited
							queueOfVertices.enqueue(currentEdgeString); // add the currentEdgeString to the queue
						}
						// postVisit
//						String pat = "";
						// update distance
						// Compute shortest path distances from s, store them in D, Dijkstra
						// Compute shortest path distances from currentEdgeString, store them in childEdgeList, Dijkstra
						if(distanceMap.get(currentEdgeString) != Integer.MAX_VALUE){// reachable 

							// create a list of child edges from the currentEdgeString
							ArrayList <SLelement<Edge>> childEdgeList = new ArrayList<SLelement<Edge>>(); // store the edges into something iterable
							SLelement<Edge> childEdgeVertices = graph_al.get(currentEdgeString).getAdjacencyList(); // get the list of edges
							while(childEdgeVertices != null){ // loop until there are no more edges
								childEdgeList.add(childEdgeVertices); // add current edge to list						 
								childEdgeVertices =  childEdgeVertices.getNext(); // get the next edge of the AdjacencyList
							}			
							
							int parentDistance = distanceMap.get(currentEdgeString);
							
							// loop through the closes vertices(AdjacencyList) of currentEdgeString
							// loop through each edges of the child vertex of the current vertex
							for(SLelement<Edge> childEdge:childEdgeList){
								String childVertex = childEdge.getValue().getVertex();
								int childDistance = distanceMap.get(childVertex);
								
									
								if(childDistance > (parentDistance)+1)	{// update distance if the child node has a larger distance than it's parent
									distanceMap.put(childVertex, distanceMap.get(currentEdgeString) + 1); //childDistance = parentDistance+1;
									graph_al.get(currentEdgeString).getSourceVertex().getVisualizer().setColor("gold");// color node path to targetVertex(
									graph_al.get(currentEdgeString).getSourceVertex().getVisualizer().setSize(25.0);
//									graph_al.get(childVertex).getSourceVertex().getVisualizer().setColor("blue");// nodes not in the path
//									pat = pat.concat(currentEdgeString);
								}
								// if the targetVertex is equal to the current childNode(found)
								// print info to console
								// clear queueOfVertices to stop search
								if(childEdge.getValue().getVertex().equalsIgnoreCase(targetVertex)){
									System.out.println("Starting Vertex "+startVertex+" found "+targetVertex+ "\nBacon Number: "+ distanceMap.get(targetVertex));
									// print path of the target vertex
//									String path = pathMap.get(targetVertex);
									// loop through the path of the target node until there are no more nodes
//									while(path!=null){
//										System.out.print(path.getValue()+" -> ");
//										path = path.getPrev();
//									}
//									System.out.println(path);
//									System.out.println();
									queueOfVertices.clear();
//									break;
								}
							}			

						}else{// Unreachable
							//	System.out.println("Node unreachable");
						}// skip to next node
					}
				} // end of while loop for BFS
			} 
		}	

		graph_al.get(startVertex).getSourceVertex().getVisualizer().setColor("red"); // set the staring vertex color to red
		graph_al.get(targetVertex).getSourceVertex().getVisualizer().setColor("red"); // set the target vertex color to red

		bridges.setDataStructure(graph_al, "graphl");
		bridges.visualize();
	} // end of main method
} // end of class