package com.godric.cd.application.aop;

import com.alibaba.fastjson.JSON;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.result.BaseResult;
import com.godric.cd.result.DataResult;
import com.godric.cd.result.ListResult;
import com.godric.cd.result.PaginationResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class ApiAspect {

    @Around("execution(* com.godric.cd.controller..*.*(..))")
    public Object handleAnimalController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        List<Object> logArgs = Arrays.stream(args)
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        //过滤后序列化无异常
        log.info("method [{}] start process, args:{}", proceedingJoinPoint.getSignature(), JSON.toJSONString(logArgs));
        try {
            return proceedingJoinPoint.proceed();
        } catch (MethodArgumentNotValidException e) {
            log.error("methodArgumentNotValidException", e);
            throw new BizException(e.getMessage());
        } catch(BizException e) {
            log.warn("method [{}] biz error, args:{}, e:{}", proceedingJoinPoint.getSignature(), logArgs, e.getMessage());
            Class<?> returnClass = ((MethodSignature)proceedingJoinPoint.getSignature()).getReturnType();
            if (returnClass == DataResult.class) {
                return DataResult.error(e.getMessage());
            }
            if (returnClass == ListResult.class) {
                return ListResult.error(e.getMessage());
            }
            if (returnClass == PaginationResult.class) {
                return PaginationResult.buildError(e.getMessage());
            }
            return BaseResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("error, args:{}", logArgs, e);
            Class<?> returnClass = ((MethodSignature)proceedingJoinPoint.getSignature()).getReturnType();
            if (returnClass == DataResult.class) {
                return DataResult.error(BizErrorEnum.SYSTEM_ERROR);
            }
            if (returnClass == ListResult.class) {
                return ListResult.error(BizErrorEnum.SYSTEM_ERROR);
            }
            if (returnClass == PaginationResult.class) {
                return PaginationResult.buildError(BizErrorEnum.SYSTEM_ERROR);
            }
            return BaseResult.fail(BizErrorEnum.SYSTEM_ERROR);
        }
    }

}
