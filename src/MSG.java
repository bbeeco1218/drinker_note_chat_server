import java.util.ArrayList;

public class MSG {
    // TODO : 리사이클러뷰 아이템에 들어갈 텍스트

    ArrayList<String> Msgs;

    String myID; //보내는사람
    String targetID; //받는사람
    int RoomNum; //방번호
    boolean checkimg;



    public ArrayList<String> getMsgs() {
        return Msgs;
    }

    public void setMsgs(ArrayList<String> msgs) {
        Msgs = msgs;
    }

    public MSG(ArrayList<String> msg, String myID, String targetID, int roomNum, boolean checkimg) {
        this.Msgs = msg;
        this.myID = myID;
        this.targetID = targetID;
        RoomNum = roomNum;
        this.checkimg = checkimg;
    }


    public boolean isCheckimg() {
        return checkimg;
    }

    public void setCheckimg(boolean checkimg) {
        this.checkimg = checkimg;
    }


    public String getMyID() {
        return myID;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public int getRoomNum() {
        return RoomNum;
    }

    public void setRoomNum(int roomNum) {
        RoomNum = roomNum;
    }
}