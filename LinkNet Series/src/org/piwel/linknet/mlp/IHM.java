package org.piwel.linknet.mlp;

import java.util.Date;

/**
 * 
 * @author Atomix
 *
 *Cette interface permet de g�rer les flux de sortie.
 *
 */
public class IHM {

	/**
	 * 
	 * @param msg Chaine de caract�re � afficher ou enregistrer
	 */
	@SuppressWarnings("deprecation")
	public static void info(String msg) {
		System.out.println("["+(new Date()).toGMTString()+"] [INFO] - " + msg);
	}
	@SuppressWarnings("deprecation")
	public static void error(String message) {
		System.err.println("["+(new Date()).toGMTString()+"] [ERROR] - " + message);
	}
    
}
