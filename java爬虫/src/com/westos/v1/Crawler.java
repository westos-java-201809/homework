package com.westos.v1;

import com.westos.util.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络爬虫示例
 * <p>
 * 仅爬取贴吧某个页面正文中图片，使用最原始的java API
 *
 * @author yihang
 */
public class Crawler {

    /**
     * 正文图片正则
     */
    static Pattern pattern = Pattern.compile("<img class=\"BDE_Image\" src=\"(.*?)\"");

    /**
     * 存储路径，文件夹需要事先存在
     */
    static final String SAVE_PREFIX = "E:\\9.22实训作业\\imgs\\";

    /**
     * 贴吧网络地址
     */
    static String TIEBA_URL = "https://tieba.baidu.com/p/2256306796?red_tag=1781367364";


    public static void main(String[] args) {
        // 创建线程池，并发下载图片
        ExecutorService es = Executors.newFixedThreadPool(10);

        // 抓取整个网页
        Utils.fetch(TIEBA_URL).ifPresent(html -> {
            Matcher matcher = pattern.matcher(html);
            // 找到每个匹配的图片
            while (matcher.find()) {
                String imageURL = matcher.group(1);
                // 提交给线程池进行下载
                es.submit(() -> Utils.download(imageURL, SAVE_PREFIX));
            }
        });
        es.shutdown();
    }
}
