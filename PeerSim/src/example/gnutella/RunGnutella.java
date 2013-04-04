package example.gnutella;


import java.util.*;

public class RunGnutella {

	public static void main(String[] args) {
		Random rand = new Random();
		boolean flag = true;
		long startTime=0;
		long stopTime =0;
		int tempReturn=0;
		int numFailures=0;
		double averageRunTime=0.0;
		double averageHops=0.0;
		Files file = new Files();

		System.out.println(file.FileName);
		//make new graph
		GenerateGraph TestGraph = new GenerateGraph();

		Scanner reader = new Scanner(System.in);
		int method;

		System.out.println("Enter a search method: ");
		method = Integer.parseInt(reader.next());



		//How many times to test the system
		for(int i=0; i<10000; i++){
			//choose node to start search from
			int start =  rand.nextInt(TestGraph.n);

			//choose file to search for
			while(flag){
				file = new Files();

				//if the node already has this file keep generating
				if(TestGraph.graph.get(start).fileList.contains(file)){
					flag = true;
				}
				else{
					flag = false;
				}

			}

			switch(method) {
			case 1: tempReturn = TestGraph.graph.get(start).Flood(TestGraph.graph.get(start).GenerateQuery(file));
			break;

			case 2: tempReturn = TestGraph.graph.get(start).RandomWalk(TestGraph.graph.get(start).GenerateQuery(file));
			break;

			case 3: tempReturn = TestGraph.graph.get(start).RandomWalkWithNeighborTable(TestGraph.graph.get(start).GenerateQuery(file));
			break;
			}

			//take down start time
			startTime = System.currentTimeMillis();


			/*
			 * 
			 * execute a search
			 * Three different types Flood, RandomWalk, and RandomWalkWithNeighborTable
			 * 
			 * */




			//take down search end time
			stopTime = System.currentTimeMillis();

			if(tempReturn != -100){
				averageRunTime += (stopTime-startTime);
				averageHops += TestGraph.graph.get(start).currentQuery.hopCount;
			}
			else{
				numFailures++;
			}

		}

		System.out.println("Average Time for 10000 runs is: " + (averageRunTime/(10000.0-numFailures)));
		System.out.println("Average Hops for 10000 runs is: " + (averageHops/(10000.0-numFailures)));
		System.out.println("Number of Failures is: " + numFailures);



	}

}
