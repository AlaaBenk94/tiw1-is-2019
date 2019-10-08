package tiw1.annotation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Inherited
/**
 * Source : https://openclassrooms.com/fr/courses/1746031-java-et-les-annotations/2667306-creez-et-utilisez-vos-propres-annotations
 */
public @interface Todo {
    NIVEAU value() default NIVEAU.BUG;
    String auteur() default "Toto";
    String destinataire() default "Toto";
    String commentaire() default "";
}