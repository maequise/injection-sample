package com.maequise.context.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtils {
    public static boolean isClassAnnotated(Class<?> clazz, Class<? extends Annotation> annotation){
        return clazz.getAnnotation(annotation) != null;
    }

    public static boolean isMethodAnnotated(Method method, Class<? extends Annotation> annotation){
        return method.getAnnotation(annotation) != null;
    }

    public static boolean isClassMemberAnnotated(Field field, Class<? extends Annotation> annotation){
        return field.getAnnotation(annotation) != null;
    }
}
