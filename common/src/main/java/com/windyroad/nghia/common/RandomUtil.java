package com.windyroad.nghia.common;

import java.util.Random;

/**
 * Created by Nghia-PC on 7/13/2015.
 */
public class RandomUtil {
    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();
        //Random rand = new Random(seed);  // seed tạo ra random, cùng seed => random như nhau

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int range = (max - min) + 1;
        int randomNum = rand.nextInt(range) + min;

        return randomNum;
    }
}
