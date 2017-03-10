package tom.net.example;

import tom.net.irc.IRC;
import tom.net.irc.IRCList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IRCList l = new IRCList();
		IRC irc = new IRC("localhost", "#yup", "me", "myself", 4444, true);
		l.addServer(irc);
		l.findServer("localhost").write("hi");
		l.deleteServer(irc);
	}

}
