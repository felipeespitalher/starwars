package br.com.letscode.starwars.configuration.messages.errors;

import br.com.letscode.starwars.configuration.messages.MessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdviceExceptionHandler {

    private final MessageProvider messageProvider;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> beanValidationException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.debug(HttpStatus.BAD_REQUEST.name(), ex);
        List<FieldError> errors = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ex.getBindingResult().getAllErrors())) {
            ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(FieldError.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(HandledError.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> anyException(HttpServletRequest request, Exception ex) {
        // TODO: send logs to any repository
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(HandledError.builder()
                        .message(translateMessage(ex.getMessage()))
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .errors(getGenericErrorMessage(ex))
                        .build());
    }

    private String getGenericErrorMessage(Exception ex) {
        if (StringUtils.hasText(ex.getMessage())) {
            return ex.getMessage();
        } else {
            return translateMessage("generic.unexpected.exception.message");
        }
    }

    private String translateMessage(String rawMessage) {
        try {
            return messageProvider.getMessage(rawMessage);
        } catch (NoSuchMessageException e) {
            return rawMessage;
        }
    }

}
