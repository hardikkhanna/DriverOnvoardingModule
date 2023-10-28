package com.module.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class ApplicationUtilsTest {

    @Mock
    private DigestUtils digestUtils;


    @Test
    public void testDecodeMd5Hash() {
        String key = "sampleKey";
        String expectedResult = "a6cd4cf48235113d23e3da601f2be5aa";

        when(digestUtils.md5Hex(key)).thenReturn(expectedResult);

        String result = ApplicationUtils.decodeMd5Hash(key);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDecodeMd5HashWithEmptyKey() {
        String key = "";
        String expectedResult = "d41d8cd98f00b204e9800998ecf8427e"; // MD5 hash for an empty string

        when(digestUtils.md5Hex(key)).thenReturn(expectedResult);

        String result = ApplicationUtils.decodeMd5Hash(key);

        assertEquals(expectedResult, result);
    }

}
