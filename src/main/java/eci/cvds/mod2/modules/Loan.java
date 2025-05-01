package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;

import eci.cvds.mod2.util.State;
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
public class Loan {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;
    @Setter
    @NotBlank
    String elementId;
    @Setter
    @NotNull
    State state;
}
