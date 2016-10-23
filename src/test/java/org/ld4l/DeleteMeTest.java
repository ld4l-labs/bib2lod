/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

/**
 * TODO
 */
public class DeleteMeTest {
	@Test(expected = IllegalArgumentException.class)
	public void testMethod() throws ClientProtocolException, IOException {
		new DeleteMe().method();
	}
}
