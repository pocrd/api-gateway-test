package net.pocrd.apigwtest;

import net.pocrd.m.app.client.ApiAccessor;
import net.pocrd.m.app.client.ApiContext;
import net.pocrd.m.app.client.BaseRequest;
import net.pocrd.m.app.client.api.request.Apitest_TestBadResponse;
import net.pocrd.m.app.client.api.request.Apitest_TestDemoSayHello;
import net.pocrd.m.app.client.api.request.Apitest_TestThrowServiceException;
import net.pocrd.m.app.client.api.resp.Api_APITEST_SimpleTestEntity;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by guankaiqiang521 on 2014/9/29.
 */
public class ApiFunctionTest {
    private static ApiContext  context  = ApiContext.getDefaultContext("1", 123, "1.2.3");
    private static ApiAccessor accessor = new ApiAccessor(context, 1000, 3000, "apigw tester", "http://localhost:8080/m.api");

    @BeforeClass
    public static void init() {
        System.setProperty("debug.dubbo.url", "dubbo://localhost:20880/");
        System.setProperty("debug.dubbo.version", "LATEST");
    }

    @Test
    public void testThrowServiceException() {
        Apitest_TestThrowServiceException test = new Apitest_TestThrowServiceException();
        accessor.fillApiResponse(test);
        Assert.assertEquals(123, test.getReturnCode());
    }

    @Test
    public void testBadResponse() {
        Apitest_TestBadResponse test = new Apitest_TestBadResponse();
        final BaseRequest[] requests = new BaseRequest[] { test };
        accessor.fillApiResponse(requests);
        Assert.assertEquals("xx", test.getResponse().str);
    }

    @Test
    public void testDynamicEntity() {
        Apitest_TestDemoSayHello test = new Apitest_TestDemoSayHello("abc");
        final BaseRequest[] requests = new BaseRequest[] { test };
        accessor.fillApiResponse(requests);
        Assert.assertEquals("aabbcc", ((Api_APITEST_SimpleTestEntity)test.getResponse().dynamicEntity.entity).strValue);
    }

    @Test
    public void testCodeTransfer() {
        Apitest_TestDemoSayHello test = new Apitest_TestDemoSayHello("你好");
        final BaseRequest[] requests = new BaseRequest[] { test };
        accessor.fillApiResponse(requests);
        Assert.assertEquals(123, test.getReturnCode());
    }
}
