
import com.google.gson.Gson;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.*;
import java.net.Socket;

public class Receiver implements Runnable{

    Socket socket;
    DataInputStream in;

    String name;

    User user;

    public Receiver(User user,Socket socket) throws Exception
    {
        this.user = user;
        this.socket = socket;
        //접속한 Client로부터 데이터를 읽어들이기 위한 DataInputStream 생성
        in = new DataInputStream(socket.getInputStream());

        //최초 사용자로부터 닉네임을 읽어들임
        this.name = in.readUTF();
        //최초 사용자로부터 방번호를 읽어들임
//        this.room = in.readUTF();
        //사용자 추가해줍니다.
        user.AddClient(name, socket);

    }


    public void run()
    {
        try
        {
            //Receiver는 사용자로부터 넘어오는 데이터를 읽어와 채팅방에 있는 사용자들에게 전송하는 것을 반복합니다.
            while(true) {

                String msg = in.readUTF(); //들어오는 데이터를 읽는다
                System.out.println("msg : " + msg);
                if (msg.equals("read")) { //읽음 메세지라면
                    String read = in.readUTF();

                    Gson gson = new Gson();
                    read readOBJ = gson.fromJson(read, read.class);
                    user.readMSG(readOBJ);
                } else if(msg.equals("news")){ //뉴스메세지라면
                    String news = in.readUTF();
//                    System.out.println("news : " + news);
                    Gson gson = new Gson();
                    News newsOBJ = gson.fromJson(news,News.class);
                    user.newsMSG(newsOBJ,news);
                }
                else { //읽음 메세지가 아니면
                    user.sendMsg(msg, name); //서버에서는 데이터가 들어오면 유저들에게 메세지를 보낸다
                }

            }
        }catch(Exception e) {
            //Exception이 발생했다는 건 사용자가 접속을 끊었다는 의미입니다. 채팅방에서 사용자를 제거합니다.
            System.out.println(e);
            user.RemoveClient(this.name);
        }
    }
}