package cn.lefer.august.object;

import java.util.Map;

/**
 * 请求参数封装
 *
 * @author fangchao
 * @since 2018-09-10 14:39
 **/
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
