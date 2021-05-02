package com.maequise.context;

import com.maequise.context.annotations.Component;
import com.maequise.context.annotations.Service;
import com.maequise.context.exceptions.ParseException;
import com.maequise.context.parsers.ContextParser;
import com.maequise.context.reflection.ReflectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    private static final String DEFAULT_DIR_SEPARATOR = System.getProperty("file.separator");

    private Document document;
    private static Map<String, Object> cachedClasses = new HashMap<>();
    private static Map<String, MetadataClass> cachedClassAndMetadata = new HashMap<>();

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
        registeredBeans.put(nameBean, value);
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

            for (Map.Entry<String, Object> entry : cachedClasses.entrySet()){
                Class clazz = (Class) entry.getValue();

                if(ReflectionUtils.isClassAnnotated(clazz, Service.class) || ReflectionUtils.isClassAnnotated(clazz, Component.class)){
                    try {
                        Object instance = clazz.getConstructor().newInstance();

                        cachedClassAndMetadata.put(clazz.getName(), MetadataClass.getInstance().getMetadataOfClass(clazz));
                        registerBean(clazz.getName(), instance);
                    }catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                        LOGGER.error("Error during instantiate class", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        NodeList beans = document.getElementsByTagName("bean");


        System.out.println("test");
    }

    private void crawlPackages(final String basePackage) {
        for (String path : CLASSPATH.split(DEFAULT_SEPARATOR_PATH)) {
            File file = new File(path);

            //manage case of jar file
            if (file.exists() && file.isFile() && file.getName().endsWith(".jar")) {
                try {
                    JarFile jarFile = new JarFile(file);

                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();

                        if (entry.getName().contains(basePackage) && entry.getName().endsWith(".class")) {
                            Class clazz = Class.forName(entry.getName().replace(DEFAULT_DIR_SEPARATOR, "."));

                            cachedClasses.put(entry.getName(), clazz);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.error(e);
                }
            }

            //manage case of classes directory ==> in case of IDE execution
            if (file.exists() && file.isDirectory() && file.getName().contains("classes")) {
                crawlDirectory(file, basePackage);
            }
        }
    }

    private void crawlDirectory(File directory, final String basePackage) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                crawlDirectory(file, basePackage);
            }
            if (file.getName().endsWith(".class")) {
                String startPackage = basePackage.split("\\.")[0];

                String clazzName = startPackage
                        .concat(file.getAbsolutePath()
                                .split(startPackage,2)[1]
                                .replace(DEFAULT_DIR_SEPARATOR, ".")
                                .split(".class")[0]
                        );

                if(clazzName.contains(basePackage)) {
                    try {
                        Class clazz = Class.forName(clazzName);

                        cachedClasses.put(clazzName, clazz);
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("Class not found !", e);
                    }
                }
            }
        }
    }

    static class FinderUtility {
        static void findImplementation(Class<?> clazz) {
            MetadataClass metadata = cachedClassAndMetadata.get(clazz.getName());

            for(Field field : metadata.getAnnotatedFields()){
                if (field.getType().isInterface()){
                    Class<?> typeClassOfField = field.getType().getClass();

                }
            }
        }

        static void findInterface(Class<?> clazz) {

        }

        static <T> T instantiate(Class<?> clazz) {
            return null;
        }
    }
}
