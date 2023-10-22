package ink.eccentric.utils;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * bean拷贝工具类
 * @author eccentric
 */
public class BeanCopyUtil {

    /**
     * bean拷贝
     * @param source
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T copyBean(Object source,Class<T> clazz){
        T result = null;
        try {
            result = clazz.newInstance();
            //属性拷贝
            BeanUtils.copyProperties(source,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * bean拷贝
     * @param list
     * @param clazz
     * @return
     * @param <O>
     * @param <T>
     */
    public static <O,T> List<T> copyBeanList(List<O> list, Class<T> clazz){
        List<T> result= new ArrayList<>();
        for (O source : list){
            result.add(copyBean(source, clazz));
        }
        return result;
    }

}
