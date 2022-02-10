package com.godric.cd.application.aop;

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

@Slf4j
@Aspect
@Component
public class ApiAspect {

    @Around("execution(* com.godric.cd.controller..*.*(..))")
    public Object handleAnimalController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("method [{}] start process, args:{}", proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs());
        try {
            return proceedingJoinPoint.proceed();
        } catch (MethodArgumentNotValidException e) {
            log.error("methodArgumentNotValidException", e);
            throw new BizException(e.getMessage());
        } catch(BizException e) {
            log.warn("method [{}] biz error, args:{}, e:{}", proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs(), e.getMessage());
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
            log.error("error, args:{}",proceedingJoinPoint.getArgs(), e);
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
