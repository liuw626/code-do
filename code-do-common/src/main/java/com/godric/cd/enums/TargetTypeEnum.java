package com.godric.cd.enums;

import lombok.Getter;

import java.util.Arrays;

public enum TargetTypeEnum {

    ARTICLE,
    COMMENT;

    public static boolean valid(String type) {
        return Arrays.stream(TargetTypeEnum.values()).anyMatch(t -> t.name().equalsIgnoreCase(type));
    }

}
