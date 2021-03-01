package editor;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    private Socket socket = null;
    private final String SERVER_IP = "127.0.0.1";
    private final int SERVER_PORT = 8080;
    private String code;

    public Client(String code) throws IOException
    {
        this.code = code;
        this.socketConnect(this.SERVER_IP, this.SERVER_PORT);
    }

    public void ChangeCode(String newCode)
    {
        this.code = newCode;
    }

    public void sendTextUpdate(String text)
    {
        if(!this.socket.isClosed() && this.socket.isConnected())
        {
            this.sendData("|"+this.code+"|"+text);
        }
    }

    public String getGlobalText()
    {
        if(!this.socket.isClosed() && this.socket.isConnected())
        {
            return this.sendData("|"+this.code+"|");
        }
        return null;
    }

    private String sendData(String text)
    {
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(text);
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            return in.readLine();
        }
        catch (IOException e) {
            try{
                this.socket.close();
                this.socketConnect(this.SERVER_IP, this.SERVER_PORT);
            }catch (IOException err) {}
        }
        return null;
    }

    private void socketConnect(String ip, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(ip, port);
    }
}