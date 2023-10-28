
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class Send implements Runnable{
    DataOutputStream out;
    BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));
    public Send(DataOutputStream out)
    {
        this.out = out;
    }
    public void run()
    {
        while(true)
        {
            // 클라이언트으이 서브 쓰레드는 키보드로부터 입력받은 내용 서버로 전송하는 것만 계속해서 반복.
            try
            {
                String msg = in2.readLine();    //키보드로부터 입력을 받음
                out.writeUTF(msg);                //서버로 전송
            }catch(Exception e) {}
        }
    }
}