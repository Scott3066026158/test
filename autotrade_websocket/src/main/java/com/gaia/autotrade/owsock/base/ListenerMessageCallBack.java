package com.gaia.autotrade.owsock.base;

import com.gaia.autotrade.owsock.base.CMessage;

public interface ListenerMessageCallBack
{
    /**
     监听消息

     @param message 消息
     */
    void CallListenerMessageEvent (CMessage message);
}
