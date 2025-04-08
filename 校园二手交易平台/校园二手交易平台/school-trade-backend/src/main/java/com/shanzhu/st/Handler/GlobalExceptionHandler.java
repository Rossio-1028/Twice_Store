package com.shanzhu.st.Handler;

import com.shanzhu.st.Exception.ParamException;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.vo.R;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> collect = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return R.fail(ErrorMsg.PARAM_ERROR, collect);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R HttpMessageNotReadableExceptionHandler() {
        return R.fail(ErrorMsg.MISSING_PARAMETER, "requestBody错误!");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return R.fail(ErrorMsg.MISSING_PARAMETER, "缺少参数" + e.getParameterName());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R ConstraintViolationExceptionHandler(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        Map<String, String> map = new HashMap<>();
        if (set.size() > 0) {
            for (ConstraintViolation<?> cv : set) {
                String[] param = cv.getPropertyPath().toString().split("\\.");
                String message = cv.getMessage();
                map.put(param[param.length - 1], message);
            }
        }

        return R.fail(ErrorMsg.PARAM_ERROR, map);
    }

    @ExceptionHandler(ParamException.class)
    public R ParamExceptionHandler(ParamException e) {
        return R.fail(ErrorMsg.PARAM_ERROR, e.getMap());
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public R MissingRequestCookieExceptionHandler() {
        return R.fail(ErrorMsg.COOKIE_ERROR);
    }
}