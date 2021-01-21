package kms.network.handler;

import kms.network.encryption.inbound.LittleEndianAccessor;
import kms.network.opcode.RecvOpcode;
import kms.object.player.Player;

public class LoginHandler {

    public static final void HandlePacket(final RecvOpcode header, final LittleEndianAccessor slea, final Player player) throws InterruptedException {
        switch (header) {
            default:
                System.out.println("[UNHANDLED] Recv [" + header + "] found");
                break;
        }

    }
}

