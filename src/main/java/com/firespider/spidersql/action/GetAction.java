package com.firespider.spidersql.action;

import com.firespider.spidersql.aio.net.http.HttpAsyncClient;
import com.firespider.spidersql.aio.net.http.Response;
import com.firespider.spidersql.lang.Gen;
import com.firespider.spidersql.lang.json.GenJsonArray;
import com.firespider.spidersql.lang.json.GenJsonElement;
import com.firespider.spidersql.lang.json.GenJsonObject;
import com.firespider.spidersql.lang.model.GetParam;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiaotong.shi on 2017/9/14.
 */
public class GetAction extends Action {
    private final HttpAsyncClient client;
    private GetParam param;

    public GetAction(Integer id, CompletionHandler<GenJsonElement, Integer> handler) throws IOException {
        super(id, handler);
        client = new HttpAsyncClient();
    }

    public GetAction(Integer id, GetParam element, CompletionHandler<GenJsonElement, Integer> handler) throws IOException {
        this(id, handler);
        this.param = element;

    }

    /***
     * 解析GetParam对象
     * @param param
     */
    private List<String> parse(GetParam param) {
        List<String> urlList = new ArrayList<>();
        if (param.getHeader() != null) {
            client.setHeader(param.getHeader());
        }
        if (param.getCharset() != null) {
            client.setCharset(Charset.forName(param.getCharset()));
        }
        return parseUrl(param.getUrl());

    }

    /***
     * 解析URL
     * 1. 普通链接
     * 2. www.XXX.%s.html(100-111) | www.XXX.%s.html(100,101,102)
     * @param url
     * @return
     */
    private List<String> parseUrl(String url) {
        List<String> urlList = new ArrayList<>();
        urlList.add(url);
        return urlList;
    }

    /***
     * 请求数据
     * todo 解析返回数据
     * @param urlList
     */
    private void handle(List<String> urlList) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(urlList.size());
        client.handleGet(urlList, new CompletionHandler<Response, Response>() {
            GenJsonObject obj = new GenJsonObject();
            @Override
            public void completed(Response result, Response attachment) {
                obj.addPrimitive("content",result.getBody());
                handler.completed(obj,result.getRequest().getUrl().hashCode());
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, Response attachment) {
                obj.addPrimitive("content",attachment.getBody());
                handler.completed(obj,attachment.getRequest().getUrl().hashCode());
                latch.countDown();
            }
        });
        latch.await();
        client.close();
    }

    @Override
    public void run() {
        List<String> urlList = parse(param);
        try {
            handle(urlList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("client close error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("latch await error");
        }
    }
}
