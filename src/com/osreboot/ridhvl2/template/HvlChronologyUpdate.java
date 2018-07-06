package com.osreboot.ridhvl2.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HvlChronologyUpdate {
	public String label();
	public int launchCode() default -1;
	public int chronology() default -1;
}
