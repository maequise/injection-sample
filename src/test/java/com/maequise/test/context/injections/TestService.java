package com.maequise.test.context.injections;

import com.maequise.context.annotations.Service;

@Service
public class TestService implements IService {
    @Override
    public String displayHello() {
        return "This is a test";
    }
}
