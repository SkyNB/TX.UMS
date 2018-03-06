package com.lnet.ums.service;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.notification.notificationDto.NotificationDto;
import com.lnet.ums.contract.api.NotificationService;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationServiceImplTest extends BaseTest {
    @Resource
    private NotificationService notificationService;

    /*@Test
    public void recall() throws Exception {

    }


    @Test
    public void getNotificationsForReader() throws Exception {

    }

    @Test
    public void read() throws Exception {

        String notifyId = notificationService.send("tes read", "test content", "simmy");
        notificationService.read(notifyId, "SIMMY");


    }


    @Test
    public void send() throws Exception {
        String id = notificationService.send("test", "test body", "simmy");
        System.out.println(id);

    }

    @Test
    public void send() {
        Response response = notificationService.send("测试", "测试内容", "sys", "sys");
        Assert.isTrue(response.isSuccess());
    }

    @Test
    public void send2() {
        Response response = notificationService.send("测试2", "测试内容2", "sys", Arrays.asList(new String[]{"receiver2", "receiver3"}));
        Assert.isTrue(response.isSuccess());
    }

    @Test
    public void recall() {
        Response response = notificationService.recall("766151135439826944");
        Assert.isTrue(response.isSuccess());
    }

    @Test
    public void recall2() {
        Response response = notificationService.recall("766151135439826944", "sys");
        Assert.isTrue(response.isSuccess());
    }

    @Test
    public void read() {
        Response response = notificationService.read("766151135439826944", "sys");
        Assert.isTrue(response.isSuccess());
    }

    @Test
    public void pageList() {
        PagedResponse pagedResponse = notificationService.pageList(1, 20, null);
        System.out.println(pagedResponse.getBody());
    }*/
    @Test
    public void pattern() {
        Pattern pattern = Pattern.compile("1[35678]\\d-\\d{4}-\\d{4}");
        Matcher matcher = pattern.matcher("138-1998-0570");
        Pattern pattern1 = Pattern.compile("\\w*");
        Matcher matcher1 = pattern1.matcher("aaa");
        Assert.isTrue(matcher.matches());
        Assert.isTrue(matcher1.matches());
    }

    @Test
    public void getNotificationsForReader() {
        Response<List<NotificationDto>> response = notificationService.getNotificationsForReader("ALL", 20);
        List<NotificationDto> dtos = response.getBody();
        System.out.println(dtos);
    }

    @Test
    public void get() {
        Response<NotificationDto> response = notificationService.get("781739911987060736");
        Assert.isTrue(response.isSuccess());
        System.out.println(response.getBody());
    }

    @Test
    public void pageList() {
        Map<String, Object> map = new HashMap<>();
        PageResponse<NotificationDto> response = notificationService.pageList(1, 20, map);
        System.out.println(response.getBody());
    }

    @Test
    public void send2() {
        Response response = notificationService.send("测试2", "测试内容2", "sys", Arrays.asList(new String[]{"receiver2", "receiver3"}));
        Assert.isTrue(response.isSuccess());
    }
}