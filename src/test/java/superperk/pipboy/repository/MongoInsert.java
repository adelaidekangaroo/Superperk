package superperk.pipboy.repository;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Repeatable(MongoInserts.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(MongoExtension.class)
public @interface MongoInsert {
    String location();

    String collection();
}