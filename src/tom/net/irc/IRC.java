package tom.net.irc;

/**
 * @author Thomas J Palmer
 * @version 1.0.0.0
 * date   09 March, 2017
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class IRC implements Runnable{
	
	private boolean connected;
	
	private int port;
	private Socket socket;
	
	private String server;
	private String channel;
	private String user;
	private String nick;
	
	private BufferedReader in;
	private BufferedWriter out;
	
	public String output = "";

	/**
	 * Basic empty IRC constructor
	 */
	public IRC () {}

	/**
	 * IRC with only Server name
	 * 
	 * @param server
	 */
	public IRC (String server) {
		this.server = server;
	}

	/**
	 * IRC with Server name and Channel name
	 * 
	 * @param server
	 * @param channel
	 */
	public IRC (String server, String channel) {
		this.server = server;
		this.channel = channel;		
	}

	/**
	 * IRC with Server name, Channel name, and Username
	 * 
	 * @param server
	 * @param channel
	 * @param user
	 */
	public IRC (String server, String channel, String user) {
		this.server = server;
		this.channel = channel;		
		this.user = user;
	}

	/**
	 * IRC with all info, and if con is true
	 * it will connect automatically 
	 * after the IRC is created
	 * 
	 * @param server
	 * @param channel
	 * @param user
	 * @param nick
	 */
	public IRC (String server, String channel, String user, String nick) {
		this.server = server;
		this.channel = channel;		
		this.user = user;
		this.nick = nick;
	}

	/**
	 * IRC with all info, and if con is true
	 * it will connect automatically 
	 * after the IRC is created
	 *
	 * @param server
	 * @param channel
	 * @param user
	 * @param nick
	 * @param port
	 * @param con
	 */
	public IRC (String server, String channel, String user, String nick, int port, boolean con) {
		this.server = server;
		this.channel = channel;		
		this.user = user;
		this.nick = nick;
		this.port = port;

		if (con)
			try {
				this.connect();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Connect to IRC
	 * @throws UnknownHostException 
	 * @throws IOException 
	 */
	public void connect () throws UnknownHostException, IOException{
        socket = new Socket(server, port);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
        
        // Log on to the server.
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + user + " 8 * : Java IRC Hacks Bot\r\n");
        writer.flush( );
        
        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = reader.readLine( )) != null) {
            if (line.indexOf("004") >= 0) {
                // We are now logged in.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }
        
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        writer.flush();
        this.run();
	}
	
	/**
	 * Write to output with String
	 * @param s String
	 */
	public void write(String s) {
		output = s;
	}
	
	/**
	 * Write to output with Scanner
	 * @param s Scanner
	 */
	public void write(Scanner s) {
		output = s.nextLine();
	}
	
	/**
	 * Starts the socket reader
	 */
	public void run () {
		String line = null;
		try {
			while ((line = in.readLine( )) != null) {
	            if (line.toLowerCase( ).startsWith("PING ")) {
	                out.write("PONG " + line.substring(5) + "\r\n");
	                out.write("PRIVMSG " + channel + " :I got pinged!\r\n");
	                if (!this.output.equalsIgnoreCase(""))
	                	out.write(output);
	                out.flush( );
	            }
	            else {
	                System.out.println(line);
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes connection to IRC
	 * @throws IOException 
	 */
	public void close () throws IOException {
		socket.close();
	}
	
	/**
	 * Get Server name
	 */
	public String getServer () {
		return server;
	}
	
	/**
	 * Set Server name
	 * @param server
	 */
	public void setServer (String server) {
		this.server = server;
	}
	
	/**
	 * Get Channel name
	 */
	public String getChannel () {
		return channel;
	}
	
	/**
	 * Set Channel name
	 * @param channel
	 */
	public void setChannel (String channel) {
		this.channel = channel;
	}
	
	/**
	 * Get Username
	 */
	public String getUser () {
		return user;
	}
	
	/**
	 * Set Username
	 * @param user
	 */
	public void setUser (String user) {
		this.user = user;
	}
	
	/**
	 * Get Nickname
	 */
	public String getNick () {
		return nick;
	}
	
	/**
	 * Set Nickname
	 * @param nick
	 */
	public void setNick (String nick) {
		this.nick = nick;
	}
	
	/**
	 * Get connection Port
	 */
	public int getPort () {
		return port;
	}
	
	/**
	 * Set connection Port
	 * @param port
	 */
	public void setPort (int port) {
		this.port = port;
	}
	
	/**
	 * Print function
	 */
	public String print() {
		String s = this.server
				.concat("\n").concat(this.channel)
				.concat("\n").concat(this.channel)
				.concat("\n").concat(this.user)
				.concat("\n").concat(this.nick)
				.concat("\n").concat(Integer.toString(this.port));
		System.out.println(s);
		return s;
	}

}
