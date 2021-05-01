package com.maequise.test.context;

import com.maequise.context.AppContext;
import com.maequise.context.DefaultApplicationContext;
import com.maequise.context.exceptions.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestApplicationContext {
    private static AppContext context;

    @BeforeAll
    static void init(){
        context = new DefaultApplicationContext();
    }

    @Test
    void testInstanceExist(){
        assertNotNull(context);
    }

    @Test
    public void testParseContext(){
        context.parseContext();
    }

    @Test
    public void testParseContextWithFilePath(){
        context.parseContext(null);
    }

    @Test
    public void testParseContextWithFilePathAndURIType(){
        assertThrows(ParseException.class, () -> context.parseContext("null file", "file:"));
        assertThrows(ParseException.class, () -> context.parseContext("null file", "unknown:"));
    }

    @Test
    public void testParseContent(){
        assertDoesNotThrow(() -> context.parseContext(
                TestApplicationContext.class.getClassLoader().getResource("test-context.xml").getFile(), "file"));
    }
}


