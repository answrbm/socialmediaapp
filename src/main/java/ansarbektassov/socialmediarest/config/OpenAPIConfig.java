package ansarbektassov.socialmediarest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "answrbm",
                        email = "answrbm@mail.com",
                        url = "https://answrbm.com"
                ),
                description = "Open API documentation for Social Media",
                summary = "Open API specification - answrbm",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://license.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod Env",
                        url = "http://prodenv.com"
                )
        },
        security = {
                @SecurityRequirement(name = "Authorization")
        }
)
@SecurityScheme(
        name = "Authorization",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
