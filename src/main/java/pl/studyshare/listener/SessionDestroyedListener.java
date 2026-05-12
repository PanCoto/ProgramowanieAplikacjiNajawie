package pl.studyshare.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.studyshare.component.VoteCollector;
import pl.studyshare.repository.AnswerRepository;

@Component
@Slf4j
public class SessionDestroyedListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        var context = WebApplicationContextUtils.getWebApplicationContext(se.getSession().getServletContext());

        if (context != null) {
            try {
                VoteCollector collector = context.getBean(VoteCollector.class);
                AnswerRepository repository = context.getBean(AnswerRepository.class);

                collector.flushToDatabase(repository);
            } catch (Exception e) {
                log.error("Błąd podczas zapisywania głosów sesji do bazy: {}", e.getMessage());
            }
        }
    }
}