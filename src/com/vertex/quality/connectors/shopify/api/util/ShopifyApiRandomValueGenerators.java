package com.vertex.quality.connectors.shopify.api.util;

import com.vertex.quality.common.utils.VertexLogger;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

/**
 * Generates random values in different data-types
 *
 * @author Shivam.Soni
 */
public class ShopifyApiRandomValueGenerators {

    /**
     * Generates random numbers in different length
     *
     * @param length small, medium, long
     * @return random numbers
     */
    public static String generateRandomNumbers(String length) {
        String generatedNo;
        switch (length.toLowerCase(Locale.ROOT)) {
            case "small":
                generatedNo = String.valueOf(ThreadLocalRandom.current().nextInt(99));
                break;
            case "medium":
                generatedNo = String.valueOf(ThreadLocalRandom.current().nextInt(99999));
                break;
            case "long":
                generatedNo = String.valueOf(ThreadLocalRandom.current().nextInt(999999999));
                break;
            default:
                generatedNo = String.valueOf(ThreadLocalRandom.current().nextInt(999999));
                break;
        }
        return generatedNo;
    }

    /**
     * Generates random special characters
     *
     * @param len length of the String
     * @return Random Special Characters
     */
    public static String generateRandomSpecialCharacters(int len) {
        Random r = new Random();
        char[] choices = ("!@#$%^*()%$%$@#$@)?/(^$@!").toCharArray();
        StringBuilder salt = new StringBuilder(len);
        for (int i = 0; i < len; ++i)
            salt.append(choices[r.nextInt(choices.length)]);
        return salt.toString();
    }

	public static String generateRandomAlphaNumericCharacters(){
		UUID randomUUID = UUID.randomUUID();
		VertexLogger.log(randomUUID.toString().replaceAll("-", ""));
		return randomUUID.toString().replaceAll("-", "");
	}

}
