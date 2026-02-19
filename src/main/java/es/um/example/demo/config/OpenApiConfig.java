package es.um.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestión de Tareas",
                version = "1.0.0",
                description = "API REST para gestionar tareas (TODO list) con asunto, fecha y estado",
                contact = @Contact(name = "Universidad de Murcia", email = "example@um.es"),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        servers = @Server(url = "${springdoc.server.url}", description = "Servidor de la API")
)
@SecurityScheme(
        name = "oidc",
        type = SecuritySchemeType.OPENIDCONNECT,
        openIdConnectUrl = "${springdoc.security.issuer-uri}/.well-known/openid-configuration"
)
public class OpenApiConfig {
}
