package com.firespider.spidersql.aio.net.core;

import java.nio.channels.CompletionHandler;

/**
 * Created by stone on 2017/9/17.
 */
public class WriteToChannelHandler implements CompletionHandler<Integer, Session> {

    @Override
    public void completed(Integer result, Session session) {
        session.readFromChannel(result, true);
    }

    @Override
    public void failed(Throwable exc, Session session) {
        session.handleFail();
    }
}
