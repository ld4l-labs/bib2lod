/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO
 */
public class DeleteMe {
	private static final Logger log = LogManager.getLogger();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Print a greeting");
		log.info("Log a greeting");
	}
	
	public void method() throws ClientProtocolException, IOException {
		new DefaultHttpClient().execute((HttpUriRequest)null);
	}

}
