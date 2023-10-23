package ink.eccentric.constants;

import java.util.List;

/**
 * @author eccentric
 */
public class SystemConstants {
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 评论类型(type)为文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型(type)为友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 用户类型(type)为管理员
     */
    public static final String IS_ADMIN = "1";
    /**
     * 用户类型(type)不是管理员
     */
    public static final String NOT_ADMIN = "0";

    public static final Integer SUCCESS_CODE = 200;

    /**
     * 正常状态
     */
    public static final String NORMAL_STATUS = "0";
    /**
     * 异常状态
     */
    public static final String ABNORMAL_STATUS = "1";

    /**
     * 超级管理员的id
     */
    public static final Long ADMIN_ID = 1L;

    /**
     * "普通用户"的角色id
     */
    public static final Long NORMAL_ROLE_ID = 2L;

    public static final List<Long> BASE_ROLE_ID_LIST = List.of(1L,2L);

}