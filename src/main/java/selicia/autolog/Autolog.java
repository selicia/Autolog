/**
 * "THE BEERWARE LICENSE" (Revision 42):
 * Selicia wrote this code. As long as you retain this
 * notice, you can do whatever you want with this stuff. If we
 * meet someday, and you think this stuff is worth it, you can
 * buy me a beer in return.
 */
package selicia.autolog;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.google.gson.Gson;

@Aspect
public class Autolog {
	private final Gson GSON = new Gson();
	private final Logger LOGGER = Logger.getLogger("Logger");
	
	@Around(value="execution(* *(..)) && @annotation(AutoMethodLog)")
	public Object autoMethodLog(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		
		String source = methodSignature.getDeclaringTypeName();
		String parameters = GSON.toJson(args);
		String signature = methodSignature.getName();
		String timestamp = LocalDateTime.now().toString();
		
		long startTime = System.currentTimeMillis();
		Object methodResult = pjp.proceed();
		
		String returnValue = (methodResult == null) ? "null" : methodResult.toString();
		long stopTime = System.currentTimeMillis();
		long runtime = stopTime - startTime;
		
		String logMessage = "AUTOLOG: " + source + "." + signature
			+ "\n\t Timestamp: " + timestamp
			+ "\n\t Runtime: " + Long.toString(runtime)
			+ "\n\t Parameters: " + parameters
			+ "\n\t Returns: " + returnValue;
		
//		System.out.println(logMessage);
		LOGGER.info(logMessage);
		
		return methodResult;
	}
}
