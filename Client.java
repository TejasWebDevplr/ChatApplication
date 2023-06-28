import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StringContent;

public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
//Declare Compenent for GUI
     private JLabel heading =new JLabel("Client");
     private JTextArea messageArea = new JTextArea();
     private JTextField messageInput=new JTextField();
     private Font font =new Font("Roboto",Font.PLAIN,20);


    //constructor
    public Client() {
         try {
             System.out.println("Sending request to server...");
             socket = new Socket("127.0.0.1", 7778);
             System.out.println("Connection established...");

             br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream());

                  //  createGUI();
                  //  handleEvent();
                    startReading();
                   startWriting();
        }
         catch (Exception e) {
            e.printStackTrace();
         }

    }





    //  private void handleEvent( ) {
    

    //         messageInput.addKeyListener(new KeyListener() {

    //            @Override
    //            public void keyTyped(KeyEvent e) {
    //                // TODO Auto-generated method stub
    //               // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    //            }

    //            @Override
    //            public void keyPressed(KeyEvent e) {
    //                // TODO Auto-generated method stub
    //                //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    //            }

    //            @Override
    //            public void keyReleased(KeyEvent e) {
    //                // TODO Auto-generated method stub
    //               //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    //                   //  System.out.println("key released "+e.getKeyCode());
    //                   if(e.getKeyCode()==10){
    //                    // System.out.println("you have pressed enter Button");
    //                     String ContentToSend=messageInput.getText();
    //                       messageArea.append("Me : "+ContentToSend+"\n");   out.println(ContentToSend);
    //                     out.flush();
    //                     messageInput.setText("");  
    //                     messageInput.requestFocus();
    //                 }
    //             }
               
    //         });
    //      }

    




    // private void createGUI(){
    //     //gui code 
    //        this.setTitle("Client Messenger[END]");
    //      this.setSize(500,500 );
    //       this.setLocationRelativeTo(null);
    //       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //          //coding for component of gui
    //          heading.setFont(font); 
    //          messageArea.setFont(font);
    //          messageInput.setFont(font);
    //           //   heading.setIcon(new ImageIcon("3rdsharingan.png"));
    //               heading.setHorizontalAlignment(SwingConstants.CENTER);
    //               heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    //               messageArea.setEditable(false);
              
            
    //               //Frame layout
    //            this.setLayout(new BorderLayout());
    //           //adding component to frame
    //           this.add(heading,BorderLayout.NORTH);
    //           JScrollBar jScrollPane=new JScrollBar();
    //           this.add(jScrollPane);
                

    //           this.add(messageArea,BorderLayout.CENTER);
    //           this.add(messageInput,BorderLayout.SOUTH);


    //      this.setVisible(true);
    //     }

          

    //start reading
    public void startReading()
     {

        // thread who will read data
         Runnable r1 = () -> {
            System.out.println("Reader Started");
            try {
                  while (true) {
                     String msg;

                     msg = br.readLine();

                     if (msg.equals("exit")) {

                        System.out.println("Server Terminated the chat");
                        JOptionPane.showMessageDialog(this,"Server  Left the Chat");
                        messageInput.setEnabled(false);
                       socket.close();
                        break;
                     }
                    System.out.println("Server: " + msg);
                        //   messageArea.append("server.: "+msg+"\n");
                    }
            } 
                catch (Exception e) 
                {
                 // e.printStackTrace();
                 System.out.println("Connection closed..");
               }

        };
        new Thread(r1).start();
    }
            //start writing
    public void startWriting() {
        // thread- take data form user and send to client
           Runnable r2 = () -> 
           {
                System.out.println("Writer Started....");
             try {
                 while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                      if (content.equals("exit")) {
                        socket.close();
                        break;

                     }

                 }
                 System.out.println("connection is closed..");


            } 
              catch (Exception e) 
              {
                e.printStackTrace();
                // System.out.println("Connection closed..");
             }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }


    public Socket getSocket() {
        return socket;
    }


    public BufferedReader getBr() {
        return br;
    }


    public PrintWriter getOut() {
        return out;
    }


    public JLabel getHeading() {
        return heading;
    }


    public JTextArea getMessageArea() {
        return messageArea;
    }


    public JTextField getMessageInput() {
        return messageInput;
    }


    public Font getFont() {
        return font;
    }

}
