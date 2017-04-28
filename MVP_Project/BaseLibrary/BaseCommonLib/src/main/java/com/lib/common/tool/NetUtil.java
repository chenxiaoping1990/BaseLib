package com.lib.common.tool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 网络类型
 *
 * @JSONHelper.java
 * @description:
 * @author: allen
 * @version: 1.0.0
 * @modify:
 * @Copyright:
 * @since 2016/12/20
 */
public class NetUtil {
    /**
     * 中国电信ctwap
     */
    public static final String CTWAP = "ctwap";

    /**
     * 中国电信ctnet
     */
    public static final String CTNET = "ctnet";

    /**
     * 中国移动cmwap
     */
    public static final String CMWAP = "cmwap";

    /**
     * 中国移动cmnet
     */
    public static final String CMNET = "cmnet";

    /**
     * 3G wap 中国联通3gwap APN
     */
    public static final String GWAP_3 = "3gwap";

    /**
     * 3G net 中国联通3gnet APN
     */
    public static final String GNET_3 = "3gnet";

    /**
     * uni wap 中国联通uni wap APN
     */
    public static final String UNIWAP = "uniwap";

    /**
     * uni net 中国联通uni net APN
     */
    public static final String UNINET = "uninet";

    /**
     * APN数据访问地址
     */
    private static final Uri PREFERRED_APN_URI = Uri
            .parse("content://telephony/carriers/preferapn");

    static ConnectivityManager connectivity;
    public static int netType; // 网络的状态

    /**
     * 判断网络状态是否可用
     *
     * @return true: 网络可用 ; false: 网络不可用
     */
    public static final boolean isConnectInternet(Context context) {
        if (null == connectivity)
            connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
        if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错
            netType = networkInfo.getType();
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 设置Intetn的参数（【设置】-->【网络】）
     *
     * @param context 上下文
     * @return Intent
     */
    public static final Intent setNetClient(Context context) {
        Intent intentToNetwork = null;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intentToNetwork = new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intentToNetwork = new Intent();
            ComponentName component = new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings");
            intentToNetwork.setComponent(component);
            intentToNetwork.setAction("android.intent.action.VIEW");
        }
        return intentToNetwork;
    }

    /**
     * WIFI是否可用
     *
     * @param context 上下文
     */
    public static final boolean isWifiEnabled(Context context) {

		/* 下面代码可用 */
        if (null == connectivity)
            connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI")
                            && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否为3G网络，只有网络接入点是cmnet、ctnet和3gnet才是3G网络
     *
     * @param context
     * @return
     */
    public static final boolean is3G(Context context) {
        String type = getApnType(context);

        return type.equals(CMNET) || type.equals(CTNET) || type.equals(GNET_3);
    }

    /**
     * 获取手机网络接入点类型
     *
     * @param context
     * @return
     */
    public static final String getApnType(Context context) {
        String type = "unknown";
        Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null,
                null, null, null);
        c.moveToFirst();
        try {
            String apn = c.getString(c.getColumnIndex("apn"));
            if (TextUtils.isEmpty(apn)) {
                type = "unknown";
            } else if (apn.startsWith(CTNET)) {
                type = CTNET;
            } else if (apn.startsWith(CTWAP)) {
                type = CTWAP;
            } else if (apn.startsWith(CMWAP)) {
                type = CMWAP;
            } else if (apn.startsWith(CMNET)) {
                type = CMNET;
            } else if (apn.startsWith(GWAP_3)) {
                type = GWAP_3;
            } else if (apn.startsWith(GNET_3)) {
                type = GNET_3;
            } else if (apn.startsWith(UNIWAP)) {
                type = UNIWAP;
            } else if (apn.startsWith(UNINET)) {
                type = UNINET;
            }
        } catch (Exception e) {
            e.printStackTrace();
            type = "unknown";
        }

        return type;
    }

    /**
     * 获取手机Ip地址
     *
     * @param context
     * @return
     */
    public static final String getHostIp(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return getNetIp(context);
            } else {
                return getLocalIpAddress();
            }
        } else {
            return "";
        }
    }

    private static final String getNetIp(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private static final String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    private static final String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";

    }
}
