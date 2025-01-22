//Sebastian Smith 970630 & Marcus Engert 950505

import java.util.ArrayList;

public class nodeClass implements Comparable<nodeClass>
{
	private ArrayList<nodeClass> neighbourStation = new ArrayList<nodeClass>();
	private nodeClass lastStation;
	private ArrayList<Integer> neighbourWeight = new ArrayList<Integer>();
	private Integer routeWeight;
	private boolean stationPassed;
	private int stationID;
	private String station;
	
	public nodeClass()
	{
		
	}
		public nodeClass(int stationID, String station)
		{
			super();

			this.stationID = stationID;
			this.station = station;
		}
		
		public nodeClass(int stationID, String station, nodeClass neighbourStation, Integer neighbourWeight)
		{
			super();
			
			this.stationID = stationID;
			this.station = station;
			this.neighbourStation.add(neighbourStation);
			this.neighbourWeight.add(neighbourWeight);
		}
		
		public void newNeighbour(Integer neighbourWeight, nodeClass neighbourStation) // Adds a new neighbour
		{
			this.neighbourWeight.add(neighbourWeight);
			this.neighbourStation.add(neighbourStation);
		}
		
		public int compareTo(nodeClass n) 
		{
	        if(this.routeWeight < n.getRouteWeight()){
	            return -1;
	        }else if(this.routeWeight > n.getRouteWeight()){
	            return 1;
	        }else
	            return 0;
	    }
		
		/*		GETTERS & SETTERS		*/
		public ArrayList<nodeClass> getNeighbourStation() {
			return neighbourStation;
		}
		public void setNeighbourStation(ArrayList<nodeClass> neighbourStation) {
			this.neighbourStation = neighbourStation;
		}
		public nodeClass getLastStation() {
			return lastStation;
		}
		public void setLastStation(nodeClass lastStation) {
			this.lastStation = lastStation;
		}
		public ArrayList<Integer> getNeighbourWeight() {
			return neighbourWeight;
		}
		public void setNeighbourWeight(ArrayList<Integer> neighbourWeight) {
			this.neighbourWeight = neighbourWeight;
		}
		public Integer getRouteWeight() {
			return routeWeight;
		}
		public void setRouteWeight(Integer routeWeight) {
			this.routeWeight = routeWeight;
		}
		public boolean isStationPassed() {
			return stationPassed;
		}
		public void setStationPassed(boolean stationPassed) {
			this.stationPassed = stationPassed;
		}
		public int getStationID() {
			return stationID;
		}
		public void setStationID(int stationID) {
			this.stationID = stationID;
		}
		public String getStation() {
			return station;
		}
		public void setStation(String station) {
			this.station = station;
		}
	}

