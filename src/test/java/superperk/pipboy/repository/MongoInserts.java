package superperk.pipboy.repository;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(MongoExtension.class)
public @interface MongoInserts {
    MongoInsert[] value() default {};
}