package cn.lefer.august.object;

import java.util.Map;

/**
 * 返回视图对象封装
 *
 * @author fangchao
 * @since 2018-09-10 14:40
 **/
public class View {
    private String path;
    private Map<String,Object> model;

    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public View addModel(String key,Object value){
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
