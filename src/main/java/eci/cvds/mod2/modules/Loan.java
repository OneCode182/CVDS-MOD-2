package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;

import eci.cvds.mod2.util.State;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "loans")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un préstamo de un elemento recreativo.")
public class Loan {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador único del préstamo", example = "66396e1a19e7b3038c7be552", accessMode = Schema.AccessMode.READ_ONLY)
    String id;
    @Setter
    @NotBlank
    @Schema(description = "ID del elemento recreativo asociado al préstamo", example = "abc123")
    String elementId;
    @Setter
    @NotNull
    @Schema(description = "Estado del préstamo", example = "PRESTAMO_DEVUELTO")
    State state;
}
