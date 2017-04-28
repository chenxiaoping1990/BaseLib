package com.ziizaa.login;


import com.lib.common.router.ClassName;
import com.lib.common.router.Key;

/**
 * Created by Ping on 2017/4/12 0012.
 */

public interface IntentService {
    @ClassName("com.ziizaa.login.LoginActivity")
    void stepToLoginActivity(@Key("name") String account, @Key("password") String password);

    @ClassName("com.ziizaa.main.PropertyMainActivity")
    void stepToPropertyMain(@Key("account") String account, @Key("password") String password);
}
