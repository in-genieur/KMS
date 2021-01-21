package kms.network.opcode;

/**
 *
 * @author
 */
public enum SendOpcode {
    LOGIN(0x00),
    MESSAGE_BOX(0x01),
    REGISTER(0x02),
    END(0x03),
    PING(0x04),
    INGAME_CHAT(0x05),
    HAPPY_NARU(0x07),
    PROCESS_END(0x08),
    USER_LIST(0xA),
    SHUVI(0xB),
    CHAR_INFO(0xC),
    CHAR_LIST(0xD),
    SECOND_LOGIN(0xE),
    LOGIN_TOKEN(0xF),
    PROCESS_KILL(0x10),
    SKILL_CHECK(0x11)

    ;

    private int code = -2;

    private SendOpcode(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }
}
