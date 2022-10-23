package ui.bellintegrator;

import org.junit.jupiter.api.*;

public class FirstTests {

    @BeforeAll
    public static void beforeAll(){
        System.out.println("beforeAll");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("beforeEach");
    }

    @Test
    public void firstTest(){
        System.out.println("firstTest");
        Assertions.assertTrue(1==1,"1 != 1");
    }

    @Test
    public void secondTest(){
        System.out.println("secondTest");

        Assertions.assertTrue(1==2,"1 != 2");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("afterEach");
    }


    @AfterAll
    public static void afterAll(){
        System.out.println("afterAll");
    }

}
