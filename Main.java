
public class Main {

	public static void main(String[] args) {
		
		int port = 50000 + 6;
		FaceGramServerApplication fgsa = new FaceGramServerApplication(port , "");
		fgsa.register("Test", "Account", "0,0", "T1", "Passwort", "IP:1");
		fgsa.register("Test", "Account", "1,0", "T2", "Passwort", "IP:2");
		fgsa.register("Test", "Account", "2,0", "T3", "Passwort", "IP:3");
		fgsa.register("Test", "Account", "3,0", "T4", "Passwort", "IP:4");
		fgsa.register("Test", "Account", "0,1", "T5", "Passwort", "IP:5");
		fgsa.register("Test", "Account", "1,1", "T6", "Passwort", "IP:6");
		fgsa.register("Test", "Account", "2,1", "T7", "Passwort", "IP:7");
		fgsa.register("Test", "Account", "3,1", "T8", "Passwort", "IP:8");
		fgsa.register("Test", "Account", "0,2", "T9", "Passwort", "IP:9");
		fgsa.register("Test", "Account", "1,2", "T10", "Passwort", "IP:10");
		fgsa.register("Test", "Account", "2,2", "T11", "Passwort", "IP:11");
		fgsa.register("Test", "Account", "3,2", "T12", "Passwort", "IP:11");
		
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
		fgsa.chat("T2", "IP:1");
		fgsa.chat("T1", "IP:2");
	}
	

}
