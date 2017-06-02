import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Hello World!");
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.print("计算机名称：" + localHost.getHostName());
        System.out.print("IP地址：" + localHost.getHostAddress());
        byte[] address = localHost.getAddress();
        System.out.print("字节数组形式的ip：" + Arrays.toString(address));
        System.out.print(localHost);
//        InetAddress.getByName("");



    }
}
