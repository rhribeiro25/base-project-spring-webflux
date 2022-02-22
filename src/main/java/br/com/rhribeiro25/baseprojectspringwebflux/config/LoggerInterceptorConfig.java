package br.com.rhribeiro25.baseprojectspringwebflux.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class Logger Aspect
 *
 * @author Renan Henrique Ribeiro
 * @since 06/03/2021
 */
@Component
@Aspect
@Log4j2
public class LoggerInterceptorConfig {

    private ObjectMapper mapper;
    private Map<String, Map> logMap;

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        mapper = new ObjectMapper();
        logMap = new HashMap<>();
        long start = System.currentTimeMillis();
        var result = joinPoint.proceed();
        if (result instanceof Mono) {
            return logMonoResult(joinPoint, start, (Mono) result);
        } else if (result instanceof Flux) {
            return logFluxResult(joinPoint, start, (Flux) result);
        } else {
            // body type is not Mono/Flux
            logResult(joinPoint, start, result);
            return result;
        }
    }

    private Mono logMonoResult(ProceedingJoinPoint joinPoint, long start, Mono result) {
        AtomicReference<String> traceId = new AtomicReference<>("");
        return result.doOnSuccess(o -> {
            setTraceIdInMDC(traceId);
            var response = Objects.nonNull(o) ? o.toString() : "";
            try {
                logRequest(joinPoint);
                logResponse(joinPoint, start, response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).subscriberContext(context -> {
            // the error happens in a different thread, so get the trace from context, set in MDC and downstream to doOnError
            setTraceIdFromContext(traceId, (Context) context);
            return context;
        }).doOnError(o -> {
            setTraceIdInMDC(traceId);
            try {
                logRequest(joinPoint);
                logError(joinPoint, start, o.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

    }

    private Flux logFluxResult(ProceedingJoinPoint joinPoint, long start, Flux result) {
        return result.doFinally(o -> {
            try {
                logRequest(joinPoint);
                logResponse(joinPoint, start, o.toString()); // NOTE: this is costly
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).doOnError(o -> {
            try {
                logRequest(joinPoint);
                logError(joinPoint, start, o.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private void logResult(ProceedingJoinPoint joinPoint, long start, Object result) throws JsonProcessingException {
        try {
            logRequest(joinPoint);
            logResponse(joinPoint, start, result.toString());
        } catch (Exception e) {
            logRequest(joinPoint);
            logError(joinPoint, start, e.getMessage());
        }
    }


    private void logError(ProceedingJoinPoint joinPoint, long start, String error) throws JsonProcessingException {
        Map<String, String> body = setLogMap(joinPoint, start, error);
        logMap.put("Error", body);
        log.error(mapper.writeValueAsString(logMap));
    }

    private void logResponse(ProceedingJoinPoint joinPoint, long start, String response) throws JsonProcessingException {
        Map<String, String> body = setLogMap(joinPoint, start, response);
        logMap.put("Response", body);
        log.info(mapper.writeValueAsString(logMap));
    }

    private void logRequest(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        Map<String, String> body = setLogMap(joinPoint, null, null);
        logMap.put("Request", body);
        log.info(mapper.writeValueAsString(logMap));
    }

    private void setTraceIdFromContext(AtomicReference<String> traceId, Context context) {
        if (context.hasKey("X-B3-TraceId")) {
            traceId.set(context.get("X-B3-TraceId"));
            setTraceIdInMDC(traceId);
        }
    }

    private void setTraceIdInMDC(AtomicReference<String> traceId) {
        if (!traceId.get().isEmpty()) {
            MDC.put("X-B3-TraceId", traceId.get());
        }
    }

    private Map<String, String> setLogMap(ProceedingJoinPoint joinPoint, Long start, String result) throws JsonProcessingException {
        Map<String, String> body = new HashMap<>();
        body.put("Class", joinPoint.getSignature().getDeclaringTypeName());
        body.put("Method", joinPoint.getSignature().getName() + "()");
        if (result != null)
            body.put("Payload", mapper.writeValueAsString(result));
        else if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0)
            body.put("Payload", mapper.writeValueAsString(mapper.writeValueAsString(joinPoint.getArgs()[0])));
        else
            body.put("Payload", "");
        if (start != null) body.put("Time", String.valueOf(System.currentTimeMillis() - start) + " ms");
        return body;
    }

}