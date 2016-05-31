package es.ucm.fdi.tp.assignment6;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
	
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	/**
	 * Store the corresponding input and output streams
	 * @param s
	 * @throws IOException 
	 */
	public Connection(Socket s) throws IOException {
		this.s = s;
		this.out = new ObjectOutputStream(s.getOutputStream());
		this.in = new ObjectInputStream(s.getInputStream());
	}
	
	/**
	 * Send an object to the output stream of the socket
	 * @param r
	 * @throws IOException 
	 */
	public void sendObject(Object r) throws IOException {
		out.reset();
		out.writeObject(r);
		out.flush();
	}
	
	/**
	 * Read an object from the input stream of the socket
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Object getObject() throws ClassNotFoundException, IOException {
		return in.readObject();
	}
	
	/**
	 * Close the socket
	 * @throws IOException 
	 */
	public void stop() throws IOException {
		s.close();
	}
}
