/**
 * YWriteToParcel.java
 *
 * Copyright 2012 yacamim.org.br
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
