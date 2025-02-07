import java.io.*;
import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;

public class Client extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea= new JTextArea();
    private JTextField messageInput= new JTextField();
    private Font font= new Font("Roboto", Font.PLAIN, 20);

    public Client(){
        try {
            // System.out.println("Sending request to server");
            // socket= new Socket("127.0.0.1",7777);
            // System.out.println("connection done.");

            // br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // out= new PrintWriter(socket.getOutputStream());

            createGUI();
            // startReading();
            // startWriting();


        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void createGUI(){
        this.setTitle("Client Messanger[END]");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setIcon(new ImageIcon("clogo.jpg"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        

        this.setLayout(new BorderLayout());

        this.add(heading, BorderLayout.NORTH);
        this.add(messageArea, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);


        this.setVisible(true);
    }

    public void startReading(){
        //thread- read karke deta rhega
        Runnable r1= ()->{
            System.out.println("reader started..");
            try{
            while (true) {

                
                String msg= br.readLine();
                
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat");
                    socket.close();
                    break;
                    
                }

                System.out.println("Server : " + msg);
                
            }
        }catch(Exception e){
            //e.printStackTrace();
            //System.out.println("Connection closed");
        }
        };

        new Thread(r1).start();
    }

    public void startWriting(){
        //thread- data user se lega or send krega client ko
        Runnable r2= ()->{
            System.out.println("writer started..");
            try {
            while (!socket.isClosed()) {
                

                    BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));

                    String content= br1.readLine();

                    
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                
            }

            System.out.println("Connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        };

        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}
