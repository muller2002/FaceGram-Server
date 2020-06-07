
public class Main {

	public static void main(String[] args) {
		
		int port = 50000 + 5;

		FaceGramServerApplication fgsa = new FaceGramServerApplication(port , "");
		fgsa.register("Ö", "Account", "0,0", "T1", "P", "IP:1");
		
		for(int i = 1; i < 100; i++) {
			fgsa.register("TestNr" + i, "Account", (int)i/90 + "," + i%90, "T" + i, "P", "IP:" + i);
		}
		
		fgsa.register("Magnus", "Müller", "52.1172,8.6868", "MagnusM", "P", "a:1");
		fgsa.register("Daniel","Benke","52.125081,8.616338","DanielB","123", "a:2");
		fgsa.register("Jannik","Boldt","52.119660,8.611019","JannikB","123", "a:3");
		
		for(int i = 1; i < 5; i++) {
			fgsa.addFriend("T" + i, "a:2");
		}
		fgsa.friendList("a:2");
		
		
		fgsa.addFriend("T1",  "IP:2");
		fgsa.addFriend("T3",  "IP:2");
		fgsa.addFriend("T4",  "IP:2");
		
		
		
		fgsa.addFriend("T7",  "IP:1");
		
		fgsa.addFriend("T5",  "IP:6");
		
		fgsa.addFriend("T8",  "IP:7");
		
		fgsa.addFriend("T9",  "IP:8");
		
		fgsa.addFriend("T10",  "IP:9");
		
		fgsa.addFriend("T11",  "IP:10");
		
		fgsa.knows("T2", "IP:1");
		fgsa.knows("T3", "IP:1");
		fgsa.knows("T4", "IP:1");
		fgsa.knows("T5", "IP:1");
		fgsa.knows("T6", "IP:1");
		fgsa.knows("T7", "IP:1");
		fgsa.knows("T8", "IP:1");
		fgsa.knows("T9", "IP:1");
		fgsa.knows("T10", "IP:1");
		fgsa.knows("T11", "IP:1");

		
		fgsa.message("T2", "test", "IP:1");
		fgsa.message("T2", "test1", "IP:1");
		fgsa.message("T3", "testa1", "IP:1");
		fgsa.message("T1", "test2", "IP:2");
		fgsa.chat("T4", "IP:1");
		//fgsa.chat("T1", "IP:2");
		
	}
	

}
