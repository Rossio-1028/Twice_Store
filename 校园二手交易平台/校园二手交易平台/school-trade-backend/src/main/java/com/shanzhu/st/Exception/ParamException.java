package com.shanzhu.st.Exception;

import java.util.Map;

/**
 * 参数异常
 *
 */
public class ParamException extends RuntimeException{
    private Map map;

    public ParamException(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }
}
