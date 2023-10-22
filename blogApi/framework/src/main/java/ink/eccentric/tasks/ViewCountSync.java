package ink.eccentric.tasks;


import ink.eccentric.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务——访问量同步到数据库
 * @author eccentric
 */
@Component
public class ViewCountSync {

    @Autowired
    private ArticleService articleService;

    /**
     * 每十分钟进行一次数据同步
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void sync(){
        articleService.syncViewCount();
    }

}
