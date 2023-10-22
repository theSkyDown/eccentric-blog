package ink.eccentric.controller;

import com.alibaba.excel.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author eccentric
 */
public abstract class BaseController {

    /**
     * 判断Integer参数有效性
     *
     * @param param
     */
    public static boolean isOk(Integer param) {
        return param != null && param > 0;
    }

    /**
     * 判断Integer参数是否无效
     */
    public static boolean notOk(Integer param) {
        return param == null || param <= 0;
    }

    /**
     * 判断List参数有效性
     *
     * @param param
     */
    public static boolean isOk(List<?> param) {
        return param != null && param.size() > 0;
    }

    /**
     * 判断List参数是否无效
     *
     * @param param
     */
    public static boolean notOk(List<?> param) {
        return param == null || param.size() == 0;
    }

    /**
     * 判断String参数是否有效
     *
     * @param param
     */
    public static boolean isOk(String param) {
        return StringUtils.isNotBlank(param);
    }

    /**
     * 判断String参数无效
     *
     * @param param
     */
    public static boolean notOk(String param) {
        if (param != null) {
            int i = 0;

            for (int len = param.length(); i < len; ++i) {
                if (param.charAt(i) > ' ') {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * 判断Double参数是否有效
     *
     * @param param
     */
    public static boolean isOk(Double param) {
        return param != null && param > 0;
    }

    /**
     * 判断Double参数无效
     *
     * @param param
     */
    public static boolean notOk(Double param) {
        return param == null || param <= 0;
    }


    /**
     * 判断Float参数是否有效
     *
     * @param param
     */
    public static boolean isOk(Float param) {
        return param != null && param > 0;
    }

    /**
     * 判断Float参数无效
     *
     * @param param
     */
    public static boolean notOk(Float param) {
        return param == null || param <= 0;
    }

    /**
     * 判断Long参数是否有效
     *
     * @param param
     */
    public static boolean isOk(Long param) {
        return param != null && param > 0;
    }

    /**
     * 判断Long参数无效
     *
     * @param param
     */
    public static boolean notOk(Long param) {
        return param == null || param <= 0;
    }




    /**
     * 判断Date类型数据是否正确
     *
     * @param param
     * @return
     */
    public static boolean isOk(Date param) {
        return param != null;
    }




    /**
     * 判断Date类型数据不正确
     *
     * @param param
     * @return
     */
    public static boolean notOk(Date param) {
        return param == null;
    }



    /**
     * 判断Boolean类型数据不正确
     *
     * @param param
     * @return
     */
    public static boolean notOk(Boolean param) {
        return param == null;
    }

    /**
     * 判断Boolean类型数据是否正确
     *
     * @param param
     * @return
     */
    public static boolean isOk(Boolean param) {
        return param != null;
    }

    /**
     * 判断Map类型数据不正确
     *
     * @param param
     * @return
     */
    public static boolean notOk(Map<?, ?> param) {
        return param == null || param.isEmpty();
    }

    /**
     * 判断Map类型数据是否正确
     *
     * @param param
     * @return
     */
    public static boolean isOk(Map<?, ?> param) {
        return param != null && !param.isEmpty();
    }

    /**
     * 判断Set类型数据不正确
     *
     * @param param
     * @return
     */
    public static boolean notOk(Set<?> param) {
        return param == null || param.isEmpty();
    }

    /**
     * 判断Set类型数据是否正确
     *
     * @param param
     * @return
     */
    public static boolean isOk(Set<?> param) {
        return param != null && !param.isEmpty();
    }

    /**
     * 判断byte[]类型数据不正确
     *
     * @param param
     * @return
     */
    public static boolean notOk(byte[] param) {
        return param == null || param.length == 0;
    }

    /**
     * 判断byte[]类型数据是否正确
     *
     * @param param
     * @return
     */
    public static boolean isOk(byte[] param) {
        return param != null && param.length > 0;
    }

    public static boolean isOk(Object obj){
        if (Objects.isNull(obj)){
            return false;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                if (declaredField.get(obj)!=null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static boolean notOk(Object obj){
        return !isOk(obj);
    }
}
