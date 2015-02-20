package trakkeri;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Peer {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Arrays.hashCode(this.ip);
        hash = 79 * hash + this.port;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Peer other = (Peer) obj;
        if (!Arrays.equals(this.ip, other.ip)) {
            return false;
        }
        if (this.port != other.port) {
            return false;
        }
        return true;
    }

    private final byte[] ip;
    private final int port;

    public Peer(byte[] ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Byte[] peerinfocompact() {
        Byte[] ret = new Byte[6];
        byte[] address = ip;
        for (int i = 0; i < 4; i++) {
            ret[i] = address[i];
        }

        byte[] portbytes = ByteBuffer.allocate(4).putInt(port).array();
        ret[4] = portbytes[2];
        ret[5] = portbytes[3];
        return ret;
    }
}
