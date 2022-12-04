package site.metacoding.junitproject.util;

import org.springframework.stereotype.Component;

@Component
public class MailSenderStub implements MailSender { // 가짜 구현체

    @Override
    public boolean send() {
        return true;
    }
}
