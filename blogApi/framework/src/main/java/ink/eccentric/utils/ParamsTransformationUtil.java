package ink.eccentric.utils;

import ink.eccentric.domain.vo.common.Result;
import ink.eccentric.enums.AppHttpCodeEnum;
import ink.eccentric.exception.SystemException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 参数转换工具
 * @author eccentric
 */
public class ParamsTransformationUtil {

    /**
     * 将id字符串转换为列表
     * @param ids
     * @return
     */
    public static List<Long> idsToList(String ids){
        List<Long> idList = new ArrayList<>();
        if (Objects.isNull(ids) || !StringUtils.hasText(ids)){
            throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
        }
        for (String id : ids.split(",")){
            if (id.matches("\\d+")){
                idList.add(Long.valueOf(id));
            }else{
                throw new SystemException(AppHttpCodeEnum.PARAMS_FAIL);
            }
        }
        return idList;
    }

}
