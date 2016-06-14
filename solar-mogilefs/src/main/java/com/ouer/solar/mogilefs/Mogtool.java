/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.File;

import com.ouer.solar.mogilefs.exception.BadHostFormatException;
import com.ouer.solar.mogilefs.exception.MogileException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Mogtool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			showUsage();
			return;
		}
		final String command = args[0];
		if ("inject".equalsIgnoreCase(command)) {
			if (args.length < 4) {
				System.err.println("mogtool inject <key> <class> <filename>");
				return;
			}
			try {
				inject(args[1], args[2], args[3]);
			} catch (final MogileException e) {
				System.err.println("Error trying to inject file: " + e);
				e.printStackTrace();
			}
		}
		// TODO: add other commands
		else {
			System.err.println("Unknown command: '" + command + "'");
			showUsage();
		}
	}

	// TODO: make this configuration not hard-coded
	protected static Mogilefs createMogileFS()
			throws NoTrackersException, BadHostFormatException {
		final Trackers trackers = new Trackers()
				.setHosts("tracker1.qf.com:7001,"
						+ "tracker2.qf.com:7001,"
						+ "tracker3.qf.qf:7001,"
						+ "tracker4.qf.com:7001");
		final Mogilefs mogileFS = new PooledMogilefs(trackers,
				"creator.qf.com", "nginx.qf.com");
		return mogileFS;
	}

	public static void inject(String key, String storageClass, String filename)
			throws MogileException {
		final Mogilefs mogileFS = createMogileFS();

		final File file = new File(filename);
		System.out
				.println("storing " + file + " as " + key + " to " + mogileFS);
		mogileFS.storeFile(key, storageClass, file);
	}

	private static void showUsage() {
		System.err.println("Mogtool: <command> [args]");
		System.err.println(" where command is one of the following:");
		System.err.println("\t inject <key> <class> <filename>");
		System.err.println("\t\t  (other functionality coming soon...)");
		System.err.println();
	}

}
