package com.maequise.context;

public interface AppContext {
    public void parseContext();

    public void parseContext(final String pathContext);

    public void parseContext(final String pathContext, final String typeUri);

    public <T> T getBean(String nameBean);

    public <T> T getBean(String nameBean, Class<T> clazzType);

    public <T> T getBean(String nameBean, Class<T> clazzType, Object defaultValue);

    public void registerBean(String nameBean, Object value);

    public void removeBean(String nameBean);

    public void clearAll();
}
