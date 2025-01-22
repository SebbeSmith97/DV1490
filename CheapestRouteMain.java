//Sebastian Smith 970630 & Marcus Engert 950505

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.PriorityQueue;

public class CheapestRouteMain 
{

	public static void main(String[] input) 
	{
		PriorityQueue<nodeClass> pq = new PriorityQueue<nodeClass>();

		File inDataFile = new File(input[0]);
		Scanner fileInput = null;
		try 
		{
			fileInput = new Scanner(inDataFile);
		} catch (FileNotFoundException e) 
		{

			e.printStackTrace();
		}
		
		ArrayList<nodeClass> map = new ArrayList<nodeClass>();
		String direction = fileInput.nextLine();
		String stationName;
		int stationNumber = -1;
		
		while ((stationName = fileInput.nextLine()).isEmpty() == false) //initiallizerar in allt i graf
		{ 
			stationNumber++;
			
			map.add(new nodeClass(stationNumber, stationName));
			map.get(stationNumber).setStationID(stationNumber);
		}


		while (fileInput.hasNextLine()) {

			int temp = 0, tempCurrent = 0, tempNeighbour = 0;
			boolean currentOK = true, neighbourOK = true;
			
			String connection = fileInput.nextLine();
			String[] strArr = connection.split("\t");
			
			while (currentOK || neighbourOK) 
			{
				if (map.get(temp).getStation().equals(strArr[0])) 
				{ 
					currentOK = false;
					tempCurrent = temp;
				}
				if (map.get(temp).getStation().equals(strArr[1])) 
				{ 
					neighbourOK = false;
					tempNeighbour = temp;
				}
				if (neighbourOK || currentOK) 
				{
					temp++;
				}

			}

			map.get(tempCurrent).newNeighbour(Integer.parseInt(strArr[2]), map.get(tempNeighbour));
			
			
			if (direction.equals("UNDIRECTED")) 
			{
				map.get(tempNeighbour).newNeighbour(Integer.parseInt(strArr[2]), map.get(tempCurrent));
			}
		}
		
		int firstStationID = -1, lastStationID = -1;
		
			String[] station = input[1].split("-");
			String firstStation = station[0]; 
			String lastStation = station[1];	
					

		for (nodeClass k : map) {
			
			k.setStationPassed(false);		//set all nodes handled false
			k.setRouteWeight(Integer.MAX_VALUE); //set distance INFINITE for all nodes

			if (k.getStation().equalsIgnoreCase(firstStation)) //if this k is start node, set distance to 0
			{ 
				firstStationID = k.getStationID();	//set index of start node
				k.setRouteWeight(0);				
			}
			
			if (k.getStation().equalsIgnoreCase(lastStation))	
			{
				lastStationID = k.getStationID();		//set index of end node
			}
		}

		if(firstStationID == -1 || lastStationID == -1) 
			throw new NoSuchElementException("Could not find node.");

		nodeClass firstStationNode = map.get(firstStationID);  
		nodeClass lastStationNode = map.get(lastStationID);			


		for (int i = 0; i < firstStationNode.getNeighbourStation().size(); i++) 
		{

			nodeClass tempNode = firstStationNode.getNeighbourStation().get(i);
			
			int tempNeighbourWeight = firstStationNode.getNeighbourWeight().get(i);	// temp for current cost 

			tempNode.setRouteWeight(firstStationNode.getRouteWeight() + tempNeighbourWeight);		//summan av alla tidigare sträckor + nästa
			tempNode.setLastStation(firstStationNode);		//grannen får start node som previous

			pq.add(tempNode);
		}
		
		firstStationNode.setStationPassed(true);		//start node blivit behandlad


		while (!(pq.isEmpty() && map.get(lastStationID).isStationPassed())) //loop för att ta sig igenom grafen
		{  
			nodeClass tempNode = pq.peek(); 	//COPY     gör current till kopia av högsta prion av heap  (billigaste)

			nodeClass originNode = map.get(tempNode.getStationID());	
			
			pq.remove();		//tar bort högst prion i min-heapen

			if (!originNode.isStationPassed()) 
			{	
				originNode.setStationPassed(true);			
				originNode.setRouteWeight(tempNode.getRouteWeight());	
				originNode.setLastStation(tempNode.getLastStation());	
				
				for (int i = 0; i < tempNode.getNeighbourStation().size(); i++) //for loop genom alla grannar 
				{  
					nodeClass originNeighbour = map.get(tempNode.getNeighbourStation().get(i).getStationID());	//skapar grann-node genom att hämta motsvarande från graf
					int weight = tempNode.getNeighbourWeight().get(i);	//kostnad

					if (!originNeighbour.isStationPassed()) {	
						
						nodeClass neighbour = duplicate(originNeighbour); 	//kopierar grann-noden 
						
						neighbour.setRouteWeight(tempNode.getRouteWeight() + weight);	//sätter ny vikt för denna noden
						
						neighbour.setLastStation(tempNode);	//sätter current som prev för grannen
						
						pq.add(neighbour);	//köar grannen i PQ, loop börjar om
						
					}
				}
			}
		}
			
		printRoute(firstStationNode, lastStationNode);


	}

	private static void printRoute(nodeClass firstStationNode, nodeClass lastStationNode){
		FileWriter inputFile;
		
		try {
			inputFile = new FileWriter ("Anwser.txt", false);
			
			PrintWriter pw = new PrintWriter(inputFile);

			pw.println("0");
			pw.println(lastStationNode.getRouteWeight());
			pw.print(arrowPrint(firstStationNode, lastStationNode));

			pw.close();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


	public static String arrowPrint(nodeClass firstStation, nodeClass currStation) 
	{
		if (currStation.getStation().equals(firstStation.getStation()))
			return firstStation.getStation();
		else 
		{
			return arrowPrint(firstStation, currStation.getLastStation()) + " -> " + currStation.getStation();
		}
	}
	
	private static nodeClass duplicate(nodeClass origin)
	{  
		nodeClass duplicate = new nodeClass();
		
		duplicate.setNeighbourStation(origin.getNeighbourStation());
		duplicate.setNeighbourWeight(origin.getNeighbourWeight());
		duplicate.setStation(origin.getStation());
		duplicate.setRouteWeight(origin.getRouteWeight());
		duplicate.setStationPassed(origin.isStationPassed());
		duplicate.setStationID(origin.getStationID());
		duplicate.setLastStation(origin.getLastStation());

		return duplicate;

	}

}

