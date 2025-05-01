package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RecreationalElement {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;
    @Setter
    @NotBlank(message = "The name cannot be null")
    String name;
    @Setter
    @Positive(message = "The quantity cannot be null")
    int quantity;
    @Setter
    String description;
}
