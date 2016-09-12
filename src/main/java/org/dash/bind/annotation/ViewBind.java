package org.dash.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View注解 自动绑定
 * 增加一个默认的单击事件绑定
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewBind {

    int value();

    boolean onClickListener() default false;

}
