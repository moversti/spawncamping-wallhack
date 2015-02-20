/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trakkeri;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arvy
 */
public class BencodeableByteString implements BencodeableObject, Comparable<BencodeableByteString> {

    private final byte[] bytes;

    public BencodeableByteString(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public BencodeableByteString(String s) throws UnsupportedEncodingException {
        bytes = s.getBytes("ASCII");
    }

    @Override
    public byte[] bencode() {
        byte[] ret = null;
        try {
            int bytepituus = bytes.length;
            int pituusmerkkipituus = (int) Math.log10(bytepituus) + 1;
            String pituusmerkit = String.valueOf(bytepituus);
            byte[] pituusmerkitbytes = pituusmerkit.getBytes("ASCII");
            ret = new byte[bytepituus + pituusmerkkipituus + 1];
            for (int i = 0; i < pituusmerkkipituus; i++) {
                ret[i] = pituusmerkitbytes[i];
            }
            ret[pituusmerkkipituus] = ':';
            for (int i = 0; i < bytepituus; i++) {
                ret[i + pituusmerkkipituus + 1] = bytes[i];
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BencodeableByteString.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public int compareTo(BencodeableByteString o) {
        try {
            String s1 = new String(bytes, "ASCII");
            String s2 = new String(o.getBytes(), "ASCII");
            return s1.compareTo(s2);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BencodeableByteString.class.getName()).log(Level.SEVERE, null, ex);
        }
        String s1 = new String(bytes);
        String s2 = new String(o.getBytes());
        return s1.compareTo(s2);
    }

}
