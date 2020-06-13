import java.util.HashMap;

public class ProfileModel {
	HashMap<String, Profile> profiles = new HashMap<String, Profile>();
	Graph friends = new Graph();
	public ProfileModel(String userdata) {
		
	}
	public boolean exists(String username) {
		return profiles.containsKey(username);
	}
	public Profile get(String username) {
		return profiles.get(username);
	}
	public void add(Profile profile) {
		friends.addVertex(new Vertex(profile.getUsername()));
		profiles.put(profile.getUsername(), profile);
		
	}

	
	public void addFriend(String username1, String username2) {
		friends.addEdge(new Edge(friends.getVertex(username1), friends.getVertex(username2), getDistance(get(username1), get(username2))));
		profiles.get(username1).addFriend(profiles.get(username2));//Adding friend to Logged in User
		profiles.get(username2).addFriend(profiles.get(username1));//Adding loged in user to friend
	}
	
	public void deleteFriend(String username1, String username2) {
		friends.removeEdge(friends.getEdge(friends.getVertex(username1), friends.getVertex(username2)));
		profiles.get(username1).deleteFriend(profiles.get(username2));//Adding friend to Logged in User
		profiles.get(username2).deleteFriend(profiles.get(username1));//Adding loged in user to friend
	}
	
	public double getDistance(Profile p1, Profile p2) {
		return p1.getCoordinates().getDistance(p2.getCoordinates());
	}
	
	
	
	
	
	public String knows(Profile p1, Profile p2) {
		for(int i = 0; i <= 3; i++) {
			String s = knows(p1, p2, i);
			if(s != null)return s;
		}
		return null;
		
		
		
	}
	private String knows(Profile p1, Profile p2, int maximalRecursionDeepth) {
		if(p1.getUsername().equals(p2.getUsername()))return p2.getUsername();
		else if(maximalRecursionDeepth <= 0)return null;
		else {
			p1.getFriendlist().toFirst();
			while(p1.getFriendlist().hasAccess()) {
				String s = knows(p1.getFriendlist().getContent(), p2, maximalRecursionDeepth-1);
				if(s != null) {
					return p1.getUsername() + "," + s;
				}
				p1.getFriendlist().next();
			}
			return null;
		}
	}
}
