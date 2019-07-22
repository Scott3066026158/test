package com.gaia.autotrade.owsock.listener;


public interface StateListener {
    void OnLogCallBack(final String time, final String log);
    void OnStateCallBack();
}
