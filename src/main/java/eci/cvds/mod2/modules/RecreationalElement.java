package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Elements")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Elemento recreativo dentro del sistema")
public class RecreationalElement {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "ID único del elemento", example = "642b7f1acb0a1d0001fae3d4")
    String id;
    @Setter
    @NotBlank(message = "The name cannot be null")
    @Schema(description = "Nombre del elemento", example = "Uno")
    String name;
    @Setter
    @Positive(message = "The quantity cannot be null")
    @Schema(description = "Cantidad disponible del elemento", example = "5")
    int quantity;
    @Setter
    @Schema(description = "Descripción del elemento", example = "Juego de mesa para 2 a 10 jugadores")
    String description;
}
