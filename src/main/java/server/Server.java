package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Ta klasa implementuje Socket server
 *
 */


public class Server {
        private static ServerSocket server;
        /*port socket serwer-a*/
        private static int port = 6666;




        public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
                /*tworzenie socket serwer*/
                server = new ServerSocket(port);
                int a1=20;
                int b1=20;
                int a2=20;
                int b2=20;
                boolean first =true;
                System.out.println("Stworzylem server");
                /*Tutaj daje wiadomosc klientom ktory byl pierwszy*/

                //******************************//
                //---- UNDER CONSTRUCKTION------//
                //******************************//

                /*Czekam na klienta 1*/
                System.out.println("Czekam na 1 gracza");
                Socket socketP1 = server.accept();
                System.out.println("Gracz 1 dolaczyl do serwera");
                /*Daje klientowi 1 odpowiedz*/
                ObjectOutputStream oosP1 = new ObjectOutputStream(socketP1.getOutputStream());
                oosP1.writeObject(true);

                /*Teraz biore wiadomosc od klienta 1 */
                ObjectInputStream checkera = new ObjectInputStream(socketP1.getInputStream());


                /*Czekam na klienta 2*/
                System.out.println("Czekam na 2 gracza");
                Socket socketP2 = server.accept();
                System.out.println("Gracz 2 dolaczyl do serwera");

                /*Konwertuje na inta*/
                String a = (String) checkera.readObject();
                System.out.println("Dostalem waidomosc od 1 gracza: " + a);

                /*Daje klientowi 2 odpowiedz*/
                ObjectOutputStream oosP2 = new ObjectOutputStream(socketP2.getOutputStream());
                oosP2.writeObject(false);

                /*Teraz biore wiadomosc od klienta 2 */
                ObjectInputStream checkerb = new ObjectInputStream(socketP2.getInputStream());

                /*Konwertuje na inta*/
                String b = (String) checkerb.readObject();
                System.out.println("Dostalem widomosc od 2 gracza: " + b);



                /*informuje klienta 1 ze wszyscy sa*/
                oosP2.writeObject(true);

                /*informuje klienta 2 ze wszyscy sa*/
                oosP1.writeObject(true);




                checkera.close();
                checkerb.close();
                oosP1.close();
                socketP1.close();
                oosP2.close();
                socketP2.close();

                /*Czekam na klienta 2*/
                System.out.println("Czekam na 2 gracza");
                Socket preSocket = server.accept();


                while(true){
                        /**
                         *
                         * Lacze sie z kientem nr 1
                         *
                         * */

                        /*Czekam na klienta 1*/
                        System.out.println("Czekam na 1 gracza");
                        Socket socket1 = server.accept();
                        System.out.println("Gracz 1 dolaczyl do serwera");

                        /*Teraz biore wiadomosc od klienta 1 */
                        ObjectInputStream checker1 = new ObjectInputStream(socket1.getInputStream());

                        /*Konwertuje na inta*/
                        a1 = (int) checker1.readObject();
                        System.out.println("Dostalem widomosc od 1 gracza: " + a1);
                        /*Konwertuje na inta*/
                        b1 = (int) checker1.readObject();
                        System.out.println("Dostalem widomosc od 1 gracza: " + b1);

                        if(first) {
                                /*Daje klientowi 2 odpowiedz*/
                                ObjectOutputStream oos2 = new ObjectOutputStream(preSocket.getOutputStream());
                                oos2.writeObject(a1);
                                oos2.writeObject(b1);
                                preSocket.close();
                                first = false;
                        }


                        /**
                         *
                         * Lacze sie z kientem nr 2
                         *
                         * */
                        /*Czekam na klienta 2*/
                        System.out.println("Czekam na 2 gracza");
                        Socket socket2 = server.accept();
                        System.out.println("Gracz 2 dolaczyl do serwera");

                        /*Teraz biore wiadomosc od klienta 2*/
                        ObjectInputStream checker2 = new ObjectInputStream(socket2.getInputStream());

                        /*Konwertuje na inta*/
                        a2 = (int) checker2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + a2);
                        b2 = (int) checker2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + b2);

                        /*Daje klientowi 1 odpowiedz*/
                        ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
                        oos1.writeObject(a2);
                        oos1.writeObject(b2);

                        /*Daje klientowi 2 odpowiedz*/
                        ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
                        oos2.writeObject(a1);
                        oos2.writeObject(b1);




                        /*Konce obcowanie z klientem drugim*/
                        /*zamykam wszystkie zrodla*/
                        checker1.close();
                        oos1.close();
                        socket1.close();
                        checker2.close();
                        oos2.close();
                        socket2.close();
                        /*Sprawdzam czy to juz koniec naszej zabawy*/
                        if(a1==20 && a2==20 && b1==20 && b2==20) break;



                }

                /******************************************/
                /*Otwieram czat do rozmowy miedzy graczami*/
                /******************************************/

                /*Czekam na klienta 1*/
                System.out.println("Czekam na 1 gracza");
                Socket sockettP1 = server.accept();
                System.out.println("Gracz 1 dolaczyl do serwera");

                /*Czekam na klienta 2*/
                System.out.println("Czekam na 1 gracza");
                Socket sockettP2 = server.accept();
                System.out.println("Gracz 2 dolaczyl do serwera");

                String mes1,mes2;
                while(true){

                        /*Teraz biore wiadomosc od klienta 2*/
                        ObjectInputStream checker2 = new ObjectInputStream(sockettP2.getInputStream());

                        /*Konwertuje na String*/
                        mes2 = (String) checker2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + mes2);


                        /*Daje klientowi 1 odpowiedz*/
                        ObjectOutputStream oos1 = new ObjectOutputStream(sockettP1.getOutputStream());
                        oos1.writeObject(mes2);


                        /*Teraz biore wiadomosc od klienta 1*/
                        ObjectInputStream checker1 = new ObjectInputStream(sockettP2.getInputStream());

                        /*Konwertuje na String*/
                        mes1 = (String) checker1.readObject();
                        System.out.println("Dostalem widomosc od 1 gracza: " + mes1);

                        /*Daje klientowi 2 odpowiedz*/
                        ObjectOutputStream oos2 = new ObjectOutputStream(sockettP2.getOutputStream());
                        oos2.writeObject(mes1);

                        if((mes1=="WIN" && mes2 =="LOSE") || (mes1=="LOSE" && mes2=="WIN")) break;


                }
                 System.out.println("Shutting down Socket server!!");
                //zamknij ServerSocket object
                server.close();
        }

}

