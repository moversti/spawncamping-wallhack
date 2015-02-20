/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trakkeri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author arvy
 */
public class BencodeableArray implements BencodeableObject {

    private final BencodeableObject[] a;

    public BencodeableArray(BencodeableObject[] a) {
        this.a = a;
    }

    @Override
    public byte[] bencode() {
        ArrayList<Byte> al = new ArrayList<>(a.length * 10);
        for (BencodeableObject b : a) {
            byte[] bencode = b.bencode();
            Byte[] benobj = new Byte[bencode.length];
            for (int i = 0; i < bencode.length; i++) {
                benobj[i] = bencode[i];
            }
            al.addAll(Arrays.asList(benobj));
        }
        Iterator<Byte> iterator = al.iterator();
        byte[] ret = new byte[al.size() + 2];
        int i = 1;
        ret[0] = 'l';
        ret[al.size() + 1] = 'e';
        while (iterator.hasNext()) {
            byte next = iterator.next();
            ret[i] = next;
            i++;
        }
        return ret;
    }

}
