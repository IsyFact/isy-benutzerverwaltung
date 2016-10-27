package de.bund.bva.isyfact.benutzerverwaltung.core.rollenverwaltung.validation;

import de.bund.bva.isyfact.benutzerverwaltung.common.konstanten.ValidierungSchluessel;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(min = 1, message = "{validation.rolle.id.leer}")
@Documented
@Constraint(validatedBy = ValideRollenIdValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface ValideRollenId {

    String message() default "{" + ValidierungSchluessel.ROLLE_ID_UNGUELTIG + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
