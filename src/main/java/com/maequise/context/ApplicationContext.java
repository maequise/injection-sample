package com.maequise.context;

import com.maequise.context.exceptions.ParseException;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ApplicationContext implements AppContext {
    private static Map<String, Object> registeredBeans = new ConcurrentHashMap<>();
    private static final String DEFAULT_CONTEXT_PATH = "/context.xml";
    private static final String DEFAULT_URI_MODE = "classpath:";
    private static final String CLASSPATH = System.getProperty("java.class.path");
    private static final String DEFAULT_SEPARATOR_PATH = System.getProperty("path.separator");
    private static final String DEFAULT_DIR_SEPARATOR = System.getProperty("separator.path");

    @Override
    public void parseContext() {
        parseContext(null);
    }

    @Override
    public void parseContext(String pathContext) {
        String filePath = pathContext == null ? DEFAULT_CONTEXT_PATH : pathContext;

        parseContext(filePath, DEFAULT_URI_MODE);
    }

    @Override
    public void parseContext(String pathContext, String typeUri) {
        String pathFoundContext = "";

        if(typeUri.contains("classpath")){
            String[] classpathEntries = CLASSPATH.split(DEFAULT_SEPARATOR_PATH);

            for (String pathEntry : classpathEntries){

            }

            //crawl for classpath resources
        }else if(typeUri.contains("file")){
            File file = new File(pathContext);

            if(file != null && file.exists()){
                //@TODO make the code
            }else {
                throw new ParseException("Please check path of path");
            }
        }else {
            throw new ParseException("Please check type of uri");
        }
    }

    @Override
    public <T> T getBean(String nameBean) {
        return null;
    }

    @Override
    public <T> T getBean(String nameBean, Class<T> clazzType) {
        return null;
    }

    @Override
    public <T> T getBean(String nameBean, Class<T> clazzType, Object defaultValue) {
        return null;
    }

    @Override
    public void registerBean(String nameBean, Object value) {

    }

    @Override
    public void removeBean(String nameBean) {

    }

    @Override
    public void clearAll() {

    }

    private void loadContextAndParse(final String pathContext){
    }

    static class Crawler {
        static void findImplementation(Class<?> clazz){

        }

        static void findInterface(Class<?> clazz){

        }

        static <T> T instantiate(Class<?> clazz){
            return null;
        }
    }
}
