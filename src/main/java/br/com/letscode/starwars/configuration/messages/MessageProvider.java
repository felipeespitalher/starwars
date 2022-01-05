package br.com.letscode.starwars.configuration.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageProvider {

    private final MessageSource messageSource;

    public String getMessage(String code, String... parameters) {
        return messageSource.getMessage(code, parameters,
                Locale.getDefault());
    }
}
