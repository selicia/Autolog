/**
 * "THE BEERWARE LICENSE" (Revision 42):
 * Selicia wrote this code. As long as you retain this
 * notice, you can do whatever you want with this stuff. If we
 * meet someday, and you think this stuff is worth it, you can
 * buy me a beer in return.
 */
package selicia.autolog;

public class HelloAutolog {

	public static void main(String[] args) {
		helloAutolog("Hello Autolog!");
	}
	
	@AutoMethodLog
	public static void helloAutolog(String message) {
		System.out.println(message);
	}

}
