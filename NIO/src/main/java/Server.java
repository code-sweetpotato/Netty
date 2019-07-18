

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;


public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(8888);
        //监听端口
        channel.bind(address);
        Selector selector = Selector.open();

        //selector与channel,同时关注连接事件
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //selector去轮询,每隔一秒钟进行轮询
            if(selector.select(1000) == 0){
                //当前没有可以处理的事情,可编写其他的的代码
                System.out.println("当前没有可处理的事情");
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    System.out.println("有客户端进行连接.....");
                    //服务器channel接受这个channel
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    Thread.sleep(10000);
                    //channel与selector连接起来,同时关注可读事件
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }

                if(key.isReadable()){
                    //有可读事件发生
                    System.out.println("客户端发消息过来了....");
                    //拿到channel
                    SocketChannel channel1 = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel1.read(buffer);
                    Thread.sleep(20000);
                    System.out.println(new String(buffer.array()));
                }
                iterator.remove();
            }

        }



    }

}
