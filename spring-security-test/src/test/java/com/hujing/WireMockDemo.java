package com.hujing;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author hj
 * @create 2019-05-01 15:14
 */
public class WireMockDemo {

    public static void main(String[] args) throws IOException {
        //连接wiremock服务器
        configureFor(8081);
        removeAllMappings();
        mock("/test/1", "response");
    }

    private static void mock(String url, String fileName) throws IOException {
        ClassPathResource cpr = new ClassPathResource("mock/response/"+fileName+".txt");
        String content = StringUtils.join(FileUtils.readLines(cpr.getFile(), "UTF-8"), "\r\n");
        stubFor(get(urlEqualTo(url)).willReturn(aResponse().withBody(content).withStatus(200)));
    }
}
