package cat.event;

import cat.dao.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NewCommentEventListener {





    // 새 댓글 알림
    @EventListener
    @Async  // 비동기로 자동 수행 애너테이션
    public void onApplicationEvent(NewCommentEvent event) throws Exception {
        System.out.println(event.getBno()+"번 게시물에 댓글이 등록되었습니다...");


    }

}
