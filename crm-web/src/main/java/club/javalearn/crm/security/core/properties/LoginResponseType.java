package club.javalearn.crm.security.core.properties;

/**
 * 认证成功后的响应方式
 *
 * @author king-pan
 * @date 2018-03-05
 **/
public enum LoginResponseType {

    /**
     * 跳转
     */
    REDIRECT,
    /**
     * 返回json
     */
    JSON
}
