package com.hujing.springsecuritytest.annotation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author hj
 * @create 2019-05-01 10:49
   自定义参数校验解析器
 */
public class MyConstrainValid implements ConstraintValidator<MyConstrain, String> {
   //可以注入spring IOC中的bean
   /**
    * 初始化
    * @param constraint
    */
   public void initialize(MyConstrain constraint) {
      System.out.println("init...");
   }
   /**
    * 执行校验逻辑
    * @param str
    * @param context
    * @return
    */
   public boolean isValid(String str, ConstraintValidatorContext context) {
      if (StringUtils.equals(str, "hi")) {
         return true;
      }
      return false;
   }
}
