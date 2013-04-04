package example.gnutella;

import java.util.*;

public class GenerateGraph {

	int n=5000;
	List<Node> graph = new ArrayList<Node>();

	public GenerateGraph(){


		graph.add(new Node(0,null));

		for(int i=1; i<=5000; i++){
			graph.add(new Node(i, GenerateNeighbors()));
		}


	}//end constructor


	public List<Node> GenerateNeighbors(){
		List<Node> nodes = new ArrayList<Node>();
		Random rand = new Random();
		//max number of connections is ten minimum is one
		if(graph.size() > 10){
			int numNeighbors = 1+ rand.nextInt(9);
			for(int i =0; i< numNeighbors; i++){
				int neighbor=  rand.nextInt(graph.size());
				nodes.add(graph.get(neighbor));
			}
		}
		else{
			int numNeighbors = rand.nextInt(graph.size());
			for(int i =0; i< numNeighbors; i++){
				int neighbor=  rand.nextInt(graph.size());
				nodes.add(graph.get(neighbor));
			}
		}
		return nodes;
	}

}
