package org.example.hibernate.user.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Before("@annotation(loggable)")
    public void log(
            JoinPoint joinPoint,
            Loggable loggable
    ) {
        Object arg = joinPoint.getArgs().length == 0 ? "null" : joinPoint.getArgs()[0];

        System.out.printf("[LOG] %s: before method: %s\n argument:%s%n",
                loggable.value(),
                joinPoint.getSignature().getName(),
                arg
        );
    }
}
