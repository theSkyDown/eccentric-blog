package ink.eccentric.runner;

import ink.eccentric.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 浏览量启动器
 * @author eccentric
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        //从 mysql 中获取每篇文章的浏览量
        Map<String,Integer> viewCountMap = articleService.getViewCounts();
        //启动时将浏览量存入到 redis 中
        redisTemplate.opsForHash().putAll("article:viewCount",viewCountMap);

    }
}
