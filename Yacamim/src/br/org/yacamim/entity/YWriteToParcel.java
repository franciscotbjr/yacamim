/**
 * YWriteToParcel.java
 *
 * Copyright 2012 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.org.yacamim.entity;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation class is meant to be use when it's necessary to sign to <strong>Yacamim</strong> that an field (that is accessed from a getter) of a <tt>BaseEntity</tt> subclass will not 
 * be persisted when the class it's member of will be "parceled" (serialized). Its default value is <tt>true</tt>, so when it's desired to a field 
 * not to be "parceled" as part of all instance, it's necessary to set it ro <tt>false</tt>.<br/>
 * <p>
 * <strong>Ex.:</strong><br/><br/>
 * public class User extends BaseEntity {<br/><br/>
 * 
 * &nbsp;&nbsp;&nbsp;&nbsp;@YWriteToParcel(false)<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;private String getPassword() {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return this.password;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/><br/>
 * 
 * }<br/>
 * </p>
 * Class YWriteToParcel TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Target({ METHOD})
@Retention(RUNTIME)
@Documented
public @interface YWriteToParcel {
	
	boolean value() default true;
	
}
