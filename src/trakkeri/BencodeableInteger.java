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
public class BencodeableInteger implements BencodeableObject {

    private final int i;

    public BencodeableInteger(int i) {
        this.i = i;
    }

    @Override
    public byte[] bencode() {
        byte[] ret = null;
        try {
            String s = String.valueOf(i);
            String sret = 'i' + s + 'e';
            ret = sret.getBytes("ASCII");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BencodeableInteger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

}
