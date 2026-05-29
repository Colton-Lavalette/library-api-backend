package com.colton.library_api.util;

import java.security.SecureRandom;

public class CodeGenerator {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int MEMBER_CODE_LENGTH = 8;
    private static final int COPY_CODE_LENGTH = 10;

    public static String generateMemberCode() {
        return generate("MEM", MEMBER_CODE_LENGTH);
    }

    public static String generateCopyCode() {
        return generate("BC", COPY_CODE_LENGTH);
    }

    private static String generate(String prefix, int length) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append("-");

        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        return sb.toString();
    }
}