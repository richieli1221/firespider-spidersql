package com.firespider.spidersql.action.model;

import com.firespider.spidersql.lang.json.GenJsonObject;

import java.util.Map;

/**
 * Created by xiaotong.shi on 2017/9/27.
 */
public class GetParam extends Param {
    private String url;
    private String charset;

    // TODO: 2017/9/28 定义正则解析模型 
    private Map<String,String[]> regrexMap;
    private Map<String,String> header;

    public GetParam(GenJsonObject element){
        this.url = element.get("url").getAsString();
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}