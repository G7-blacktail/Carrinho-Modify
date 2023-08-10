package com.certificadoranacional.ac.web.resources;

import com.certificadoranacional.ac.core.Log;
import com.certificadoranacional.ac.core.model.ExceptionRepresentation;
import com.google.common.base.Throwables;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ArExceptionMapper extends ResponseEntityExceptionHandler {

  public ArExceptionMapper() {
    super();
  }

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ResponseEntity<Object> handleThrowable(final Exception ex, final WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return this.handleExceptionInternal(ex, null, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    HttpStatus statusToReturn = HttpStatus.INTERNAL_SERVER_ERROR;
    Log.getLog().error(ex.getMessage(), ex);
    String message = Throwables.getRootCause(ex).getMessage();
    String stacktrace = Throwables.getStackTraceAsString(ex);
    if (ex instanceof HttpServerErrorException) {
      HttpServerErrorException hsee = (HttpServerErrorException) ex;
      statusToReturn = hsee.getStatusCode();
    }
    ExceptionRepresentation representation = new ExceptionRepresentation();
    representation.setMessage(message);
    representation.setStacktrace(stacktrace);
    return ResponseEntity.status(statusToReturn).contentType(MediaType.APPLICATION_JSON).body(representation);
  }

}
