
package trakkeri;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.sun.net.httpserver.HttpHandler;

/**
 *
 * @author arvy
 */
public class TrackerHandler implements HttpHandler {

    private final HashMap<String, Set<Peer>> m;

    public TrackerHandler() {
        m = new HashMap<>();
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        String requestMethod = he.getRequestMethod();
        Headers requestHeaders = (Headers) he.getRequestHeaders();
        URI requestURI = he.getRequestURI();
        Headers responseHeaders = (Headers) he.getResponseHeaders();
        if (requestMethod.equals("GET")) {
            String query = requestURI.getQuery();
            if (query != null) {
                String[] split = query.split("&");
                Map<String, String> querymap = new HashMap<>();
                for (String split1 : split) {
                    String[] split2 = split1.split("=");
                    querymap.put(split2[0], split2[1]);
                }
                String infohash = querymap.get("info_hash");
                Set<Peer> peers = m.get(infohash);
                String ip = querymap.get("ip");
                String port = querymap.get("port");
                if (ip != null && port != null) {
                    if (peers == null) {
                        m.put(infohash, new HashSet<>());
                    }
                    String[] split1 = ip.split("\\.");
                    byte[] rawip = new byte[4];
                    for (int i = 0; i < 4; i++) {
                        rawip[i] = Short.valueOf(split1[i], 10).byteValue();

                    }
                    m.get(infohash).add(new Peer(rawip, Integer.parseInt(port)));
                }
                ArrayList<Byte> peersbal = new ArrayList<>();
                for (Peer peer : peers) {
                    peersbal.addAll(Arrays.asList(peer.peerinfocompact()));
                }
                byte[] peersbytes = new byte[peersbal.size()];
                Iterator<Byte> iterator = peersbal.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    Byte next = iterator.next();
                    peersbytes[i] = next;
                    i++;
                }
                BencodeableMap response = new BencodeableMap();
                response.put(new BencodeableByteString("interval"), new BencodeableInteger(3600));
                response.put(new BencodeableByteString("peers"), new BencodeableByteString(peersbytes));
                byte[] bencode = response.bencode();
                he.sendResponseHeaders(200, bencode.length);

                OutputStream responseBody = he.getResponseBody();
                BufferedOutputStream bos = new BufferedOutputStream(responseBody);
                bos.write(bencode, 0, bencode.length);
                bos.flush();
                bos.close();
                responseBody.close();

            }
        }
    }
}
