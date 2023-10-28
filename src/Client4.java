import java.io.*;
import java.net.Socket;

public class Client4 {
    public static void main(String[] arg)
    {
        Socket socket = null;            //Server와 통신하기 위한 Socket
        DataInputStream in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
        BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
        DataOutputStream out = null;    //서버로 내보내기위한 스트림

        try {

            socket = new Socket("127.0.0.1",12345);    //서버로 접속

            in = new DataInputStream(socket.getInputStream());            //서버로부터 데이터 읽어들이기 위한 스트림 생성
            in2 = new BufferedReader(new InputStreamReader(System.in)); //채팅을 위해 사용자 키보드로부터 데이터 읽어들이기 위한 스트림 생성
            out = new DataOutputStream(socket.getOutputStream());        //채팅 내용을 서버로 전송하기 위한 출력 스트림

            //채팅에 사용 할 닉네임을 입력받음
            System.out.print("닉네임을 입력해주세요 : ");
            String data = in2.readLine();
            //서버로 닉네임을 전송
            out.writeUTF(data);

            System.out.print("방번호를 입력해주세요 : ");
            String room = in2.readLine();
            out.writeUTF(room);



            //사용자가 채팅 내용을 입력 및 서버로 전송하기 위한 쓰레드 생성 및 시작
            Thread th = new Thread(new Send(out));
            th.start();
        }catch(IOException e) {}
        try {
            //클라이언트의 메인 쓰레드는 서버로부터 데이터 읽어들이는 것만 반복.
            while(true)
            {
                String str2 = in.readUTF();
                System.out.println(str2);
            }
        }catch(IOException e) {}
    }
}