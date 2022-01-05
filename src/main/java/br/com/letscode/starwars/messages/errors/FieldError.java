package br.com.letscode.starwars.messages.errors;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldError {

    private final String field;
    private final String message;

}
