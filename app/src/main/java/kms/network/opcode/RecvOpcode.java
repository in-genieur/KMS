package kms.network.opcode;

/**
 *
 * @author
 */
public enum RecvOpcode {
    LOGIN_PASSWORD(0x00),
    CRC_DETECT_BAN(0x01),
    AUTO_REGISTER(0x02),
    PONG(0x03),
    CLOSE(0x04),
    CONNECTOR_CHAT(0x05),
    LOAD_PROCESS(0x06),
    LOAD_CHARINFO(0x07),
    LOAD_CHARLIST(0x08),
    SETTING_MAINCHAR(0x09),
    SECOND_LOGIN_PASSWORD(0xA),
    CONNECTOR_HELP(0xB),
    DISCONNECT_LOGIN(0xC),
    ADD_AUTH_LIST(0xD),
    PROCESS_DETECT_BAN(0xE),
    SKILL_CHECK(0xF)
    ;
    private int code = -2;

    public final int getValue() {
        return code;
    }

    private RecvOpcode(int code) {
        this.code = code;
    }
}

