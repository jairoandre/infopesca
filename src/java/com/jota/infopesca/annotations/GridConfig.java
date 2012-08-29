/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author root
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface GridConfig {

    boolean required() default false;
    
    boolean flag() default false;

    boolean editable() default true;

    boolean date() default false;

    boolean currency() default false;

    boolean enumerated() default false;

    boolean listed() default false;

    boolean number() default false;

    boolean weight() default false;

    boolean griddable() default false;

    Class softGridClass() default Object.class;

    String label() default "Não definido";

    int order() default -1;

    int size() default 20;

    String mask() default "";
    
    boolean visible() default true;
}
