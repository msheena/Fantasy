package cn.tail.fantasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface ClickBehavior {
    String value();
}
