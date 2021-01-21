package kms.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

import kms.network.encryption.inbound.ByteArrayByteStream;
import kms.network.encryption.inbound.LittleEndianAccessor;
import kms.object.player.Player;

public class ChannelDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {
        final Player player = ctx.channel().attr(Player.PLAYER_KEY).get();
        if (player == null) {
            return;
        }
        buffer.markReaderIndex();

        byte[] decoded = new byte[buffer.readableBytes()];
        buffer.readBytes(decoded);
        buffer.markReaderIndex();

        list.add(new LittleEndianAccessor(new ByteArrayByteStream(player.getSendCrypto().decrypt(decoded))));
    }

}
