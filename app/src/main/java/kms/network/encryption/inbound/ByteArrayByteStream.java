package kms.network.encryption.inbound;

import kms.util.HexTool;

import java.io.IOException;

/**
 * Provides for an abstraction layer for an array of bytes.
 *
 * @author Frz
 * @version 1.0
 * @since Revision 326
 */
public class ByteArrayByteStream {

    private int pos = 0;
    private long bytesRead = 0;
    private final byte[] arr;

    public ByteArrayByteStream(final byte[] arr) {
        this.arr = arr;
    }

    public long getPosition() {
        return pos;
    }

    public void seek(final long offset) throws IOException {
        pos = (int) offset;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public int readByte() {
        bytesRead++;
        return ((int) arr[pos++]) & 0xFF;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(final boolean b) {
        String nows = "";
        if (arr.length - pos > 0) {
            byte[] now = new byte[arr.length - pos];
            System.arraycopy(arr, pos, now, 0, arr.length - pos);
            nows = HexTool.toString(now);
        }
        if (b) {
            return "All: " + HexTool.toString(arr) + "\nNow: " + nows;
        } else {
            return "Data: " + nows;
        }
    }

    public long available() {
        return arr.length - pos;
    }
}
