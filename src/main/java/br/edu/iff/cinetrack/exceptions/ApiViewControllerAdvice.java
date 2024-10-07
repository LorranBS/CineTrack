package br.edu.iff.cinetrack.exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiViewControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    ProblemDetail handleDefaultException(Exception e){

        ProblemDetail prolemaDetail = ProblemDetail.forStatusAndDetail(null, PAGE_NOT_FOUND_LOG_CATEGORY);

        return prolemaDetail;
    }
}
