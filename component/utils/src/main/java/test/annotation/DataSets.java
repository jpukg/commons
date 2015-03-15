package test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * use this annotation when you want to load data in database for Acceptance testing.
 * @author S.Sarabadani
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface DataSets {
	
	/**
	 * set this property when you want to change the default data for this test method.
	 */
	String setUpDataSet() default "";

	/**
	 * set this property when you want to load default data after test method.
	 **/
	String defaultDataSet() default "";
}
