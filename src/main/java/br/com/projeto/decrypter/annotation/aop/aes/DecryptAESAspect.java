package br.com.projeto.decrypter.annotation.aop.aes;

import br.com.projeto.decrypter.annotation.components.aes.DecryptAESComponent;
import br.com.projeto.decrypter.annotation.processor.ValidatorProcessor;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;
import org.springframework.validation.BindingResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

@Aspect
@Component
public class DecryptAESAspect {

    private final DecryptAESComponent decryptAESComponent;
    private final ValidatorProcessor validatorProcessor;

    public DecryptAESAspect(
            DecryptAESComponent decryptAESComponent,
            ValidatorProcessor validatorProcessor) {
        this.decryptAESComponent = decryptAESComponent;
        this.validatorProcessor = validatorProcessor;
    }

    @Around("execution(* *(.., @DecryptAES (*), ..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final Annotation[][] paramAnnotations = methodSignature.getMethod().getParameterAnnotations();

        for (int index = 0; index < paramAnnotations.length; index++) {
            for (Annotation annotation : paramAnnotations[index]) {
                validAnnotationIsDecrypt(args, index, annotation);
            }
        }

        executeValidator(args);
        return joinPoint.proceed(args);
    }

    private void validAnnotationIsDecrypt(final Object[] args, final int index, final Annotation annotation) throws IllegalAccessException {
        if (annotation instanceof DecryptAES) {
            final String[] fieldsDecrypt = ((DecryptAES) annotation).fields();
            Object obj = args[index];

            if (!(obj instanceof String)) {
                performDecryptField(fieldsDecrypt, obj);
            } else {
                args[index] = this.decryptAESComponent.execute((String) obj);
            }
        }
    }

    private void performDecryptField(final String[] fieldsDecrypt, final Object obj) throws IllegalAccessException {
        final Field[] declaredFields = obj.getClass().getDeclaredFields();

        for (String fieldName : fieldsDecrypt) {
            for (Field field : declaredFields) {
                if (fieldName.equals(field.getName())) {
                    makeAccessible(field);
                    final Object fieldActualValue = field.get(obj);

                    if (nonNull(fieldActualValue)) {
                        setField(field, obj, this.decryptAESComponent.execute(fieldActualValue.toString()));
                    }
                }
            }
        }
    }

    private void executeValidator(final Object[] args) {
        final Object findBindResult = Arrays.stream(args)
                .filter(o -> o instanceof BindingResult).findFirst()
                .orElse(null);

        if (nonNull(findBindResult)) {
            BindingResult bindingResult = (BindingResult) findBindResult;

            asList(args).forEach(obj -> this.validatorProcessor.execute(obj, bindingResult));
        }
    }
}