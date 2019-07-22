package com.gaia.autotrade.owsock.listener;

/**
 * 最新数据监听
 */
public interface LatestDataListener {
    void OnLatestDataCallBack(final String code);
}
