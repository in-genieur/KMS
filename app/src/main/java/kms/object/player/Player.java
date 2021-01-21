package kms.object.player;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import kms.network.encryption.cross.Encryption;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    public final static AttributeKey<Player> PLAYER_KEY = AttributeKey.valueOf("player_netty");
    private final transient Lock mutex = new ReentrantLock(true);
    private Encryption sendCrypto, recvCrypto;
    private Channel channel;

    public Player(Channel channel, Encryption sendCrypto, Encryption recvCrypto) {
        this.channel = channel;
        this.sendCrypto = sendCrypto;
        this.recvCrypto = recvCrypto;
    }

    public final Lock getLock() {
        return mutex;
    }

    public final Encryption getSendCrypto() {
        return sendCrypto;
    }

    public final Encryption getRecvCrypto() {
        return recvCrypto;
    }
}
