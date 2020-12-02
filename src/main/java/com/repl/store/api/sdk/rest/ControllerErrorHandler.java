package com.repl.store.api.sdk.rest;

import com.repl.store.api.sdk.util.ExceptionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerErrorHandler {

    private static final Logger logger = LogManager.getLogger(ControllerErrorHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseResource exceptionHandler(Exception ex) {
        logger.error(ExceptionUtil.getStacktrace(ex));

        return buildErrorResource(getErrorMessage(ex));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseResource handleMissingDataException(Exception ex) {
        logger.error(ExceptionUtil.getStacktrace(ex));

        return buildErrorResource(getErrorMessage(ex));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class, ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseResource handleRequestBodyValidationException(Exception ex) {
        if (logger.isDebugEnabled()) {
            logger.error(ExceptionUtil.getStacktrace(ex));
        }

        BindingResult bindingResult = null;
        ErrorResponseResource errorResource = null;

        if (ex instanceof ConstraintViolationException) {
            String errorMsg = ((ConstraintViolationException) ex).getConstraintViolations()
                    .stream()
                    .map(violation -> violation.getPropertyPath().toString() + " " + violation.getMessage())
                    .findFirst()
                    .get();

            errorResource = buildErrorResource(errorMsg) ;

        } else if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();

        } else if (ex instanceof BindException) {
            bindingResult = ((BindException) ex).getBindingResult();

        } else if (ex instanceof MissingServletRequestPartException) {
            errorResource = buildErrorResource(((MissingServletRequestPartException)ex).getRequestPartName() + " may not be empty");

        } else if (ex instanceof MissingServletRequestParameterException) {
            errorResource = buildErrorResource(((MissingServletRequestParameterException)ex).getParameterName() + " may not be empty");
        }

        if (bindingResult != null) {
            errorResource = getBindingErrorResource(bindingResult);
        }

        return errorResource;
    }

    protected ErrorResponseResource getBindingErrorResource(BindingResult bindingResult) {
        return buildErrorResource(bindingResult.getFieldError().getField()
                .concat(" ")
                .concat(bindingResult.getFieldError().getDefaultMessage())
        );
    }

    private ErrorResponseResource buildErrorResource(String message) {
        return new ErrorResponseResource(message);
    }

    protected String getErrorMessage(Exception ex) {
        return ex.getCause() != null ? checkForNullPointer(ex.getCause()) : checkForNullPointer(ex);
    }

    private String checkForNullPointer(Throwable ex) {
        if (ex instanceof NullPointerException) {
            return ExceptionUtil.getStacktrace(ex);
        }

        return ex.getMessage();
    }

}
