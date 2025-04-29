package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RecreationalElements {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;
    @Setter
    String name;
    @Setter
    int quantity;
    @Setter
    String description;
}
