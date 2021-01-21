package kms.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import kms.app.Settings;
import kms.network.encryption.cross.Encryption;
import kms.network.encryption.inbound.LittleEndianAccessor;
import kms.network.opcode.RecvOpcode;
import kms.network.packet.LoginPacket;
import kms.object.player.Player;
import kms.util.Randomizer;

public class NettyHandler extends SimpleChannelInboundHandler<LittleEndianAccessor> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final String IP = ctx.channel().remoteAddress().toString().split(":")[0];
        long time = System.currentTimeMillis();

        System.out.println(IP + " 가 접속했습니다.");
        final byte serverRecv[] = new byte[]{(byte) 0x65, (byte) 0x56,
                (byte) 0x12, (byte) 0xfd};
        final byte serverSend[] = new byte[]{(byte) 0x2f, (byte) 0xa3,
                (byte) 0x65, (byte) 0x43};

        final byte ivRecv[] = serverRecv;
        final byte ivSend[] = serverSend;

        byte realIvRecv[] = new byte[4];
        byte realIvSend[] = new byte[4];
        System.arraycopy(ivRecv, 0, realIvRecv, 0, 4);
        System.arraycopy(ivSend, 0, realIvSend, 0, 4);

        Encryption send = new Encryption (realIvSend, (short) (0xFFFF - Settings.MAPLE_VERSION));
        Encryption recv = new Encryption (realIvRecv, Settings.MAPLE_VERSION);

        final Player player = new Player(ctx.channel(), send, recv);
        ctx.write(LoginPacket.sendHandShake(ivSend, ivRecv));
        ctx.flush();
        ctx.channel().attr(Player.PLAYER_KEY).set(player);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Player player = ctx.channel().attr(Player.PLAYER_KEY).get();
        if (player != null) {

        }
        ctx.channel().attr(Player.PLAYER_KEY).set(null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable ex) {
        ex.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) event;
            final Player player = ctx.channel().attr(Player.PLAYER_KEY).get();
            if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
                if (player != null) {

                }
            } else if (e.state() == IdleState.WRITER_IDLE) {

            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LittleEndianAccessor slea) throws Exception {
        final Player player = (Player) ctx.channel().attr(Player.PLAYER_KEY).get();
        final byte header = slea.readByte();
        try {
            for (final RecvOpcode recv : RecvOpcode.values()) {
                if (recv.getValue() == header) {
                    LoginHandler.HandlePacket(recv, slea, player);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return;
    }
}
