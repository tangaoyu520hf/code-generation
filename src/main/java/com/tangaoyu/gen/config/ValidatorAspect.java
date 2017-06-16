package com.tangaoyu.gen.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class ValidatorAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Validator validator;

    public ValidatorAspect(Validator validator){
        if (validator instanceof LocalValidatorFactoryBean) {
            this.validator = ((LocalValidatorFactoryBean) validator).getValidator();
        }
        else {
            this.validator = validator;
        }
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)"
            + " || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcutAbc() {
        // Method is empty as this is just a Poincut, the implementations are in the advices.
    }

    /**
     * Advice that logs when a method is entered and exited.
     */
    @Around("pointcutAbc()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if(this.validator instanceof ExecutableValidator){
            Object target = joinPoint.getThis();
            Object[] args = joinPoint.getArgs();
            Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
            ExecutableValidator executableValidator = (ExecutableValidator) this.validator;
            Set<ConstraintViolation<Object>> constraintViolations = executableValidator.validateParameters(target, method, args);
            System.out.println(constraintViolations);
        }
        Object result = joinPoint.proceed();
        return result;
    }
}
