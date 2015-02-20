package trakkeri;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arvy
 */
public class Trakkeri {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TrackerServer trackerServer = new TrackerServer(3396);
        trackerServer.ranaa();
//testaile();
    }

    public static void testaile() {
        try {
            BencodeableByteString bba = new BencodeableByteString("asd");
            System.out.println(new String(bba.bencode(), "ASCII"));
            BencodeableInteger bi = new BencodeableInteger(123);
            System.out.println(new String(bi.bencode(), "ASCII"));
            BencodeableObject[] boa = {bba, bi, new BencodeableByteString("ZXCZXC")};
            BencodeableArray ba = new BencodeableArray(boa);
            System.out.println(new String(ba.bencode(), "ASCII"));
            BencodeableMap bm = new BencodeableMap();
            bm.put(bba, bi);
            System.out.println(new String(bm.bencode(), "ASCII"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Trakkeri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
