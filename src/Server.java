import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {



    public static void main(String arg[])
    {

        Socket socket = null;                    //접속한 Client와 통신하기 위한 Socket
        User user = new User();                    //채팅방에 접속해 있는 Client 관리 객체
        ServerSocket server_socket=null;              //Client 접속을 받기 위한 ServerSocket

        int count = 0;
        ArrayList<Thread> thread = new ArrayList<>();
//        Thread thread[]= new Thread[10];            //접속하는 각각의 Client로부터 데이터를 읽어들이고 데이터전송

        try {
            server_socket = new ServerSocket(12345);


            //Server의 메인쓰레드는 게속해서 사용자의 접속을 받음
            while(true)
            {
                //클라이언트가 접속이완료되면 클라이언트소켓객체를 반환한다.
                socket = server_socket.accept();

                //접속 한 사용자와 통신 할 수 있는 socket을 가지는 Receiver 쓰레드를 생성 및 시작.
                thread.add(new Thread(new Receiver(user,socket)));
                thread.get(count).start();
                count++;
            }
        }catch(Exception e) {};
    }
}
