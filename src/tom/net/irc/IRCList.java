package tom.net.irc;

/**
 * @author Thomas J Palmer
 * @version 1.0.0.0
 * date   09 March, 2017
 */

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class IRCList {

	//server list
	private LinkedList<IRC> servers;
	
	private Scanner in;
	
	private int autoPort = 4560;

	/**
	 * Create server list
	 */
	public IRCList () {
		servers = new LinkedList<IRC>();
	}	

	/**
	 * Add Scanner
	 * @param in
	 */
	public void addScanner(Scanner in) {
		this.in = in;
	}
	
	public void closeScanner(Scanner in) {
		this.in.close();
	}

	/**
	 * Add server to server list and start based on con variable
	 * @param con
	 */
	public void addServer (boolean con) {
		boolean created = false;
		if (in == null) {
			addScanner(new Scanner(System.in));
			created = true;
		}
		System.out.print("Server: ");
		String server = in.nextLine().trim();
		System.out.print("Channel: ");
		String channel = in.nextLine().trim();
		System.out.print("Username: ");
		String user = in.nextLine().trim();
		System.out.print("Nickname: ");
		String nick = in.nextLine().trim();
		
		System.out.print("Would you like to specify a port (Y/n): ");
		if (in.nextLine().toLowerCase().trim().equals("y")) {
			
			System.out.print("Port: ");
			int port = in.nextInt();
			
			servers.add(new IRC(server, channel, user, nick, port, con));
		} else {
			servers.add(new IRC(server, channel, user, nick, autoPort, con));
			autoPort += 10;
		}
		if (created)
			in.close();
	}

	/**
	 * Add server and start automatically
	 */
	public void addServer () {
		boolean created = false;
		if (in == null) {
			addScanner(new Scanner(System.in));
			created = true;
		}
		System.out.print("Server: ");
		String server = in.nextLine().trim();
		System.out.print("Channel: ");
		String channel = in.nextLine().trim();
		System.out.print("Username: ");
		String user = in.nextLine().trim();
		System.out.print("Nickname: ");
		String nick = in.nextLine().trim();
		
		System.out.print("Would you like to specify a port (Y/n): ");
		if (in.nextLine().toLowerCase().trim().equals("y")) {
			
			System.out.print("Port: ");
			int port = in.nextInt();
			
			servers.add(new IRC(server, channel, user, nick, port, true));
		} else {
			servers.add(new IRC(server, channel, user, nick, autoPort, true));
			autoPort += 10;
		}
		if (created)
			in.close();
	}

	/**
	 * Add irc directly from precreated object
	 * @param irc
	 */
	public void addServer (IRC irc) {
		servers.add(irc);
	}

	/**
	 * Find irc by name from server list
	 * @param server
	 * @return
	 */
	public IRC findServer(String server) {
		for (IRC irc : servers) {
			if(irc.getServer().equals(server))
				return irc;
		}
		return null;
	}

	/**
	 * Delete IRC by object from server list
	 * @param irc
	 */
	public void deleteServer (IRC irc) {
		try {
			irc.close();
			servers.remove(irc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Delete IRC by name from server list
	 * @param server
	 */
	public void deleteServer (String server) {
		IRC irc = findServer(server);
		deleteServer(irc);
	}
}
