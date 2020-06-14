
import java.util.HashMap;
import java.util.*;

public class ProfileModel {
	HashMap<String, Profile> profiles = new HashMap<String, Profile>();
	Graph friends = new Graph();

	/**
	 * Constructor
	 * @param userdata
	 */
	public ProfileModel(String userdata) {

	}

	/**
	 * Method determines whether somebody exists in profiles
	 * @param username
	 * @return boolean
	 */
	public boolean exists(String username) {
		return profiles.containsKey(username);
	}

	/**
	 * Method returns a searched Profile
	 * @param username
	 * @return searched Profile
	 */
	public Profile get(String username) {
		return profiles.get(username);
	}

	/**
	 * Method adds a Profile 
	 * @param profile
	 */
	public void add(Profile profile) {
		friends.addVertex(new Vertex(profile.getUsername()));
		profiles.put(profile.getUsername(), profile);
	}

	/**
	 * Method adds a Friend
	 * @param username1
	 * @param username2
	 */
	public void addFriend(String username1, String username2) {
		friends.addEdge(new Edge(friends.getVertex(username1), friends.getVertex(username2),
				getDistance(get(username1), get(username2))));
		profiles.get(username1).addFriend(profiles.get(username2));// Adding friend to logged in User
		profiles.get(username2).addFriend(profiles.get(username1));// Adding logged in user to friend
	}

	/**
	 * Method deletes a Friend
	 * @param username1
	 * @param username2
	 */
	public void deleteFriend(String username1, String username2) {
		friends.removeEdge(friends.getEdge(friends.getVertex(username1), friends.getVertex(username2)));
		profiles.get(username1).deleteFriend(profiles.get(username2));// Deleting friend to logged in User
		profiles.get(username2).deleteFriend(profiles.get(username1));// Deleting logged in user to friend
	}

	/**
	 * Method returns the Distance between two Profiles
	 * @param p1 Profile 1
	 * @param p2 Profile 2
	 * @return distance
	 */
	public double getDistance(Profile p1, Profile p2) {
		// return 6378.388 * Math.acos(Math.sin(p1.getCoordinates().getLatitude()) *
		// Math.sin(p2.getCoordinates().getLatitude()) +
		// Math.cos(p1.getCoordinates().getLatitude()) *
		// Math.cos(p2.getCoordinates().getLatitude()) *
		// Math.cos(p2.getCoordinates().getLongitude() -
		// p1.getCoordinates().getLongitude()));
		return p1.getCoordinates().getDistance(p2.getCoordinates());
	}

	/**
	 * Method returns whether one user knows somebody else and about whom
	 * @param p1 Profile 1
	 * @param p2 Profile 2
	 * @return String
	 */
	public String knows(Profile p1, Profile p2) {
		for (int i = 0; i <= 3; i++) {
			String s = knows(p1, p2, i);
			if (s != null)
				return s;
		}
		return null;
	}

	/**
	 * Method determines whether one user knows somebody else and about whom
	 * @param p1 Profile 1
	 * @param p2 Profile 2
	 * @param maximalRecursionDeepth
	 * @return String
	 */
	private String knows(Profile p1, Profile p2, int maximalRecursionDeepth) {
		if (p1.getUsername().equals(p2.getUsername()))
			return p2.getUsername();
		else if (maximalRecursionDeepth <= 0)
			return null;
		else {
			p1.getFriendlist().toFirst();
			while (p1.getFriendlist().hasAccess()) {
				String s = knows(p1.getFriendlist().getContent(), p2, maximalRecursionDeepth - 1);
				if (s != null) {
					return p1.getUsername() + "," + s;
				}
				p1.getFriendlist().next();
			}
			return null;
		}
	}

	/**
	 * Method calculates the shortest Distance to distribute material to all  friends
	 * @param username 
	 * @return String which contains the order in which the user should distribute the material
	 * to his friends in the shortest way
	 */
	public String shortestDistance(String username) {
		String resultListOfFriends = "";
		List<String> friends = createSortedList(username, breadthFirstSearch(new Vertex(username)));
		friends.toFirst();
		friends.remove();
		while (!friends.isEmpty()) {
			friends.toFirst();	
			resultListOfFriends += friends.getContent() ;
			System.out.println(resultListOfFriends);
			friends.remove();	
			createSortedList(username, friends);
		}
		return resultListOfFriends;
	}
	
	/**
	 * Method goes iterative through the graph and returns all IDs from different
	 * Nodes
	 * @param v Vertex to work with
	 * @return Sting
	 */
	public String breadthFirstSearch(Vertex v) {
		String result = "";
		boolean neighbours = true;
		List<Vertex> helpList = new List<Vertex>();
		helpList.append(v);
		v.setMark(true);
		while (neighbours) {
			List<Vertex> nList = new List<Vertex>();
			if (!helpList.isEmpty()){
			helpList.toFirst();
			nList = friends.getNeighbours(helpList.getContent());
			friends.setAllVertexMarks(false);
			}
			if (nList.isEmpty()) {
				neighbours = false;
			} else {
				helpList.remove();
				nList.toFirst();
				while (nList.hasAccess()) {
					if (!nList.getContent().isMarked() && !result.contains(nList.getContent().getID())) {
						helpList.append(nList.getContent());
						nList.getContent().setMark(true);
						result += nList.getContent().getID() + ";";
						nList.next();
					} else {
						nList.next();
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Method creates a List which contains all friends in a by distance sorted order 
	 * @param username
	 * @param s String which contains all friends 
	 * @return List<String> with a sorted order of all friends
	 */
	public List<String> createSortedList(String username, String s){
		List<String> list = new List<String>();
		String[] a = s.split(";");
		for (int i = 0; i < a.length-1; i++) {
			for (int j = 0; j < a.length-1; j++) {
				if(getDistance(profiles.get(username), profiles.get(a[j+1])) < 
						getDistance(profiles.get(username), profiles.get(a[j]))) {	// Bubble-Sort in Array
					String temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
				}
			}
		}
		for (int i = 0; i< a.length; i++) {// Array in List
			list.append(a[i] + ", Dist: " + Math.round(getDistance(profiles.get(username), profiles.get(a[i]))*100.0)/100.0 + ";   ");
		}
		return list;
	}
	
	/**
	 * Method creates a List which contains all friends in a by distance sorted order 
	 * @param username
	 * @param s List<String> which contains all friends 
	 * @return List<String> with a sorted order of all friends
	 */
	public List<String> createSortedList(String username, List<String> s){
		List<String> list = new List<String>();
		ArrayList<String> a = new ArrayList<String>();
		list.toFirst();
		while (list.hasAccess()) {
			a.add(list.getContent());
		}
		for (int i = 0; i < a.size()-1; i++) {
			for (int j = 0; i < a.size()-1; j++) {
				if(getDistance(profiles.get(username), profiles.get(a.get(j+1))) < 
						getDistance(profiles.get(username), profiles.get(a.get(j)))) {	//Bubble-Sort in ArrayList
					String temp = a.get(j);
					a.add(j, a.get(j+1));
					a.add(j+1, temp);
				}
			}
		}
		for (int i = 0; i< a.size()-1; i++) {	// ArrayList in List
			list.append(a.get(i) + ", Dist: " + Math.round(getDistance(profiles.get(username), profiles.get(a.get(i)))*100.0)/100.0 + ";   ");
		}
		return list;
	}
}
