package com.namchain.lib.ss;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String [] args) {

        SecureRandom random = new SecureRandom();
        Scheme s = new Scheme(random,5,3);
        Map<Integer, byte[]> secret= s.split("Ramaguru".getBytes());
        System.out.println("The secret is " + "Ramaguru Radhakrishnan".getBytes());
        System.out.println("The test recovery is " + new String("Ramaguru Radhakrishnan".getBytes()));

        for (int i=1; i<= secret.size();i++) {
            String tmp = new String(secret.get(i));
            System.out.println("Share " + (i) + " is " + tmp);
            System.out.println("**********************************");
        }
        Map<Integer, byte[]> parts = new HashMap<>();
        parts.put(1,secret.get(1));
        parts.put(2,secret.get(2));
        parts.put(3,secret.get(3));

        String out = new String(s.join(parts));
        System.out.println("The secret is " + out);

    }
}
