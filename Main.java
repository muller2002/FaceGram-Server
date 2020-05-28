
public class Main {

	public static void main(String[] args) {
		
		int port = 50000 + 6;
		FaceGramServerApplication fgsa = new FaceGramServerApplication(port , "");
		fgsa.register("Test", "Account", "0,0", "T1", "Passwort", "IP:0");
		fgsa.register("Test", "Account", "0,0", "T2", "Passwort", "IP:1");
		fgsa.register("Test", "Account", "0,0", "T3", "Passwort", "IP:2");
		fgsa.register("Test", "Account", "0,0", "T4", "Passwort", "IP:3");
		Client clientMagnus = new Client("0.0.0.0", port) {
			
			@Override
			public void processMessage(String pMessage) {
				System.out.println("E:" + pMessage);
				
			}
		};
		String ip = "192.168.2.114";
		clientMagnus.send("Register:Magnus:Mueller:0,0:Magnusm2002:Password");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientMagnus.send("AddFriend:T1");
		clientMagnus.send("AddFriend:T3");
		clientMagnus.send("Friendlist");
		clientMagnus.send("DelFriend:T1");
		clientMagnus.send("DelFriend:T1");
		clientMagnus.send("Friendlist");
		clientMagnus.send("Logout");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientMagnus.send("Login:Magnusm2002:Password");
		clientMagnus.send("Friendlist");
		
	}

}
