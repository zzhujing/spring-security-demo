package com.hujing.springsecuritycore.code.effective;

import com.hujing.springsecuritycore.code.exception.ValidateCodeException;
import com.hujing.springsecuritycore.enums.CodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hj
 * @create 2019-05-02 17:21
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor findProcessorByType(CodeType codeType) {
        return findProcessorByType(codeType.toString().toLowerCase());
    }

    /**
     * 根据类型查询对应类型的处理器
     * @param type
     * @return
     */
    public ValidateCodeProcessor findProcessorByType(String type) {
        if (StringUtils.isNotBlank(type)) {
            ValidateCodeProcessor processor = validateCodeProcessorMap.get(type + "CodeProcessor");
            if (processor != null) {
                return processor;
            }
        }
        throw new ValidateCodeException(type + "codeProcessor不存在");
    }
}
