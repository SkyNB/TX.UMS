package com.lnet.ums.contract.model.oauth;

/*
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
*/

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

import java.util.UUID;

/**
 * 随机数生成处理工具
 *
 * @author lxf
 */
// TODO: 2017/1/23 需要一直到公共包内
public abstract class GuidGenerator {

    private static RandomValueStringGenerator defaultClientSecretGenerator = new RandomValueStringGenerator(32);

    /**
     * private constructor
     */
    private GuidGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String generateClientSecret() {
        return defaultClientSecretGenerator.generate();
    }
}