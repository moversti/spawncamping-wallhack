/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trakkeri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author arvy
 */
public class BencodeableMap implements BencodeableObject {

    private final SortedMap<BencodeableByteString, BencodeableObject> map;

    public BencodeableMap() {
        map = new TreeMap<>();
    }

    public void put(BencodeableByteString k, BencodeableObject v) {
        map.put(k, v);
    }

    @Override
    public byte[] bencode() {
        Set<Map.Entry<BencodeableByteString, BencodeableObject>> entrySet = map.entrySet();
        Iterator<Map.Entry<BencodeableByteString, BencodeableObject>> iterator = entrySet.iterator();
        ArrayList<Byte> al = new ArrayList<>(entrySet.size() * 10);
        while (iterator.hasNext()) {
            Map.Entry<BencodeableByteString, BencodeableObject> next = iterator.next();
            byte[] keyb = next.getKey().bencode();
            byte[] valb = next.getValue().bencode();
            Byte[] keybo = new Byte[keyb.length];
            Byte[] valbo = new Byte[valb.length];
            for (int i = 0; i < keyb.length; i++) {
                keybo[i] = keyb[i];
            }
            for (int i = 0; i < valb.length; i++) {
                valbo[i] = valb[i];
            }
            al.addAll(Arrays.asList(keybo));
            al.addAll(Arrays.asList(valbo));
        }

        Iterator<Byte> ali = al.iterator();
        byte[] ret = new byte[al.size() + 2];
        int i = 1;
        ret[0] = 'd';
        ret[al.size() + 1] = 'e';
        while (ali.hasNext()) {
            byte next = ali.next();
            ret[i] = next;
            i++;
        }
        return ret;
    }

}
