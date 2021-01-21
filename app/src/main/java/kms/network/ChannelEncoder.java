package kms.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import kms.network.encryption.cross.Encryption;
import kms.object.player.Player;

import java.util.concurrent.locks.Lock;

public class ChannelEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] pData, ByteBuf buffer) throws Exception {
        final Player player = ctx.channel().attr(Player.PLAYER_KEY).get();

        if (player != null) {
            final Lock mutex = player.getLock();

            mutex.lock();
            try {
                final Encryption send_crpyto = player.getRecvCrypto();
                buffer.writeBytes(send_crpyto.encrypt(pData));
            } finally {
                mutex.unlock();
            }
        } else {
            buffer.writeByte((byte) 0xFF);
            buffer.writeBytes(pData);
        }
        ctx.flush();
    }
}
