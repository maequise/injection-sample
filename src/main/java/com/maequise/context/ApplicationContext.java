package com.maequise.context;

import com.maequise.context.exceptions.ParseException;
import com.maequise.context.parsers.ContextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class ApplicationContext implements AppContext {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationContext.class);

    private static Map<String, Object> registeredBeans = new ConcurrentHashMap<>();
    private static final String DEFAULT_CONTEXT_PATH = "/context.xml";
    private static final String DEFAULT_URI_MODE = "classpath:";
    private static final String CLASSPATH = System.getProperty("java.class.path");
    private static final String DEFAULT_SEPARATOR_PATH = System.getProperty("path.separator");
    private static final String DEFAULT_DIR_SEPARATOR = System.getProperty("separator.path");

    private Document document;
    private static Map<String, Map<String, Object>> cacheClasses = new HashMap<>();

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

        if (typeUri.contains("classpath")) {
            String[] classpathEntries = CLASSPATH.split(DEFAULT_SEPARATOR_PATH);

            for (String pathEntry : classpathEntries) {
                //@TODO make the code
            }

            //crawl for classpath resources
        } else if (typeUri.contains("file")) {
            File file = new File(pathContext);

            if (file != null && file.exists()) {
                document = new ContextParser(file).getDocumentParsed();

                parseDocument(document);
            } else {
                throw new ParseException("Please check path of path");
            }
        } else {
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

    private void parseDocument(final Document document) {
        NodeList basePackageNode = document
                .getElementsByTagName("scan-component");

        String basePackage = null;

        if (basePackageNode != null && basePackageNode.getLength() > 0) {
            basePackage = basePackageNode.item(0)
                    .getAttributes()
                    .getNamedItem("base-package")
                    .getNodeValue();

            crawlPackages(basePackage);
        }

        NodeList beans = document.getElementsByTagName("bean");


        System.out.println("test");
    }

    private void crawlPackages(final String basePackage) {
        for (String path : CLASSPATH.split(DEFAULT_SEPARATOR_PATH)) {
            File file = new File(path);

            if (file.exists() && file.isFile() && file.getName().endsWith(".jar")) {
                try {
                    JarFile jarFile = new JarFile(file);

                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()){
                        JarEntry entry = entries.nextElement();

                        if(entry.getName().contains(basePackage) && entry.getName().endsWith(".class")){

                        }
                    }
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        }
    }

    static class Crawler {
        static void findImplementation(Class<?> clazz) {

        }

        static void findInterface(Class<?> clazz) {

        }

        static <T> T instantiate(Class<?> clazz) {
            return null;
        }
    }
}
