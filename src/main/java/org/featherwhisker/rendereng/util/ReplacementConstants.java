package org.featherwhisker.rendereng.util;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

public class ReplacementConstants {
    private static final Map<Integer, Integer> CONSTANTS = new HashMap<>();

    public static OptionalInt getReplacementConstant(int constant) {
        if(!CONSTANTS.containsKey(constant)) return OptionalInt.empty();
        return OptionalInt.of(CONSTANTS.get(constant));
    }

    static {
        int GL_DEPTH_COMPONENT32F = 36012;
        int GL_RGBA8 = 32856;
        int GL_RGBA = 6408;
        int GL_DEPTH_COMPONENT = 6402;

        CONSTANTS.put(GL_RGBA8, GL_RGBA);
        CONSTANTS.put(GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT32F);
    }
}