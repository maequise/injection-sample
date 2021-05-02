package com.maequise.context;

import com.maequise.context.annotations.Inject;

import java.lang.reflect.Field;
import java.util.Arrays;

class MetadataClass {
    private Class<?> clazzTarget;
    private Class<?>[] interfaces;
    private Class<?> superClass;
    private Class<?>[] parentsClasses;
    private Field[] annotatedFields = new Field[0];

    static MetadataClass getInstance(){
        return new MetadataClass();
    }

    MetadataClass getMetadataOfClass(Class<?> clazz){
        clazzTarget = clazz;

        if(clazz.getInterfaces() != null && clazz.getInterfaces().length > 0){
            interfaces = clazz.getInterfaces();
        }

        if(clazz.getSuperclass() != null){
            superClass = clazz.getSuperclass();
        }

        for (Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Inject.class)){
                annotatedFields = Arrays.copyOf(annotatedFields, annotatedFields.length+1);
                int idx = annotatedFields.length;

                annotatedFields[idx-1] = field;
            }
        }

        return this;
    }

    public Class<?> getClazzTarget() {
        return clazzTarget;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public Class<?> getSuperClass() {
        return superClass;
    }

    public Class<?>[] getParentsClasses() {
        return parentsClasses;
    }

    public Field[] getAnnotatedFields() {
        return annotatedFields;
    }
}
