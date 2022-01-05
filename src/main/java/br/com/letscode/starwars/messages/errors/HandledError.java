package br.com.letscode.starwars.messages.errors;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HandledError {

    private final int status;
    private final String error;
    private final String message;
    private final LocalDateTime timestamp;
    private final Object errors;

}
