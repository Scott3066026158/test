package com.kunyi.bitamexJava.model;


/**
 * 返回消息包装对象
 *
 * @author GAIA
 * @create 2019-05-14-17:36
 */
public class JsonMessage{

    /**
     * 状态码 200表示成功，250表示失败
     */
    private int code;

    /**
     * 消息说明
     */
    private String msg;

    /**
     * 存放主体的json数据
     */
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toJson(){
        StringBuilder sBuilder = new StringBuilder();
        if(code != 0){
            sBuilder.append("{\"code\":");
            sBuilder.append(code);
        }
        if(msg != null && !"".equals(msg)){
            sBuilder.append(",\"msg\":\"");
            sBuilder.append(msg);
        }
        if(data != null && !"".equals(data)){
            sBuilder.append("\",\"data\":[");
            sBuilder.append(data);
            sBuilder.append("]");
        }else{
            sBuilder.append("\"");
        }
        sBuilder.append("}");
        return sBuilder.toString();
    }
}
