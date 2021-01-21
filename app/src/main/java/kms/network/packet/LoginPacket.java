package kms.network.packet;

import kms.app.Settings;
import kms.network.encryption.outbound.MaplePacketLittleEndianWriter;
import kms.network.opcode.SendOpcode;

public class LoginPacket {

    private static final String version;

    static {
        int ret = 0;
        ret ^= (Settings.MAPLE_VERSION & 0x7FFF);
        ret ^= (Settings.MAPLE_CHECK << 15);
        ret ^= ((Settings.MAPLE_PATCH & 0xFF) << 16);
        version = String.valueOf(ret);
    }

    public static final byte[] sendHandShake(byte[] sendIv, byte[] recvIv) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        int packetsize = 13 + version.length();
        mplew.writeShort(packetsize);
        mplew.writeShort(291); //KMS Static
        mplew.writeMapleAsciiString(version);
        mplew.write(recvIv);
        mplew.write(sendIv);
        mplew.write(2); // 1 = KMS, 2 = KMST, 7 = MSEA, 8 = GlobalMS, 5 = Test Server
        //System.out.println(HexTool.toString(mplew.getPacket()));
        return mplew.getPacket();
    }

    public static final byte[] getPing() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(2);

        mplew.writeOpcode(SendOpcode.PING.getValue());

        return mplew.getPacket();
    }
}

