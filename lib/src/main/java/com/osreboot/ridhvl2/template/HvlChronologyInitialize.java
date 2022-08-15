package com.osreboot.ridhvl2.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.osreboot.ridhvl2.HvlAction;

/**
 * Used to mark a field of type {@linkplain HvlAction.A1} as an {@linkplain HvlChronology} initialize event and 
 * allows the event to be registered. See HvlChronology's class comment for more information on registering events.
 * 
 * @author os_reboot
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HvlChronologyInitialize {
	public String label();
	public int launchCode() default -1;
	public int chronology() default -1;
}
