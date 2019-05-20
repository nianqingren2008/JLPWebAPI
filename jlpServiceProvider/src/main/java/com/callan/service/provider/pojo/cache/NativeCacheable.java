package com.callan.service.provider.pojo.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.FIELD,ElementType.LOCAL_VARIABLE})  
@Documented
public @interface NativeCacheable {
	public enum KeyMode{
        DEFAULT,    //只有加了@CacheKey的参数,才加入key后缀中  
        BASIC,      //只有基本类型参数,才加入key后缀中,如:String,Integer,Long,Short,Boolean  
        ALL;        //所有参数都加入key后缀  
    }
	
	
	public String key() default "";     //缓存key
	public KeyMode keyMode() default KeyMode.DEFAULT; //key的后缀模式 
	
}
