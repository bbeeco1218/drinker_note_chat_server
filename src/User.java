
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

public class User {

    HashMap<String,DataOutputStream> clientmap = new HashMap<String,DataOutputStream>();    //채팅방의 사용자 관리 위한 Hashmap
    HashMap<String,String> roomMap = new HashMap<String,String>();    //채팅방의 사용자 관리 위한 Hashmap


    String url = "jdbc:mysql://13.209.19.188:3306/DrinkerNote";
    String userName = "";
    String password = "";

    public synchronized void AddClient(String name,Socket socket)            //채팅방 사용자 추가 및
    {                                                                        //채팅방에 남아있는 사용자에게 접속 소식을 알립니다.
        try {
            //클라이언트를 추가한다.
            clientmap.put(name, new DataOutputStream(socket.getOutputStream()));

            //방을 추가 한다
//            roomMap.put(name,room);

            //해당 방의 클라이언트들에게 유저가 입장했다고 알림
//            sendMsg(name+" 입장하셨습니다. " + room+"번방","Server",room);
            //서버 콘솔에 총 채팅참여인원을 찍는다.
            System.out.println("채팅 참여 인원 : "+clientmap.size());
        }catch(Exception e){}

    }


    public synchronized void RemoveClient(String name)  //채팅방 사용자 제거 및 채팅방에 존재하는 Client에게 퇴장 소식을 알림
    {
        try {
            //클라이언트 해쉬맵을 지운다
            clientmap.remove(name);
//            roomMap.remove(name);
            //해당방의 클라이언트에게 유저가 퇴장했다고 메세지 보냄
//            sendMsg(name + " 퇴장하셨습니다.", "Server",room);
            //서버 콘솔에 총 채팅참여인원을 찍는다
            System.out.println("채팅 참여 인원 : "+clientmap.size());
        }catch(Exception e) {}
    }

    public synchronized void sendMsg(String msg, String name)throws Exception //채팅방에 있는 사용자에게 메세지를 전송
    {

        Gson gson = new Gson();
        MSG MSG = gson.fromJson(msg, MSG.class);
        DataOutputStream output = clientmap.get(MSG.getTargetID());
        if(output != null){
            output.writeUTF(msg);
        }
        String sql;
        if(!MSG.isCheckimg()) { //이미지가 아니라면
            //데이터베이스에 메세지를 업데이트시켜줘야한다
            sql = "INSERT INTO Chat_message(Room_key,Send_UserID,message_contents) VALUES(" + MSG.getRoomNum() + ",'" + MSG.getMyID() + "','" + MSG.getMsgs().get(0) + "')";
            System.out.println(sql);
            sendsql(sql);
        }
            sql = "UPDATE ChatRoom_User SET lately_date=now(),Check_Out=0 WHERE Room_Key=" + MSG.getRoomNum() + ";";
            sendsql(sql);
            sql = "UPDATE ChatRoom SET Room_PeopleNum=2 WHERE RoomKey=" + MSG.getRoomNum() + ";";
            sendsql(sql);
    }


    public synchronized void readMSG(read read)throws Exception //채팅방에 있는 사용자에게 메세지를 전송
    {
        //상대방에게만 메세지를 보낸다
        DataOutputStream output = clientmap.get(read.getTo());
        //상대방에게 읽었다는 표시를 줘야한다.
        if(output != null){
            output.writeUTF("read"); //읽음이라는 메세지를 주고
            output.writeUTF(String.valueOf(read.getRoomNum())); //방번호를 보내준다.
        }
        //데이터베이스의 메세지들을 읽음으로 업데이트 해준다.
        String sql = "UPDATE Chat_message SET readnum=0 WHERE Room_key="+read.getRoomNum()+" AND Send_UserID='"+read.getTo()+"' AND readnum!=0;"; //데이터베이스의 메세지들을 읽음으로 바꿔준다.
        sendsql(sql);
    }


    public synchronized void newsMSG(News news,String newsjson)throws Exception //채팅방에 있는 사용자에게 메세지를 전송
    {
        //상대방에게만 메세지를 보낸다
        DataOutputStream output = clientmap.get(news.getToID());
        //상대방에게 뉴스메세지를 보내준다
        if(output != null){
            output.writeUTF("news"); //뉴스라는 메세지를 주고
            output.writeUTF(newsjson); //뉴스제이선을 보내준다
        }

    }

    public void sendsql(String sql) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

}




