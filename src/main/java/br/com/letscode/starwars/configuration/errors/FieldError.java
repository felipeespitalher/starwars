package br.com.letscode.starwars.configuration.errors;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldError {

    private final String field;
    private final String message;

}
