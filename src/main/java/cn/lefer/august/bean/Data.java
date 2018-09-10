package cn.lefer.august.bean;

/**
 * 返回数据对象封装
 *
 * @author fangchao
 * @since 2018-09-10 14:46
 **/
public class Data {
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
