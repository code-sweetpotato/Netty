import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client2 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8888);

        if(!socketChannel.connect(address)){//连接服务器不成功
            while (!socketChannel.finishConnect()){
                //循环查询服务端的连接是否断开,没有断开则做点其他的事情
                System.out.println("客户端正在与其他服务器连接,请稍等......");
            }

        }

        ByteBuffer buffer = ByteBuffer.wrap("Hello,NIO  double".getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
