package com.atos.demo.api;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * AOP service for api tracing.
 */
@Component
@Aspect
public class TraceCalls {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceCalls.class);

    static {
        LOGGER.info("init aspectJ");
    }

    /**
     * Log methods calls (parameters and duration)
     */
    @Around("traceMethodIhm()")
    public Object afficherTrace(final ProceedingJoinPoint joinpoint) throws Throwable {
        var nomMethode = joinpoint.getSignature().getDeclaringTypeName();
        nomMethode += "." + joinpoint.getSignature().getName();
        MDC.put("nomMethode", nomMethode);
        
        var args = joinpoint.getArgs();
        var sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        sb.append(") ");
        var arguments = sb.toString();
        MDC.put("arguments", arguments);
        
        var startTime = new Date().getTime();
        LOGGER.info(">>> {}{} START", nomMethode, arguments);
        Object obj;
        try {
            obj = joinpoint.proceed();
        }
        finally {
            var stopTime = new Date().getTime();
            LOGGER.info(">>> {}{} END {} ms", nomMethode, arguments, stopTime-startTime);
        }
        return obj;
    }

    /**
     * Log methods calls (return value)
     * an HandlerInterceptorAdapter is better
     */
    @SuppressWarnings("rawtypes")
	@AfterReturning(pointcut = "traceMethodIhm()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
       	if (result instanceof ResponseEntity) {
       		var re = (ResponseEntity) result;
            LOGGER.info(">>> {}{} RETURN {} {}", MDC.get("nomMethode"), MDC.get("arguments"), re.getStatusCode(), re.getBody());
       	}
    }

    /**
     * where to apply advice (here in api controllers)
     */
    @Pointcut("execution(* com.atos.demo.api..*(..))")
    public void traceMethodIhm() {
    }

}
