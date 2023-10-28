public class read {
    int RoomNum;
    String to;

    public int getRoomNum() {
        return RoomNum;
    }

    public void setRoomNum(int roomNum) {
        RoomNum = roomNum;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public read(int roomNum, String to) {
        RoomNum = roomNum;
        this.to = to;
    }
}
