import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Created by zqh on 2017/7/16.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf firstMessage;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] data = "发送给服务器信息".getBytes();
        firstMessage = Unpooled.buffer();
        firstMessage.writeBytes(data);
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
        System.out.println("客户端收到服务器数据:" + rev);
    }

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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        // 出现异常时关闭连接。
        cause.printStackTrace();
        ctx.close();
    }
}
