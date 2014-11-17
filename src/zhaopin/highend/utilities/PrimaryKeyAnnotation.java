package zhaopin.highend.utilities;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ׿ƸORM
 * 
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKeyAnnotation {

	
	AnnoType value() default AnnoType.None;
	
	
	public enum AnnoType{ PrimaryKey,Identity,DBIngore,None};
}
