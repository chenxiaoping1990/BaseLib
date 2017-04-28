package com.lib.tab.listener;

public interface OnTabSelectListener {
    void onTabSelect(int position);

    void onTabReselect(int position);

    boolean isLogin(int position);
}