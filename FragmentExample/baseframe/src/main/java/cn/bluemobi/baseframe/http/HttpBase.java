package cn.bluemobi.baseframe.http;

/**
 * 网络参数
 */
public interface HttpBase {
    /**
     * 服务器根路径
     * @return
     */
    String getRootUrl();

    /**
     * 图片服务器根路径
     * @return
     */
    String getImageRootUrl();

    /**
     * 福利管家服务器路径
     */
    String getWelfareUrl();
}
