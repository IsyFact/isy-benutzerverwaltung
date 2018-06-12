package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation.passwordpolicy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { PasswortPolicyPasswortZuruecksetzenValidator.class, PasswortPolicyBenutzerAnlegenValidator.class })
@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface PasswortPolicyUeberpruefen {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
