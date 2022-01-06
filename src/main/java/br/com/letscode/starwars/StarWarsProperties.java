package br.com.letscode.starwars;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "star-wars")
public class StarWarsProperties {

    private Integer maximumTraitorReports;

}
