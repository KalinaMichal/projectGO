package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ChatServer {


        String mes1="";
        String mes2="";
        boolean endChat=false;
        ObjectInputStream ois1;
        ObjectInputStream ois2;
        ObjectOutputStream oos1;
        ObjectOutputStream oos2;

        public void startChatServer()
        {

        ServerSocket server = null;
        /**port socket serwer-a*/
        int port = 6666;

        /**tworzenie socket serwer*/
        {
            try {
                server = new ServerSocket(port);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Stworzylem server");
        /**Tutaj daje wiadomosc klientom ktory byl pierwszy*/


        //******************************//
        //---- PODLACZANIE DO SERVERA---//
        //******************************//

        /*Czekam na klienta 1*/
        System.out.println("Czekam na 1 gracza");
        /**Czekam na klienta 1*/
        Socket socket1 = null;
        Socket socket2 = null;
        try {
            socket1 = server.accept();
            socket2 = server.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
            try {
                ois1 = new ObjectInputStream(socket1.getInputStream());
                ois2 = new ObjectInputStream(socket2.getInputStream());
                oos1 = new ObjectOutputStream(socket1.getOutputStream());
                oos2 = new ObjectOutputStream(socket2.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                ServerChat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    private void ServerChat() throws InterruptedException {
        ClientReciveChatInfo client2Thread= new ClientReciveChatInfo(ois2, false,this );
        ClientReciveChatInfo client1Thread= new ClientReciveChatInfo( ois1, true ,this);

        client2Thread.start();
        client1Thread.start();


        /**Zmienne przechowujace wiadomosci 1 i 2 gracza*/
        endChat=false;
        while(true){

            /**Sprawdzam czy doszlo do porozumienia miedzy graczami*/
            if(endChat)
            {

                System.out.println("Wznawiam grę");
                client2Thread.interrupt();
                client1Thread.interrupt();

                break;
            }
            sleep(100);
            /**Daje klientowi 2 odpowiedz*/
            try {
                oos2.writeObject(mes1);
            } catch (IOException e) {
                e.printStackTrace();
            }



            /**Daje klientowi 1 odpowiedz*/
            try {
                oos1.writeObject(mes2);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**2 metody o tej samej nazwie ale roznymi param.*/
    public void setParams(String mes,boolean isBlack1)
    {
        {
            if (isBlack1) {
                this.mes1 = mes;
            } else {
                this.mes2 = mes;
            }
        }
        if(mes.equals("Wznawiam gre!"))endChat=true;

    }
}

class ClientReciveChatInfo extends Thread {
    public  ClientReciveChatInfo(ObjectInputStream ois1, boolean isBlack1,ChatServer server1){
        this.ois=ois1;
        this.server = server1;
        this.isBlack=isBlack1;
    }
    //GOTOWE//todo: pola prywatne! wszystkie, boole też

    private  ObjectInputStream ois;
    private boolean isBlack;
    private ChatServer server;
    private String mes;
    /**
     * Biore i konwertuje na String wiadomosc od 1 klienta
     */



    @Override
    public synchronized void run() {
        System.out.println("Jestem w watku CHAT");
        while(true){
            try {
                mes = (String) ois.readObject();
                server.setParams(mes,isBlack);
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
            System.out.println("Dostalem widomosc od 1 gracza: " + mes);
        }
    }
}
