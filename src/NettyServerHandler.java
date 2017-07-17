import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by zqh on 2017/7/16.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每当从客户端收到新的数据时，这个方法会在收到消息时被调用
     * @param ctx
     * @param msg 是一个引用计数对象，这个对象必须显示地调用 release() 方法来释放。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
        ByteBuf buf = (ByteBuf) msg;
        String recieved = getMessage(buf);
        System.out.println("服务器接收到消息：" + recieved);
        ctx.writeAndFlush(getSendByteBuf("Message"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * ，一旦一个新的连接建立了,回调该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器已与客户端建立连接");
        super.channelActive(ctx);
    }

    /**
     * 即当 Netty 由于 IO错误或者处理器在处理事件时抛出的异常时。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常时关闭连接。
        System.out.println("服务器断开客户端连接");
        cause.printStackTrace();
        ctx.close();
    }

    /*
         * 从ByteBuf中获取信息 使用UTF-8编码返回
         */
    private String getMessage(ByteBuf buf) {

        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ByteBuf getSendByteBuf(String message) throws UnsupportedEncodingException {
        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);
        return pingMessage;
    }
}
