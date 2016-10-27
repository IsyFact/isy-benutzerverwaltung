package de.bund.bva.isyfact.benutzerverwaltung.core.benutzerverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ValiderBenutzername
@Documented
@Constraint(validatedBy = BekannterBenutzernameValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface BekannterBenutzername {

    String message() default "{" + ValidierungSchluessel.MSG_BENUTZERNAME_NICHT_VORHANDEN + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
