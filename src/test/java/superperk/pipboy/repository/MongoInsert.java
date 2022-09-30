package superperk.pipboy.repository;

import java.lang.annotation.*;

@Repeatable(MongoInserts.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MongoInsert {
    String location();

    String collection();
}