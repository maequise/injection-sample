package com.maequise.test.context.injections;

import com.maequise.context.annotations.Component;
import com.maequise.context.annotations.Inject;

@Component
public class ComponentBean {
    @Inject
    private IService service;

    public String display(){
        return service.displayHello();
    }
}
