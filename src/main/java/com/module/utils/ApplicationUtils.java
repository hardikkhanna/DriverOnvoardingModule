package com.module.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class ApplicationUtils {

    public static String decodeMd5Hash(String key) {
        return DigestUtils.md5Hex(key);
    }

}
