package superperk.pipboy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "special")
public final class Special {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
}