package client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * 
 * @author Dorian Thivolle
 * @author Thibaut
 *
 */
public class Client {
	
	private Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;
	
	public void initClient() throws IOException {
	
		try{	
			
			socket = new Socket("127.0.0.1",3550);//172.16.XXX.XXX
			
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			//socket.close();
		}
	}
	
	public PrintWriter getPrinter()
	{
		return out;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	public BufferedReader getIn() {
		return in;
	}

}
