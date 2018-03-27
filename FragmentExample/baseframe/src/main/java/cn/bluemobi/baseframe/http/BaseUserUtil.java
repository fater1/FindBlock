package cn.bluemobi.baseframe.http;


public interface BaseUserUtil {

    /**
     * 获取用户ID
     * @return
     */
    String getUserId();

    /**
     * 获取SSID
     * @return
     */
    String getSsid();

    void logout();
}
