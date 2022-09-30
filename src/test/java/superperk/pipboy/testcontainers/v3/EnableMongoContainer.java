package superperk.pipboy.testcontainers.v3;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableMongoContainer {
    String image() default "";

    int port() default -1; // not implemented, yet
}