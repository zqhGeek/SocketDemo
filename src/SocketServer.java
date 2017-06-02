import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zqh on 2017/5/30.
 */
public class SocketServer {
    public static void main(String[] args) {
        try {
            /** 创建ServerSocket*/
            // 创建一个ServerSocket在端口2013监听客户请求
            ServerSocket serverSocket = new ServerSocket(2013);
            while (true) {
                System.out.println("Build Socket");
                // 侦听并接受到此Socket的连接,请求到来则产生一个Socket对象，并继续执行
                Socket socket = serverSocket.accept();
                System.out.println(" Socket accept");

                /** 获取客户端传来的信息 */
                // 由Socket对象得到输入流，并构造相应的BufferedReader对象
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                // 获取从客户端读入的字符串

                String result = null;
                while ((result = bufferedReader.readLine()) != null) {
                    System.out.println("Client say : " + result);
                }

                /** 发送服务端准备传输的 */
                // 由Socket对象得到输出流，并构造PrintWriter对象
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("hello Client, I am Server!");
                printWriter.flush();

                /** 关闭Socket*/
                printWriter.close();
                bufferedReader.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        } finally {
            System.out.println(" Socket finally");

//          serverSocket.close();
        }
    }
}
