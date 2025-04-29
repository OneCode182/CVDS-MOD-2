package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import eci.cvds.mod2.util.States;
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
    String elementId;
    @Setter
    States state;
}
