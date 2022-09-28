package superperk.pipboy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

@Entity
@Data
@Builder
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "special")
public final class Special {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(columnDefinition = "serial")
    private Long id;
    private String title;
    private String description;
}