/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.api.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used for letting the PluginManager know that it is
 * <br>
 * a handler and it wants to run when a certain event is fired
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
@Retention(RetentionPolicy.RUNTIME)
public abstract @interface EventHandler {}
