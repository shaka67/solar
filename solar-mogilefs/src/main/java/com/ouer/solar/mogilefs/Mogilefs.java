/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.ouer.solar.mogilefs.exception.MogileException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;
import com.ouer.solar.mogilefs.exception.StorageCommunicationException;
import com.ouer.solar.mogilefs.exception.TrackerCommunicationException;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Mogilefs {

	/**
	 * Get a ref to an OutputStream so you can store a new file. The original
	 * implementation returns null on an error, but this one throws an exception
	 * if something goes wrong, so null is never returned.
	 *
	 * @param key
	 * @param storageClass
	 * @return
	 */

	public OutputStream newFile(String key, String storageClass, long byteCount) throws NoTrackersException,
																								TrackerCommunicationException,
																								StorageCommunicationException;

	/**
	 * Copy the given file to mogile.
	 *
	 * @param key
	 * @param storageClass
	 * @param file
	 * @throws MogileException
	 */

	public void storeFile(String key, String storageClass, File file) throws MogileException;

	public void storeFile(String key, File file) throws MogileException;

	public void storeFile(String key, String storageClass, InputStream inputStream) throws MogileException;

	public void storeFile(String key, InputStream inputStream) throws MogileException;

	public void storeFiles(Map<String, InputStream> streams) throws MogileException;

	public void storeFiles(Map<String, InputStream> streams, String storageClass) throws MogileException;

	/**
	 * Read in an file and store it in the provided file. Return the reference
	 * to the file, or null if we couldn't find the object with the given key
	 *
	 * @param key
	 * @param destination
	 * @throws NoTrackersException
	 * @throws TrackerCommunicationException
	 */

	public File getFile(String key, File destination) throws NoTrackersException,
															   TrackerCommunicationException,
															   IOException,
															   StorageCommunicationException;

	/**
	 * Read a file into memory.
	 *
	 * @param key
	 * @return
	 * @throws NoTrackersException
	 * @throws TrackerCommunicationException
	 * @throws IOException
	 * @throws StorageCommunicationException
	 */

	public byte[] getFileBytes(String key) throws NoTrackersException,
													 TrackerCommunicationException,
													 IOException,
													 StorageCommunicationException;

	/**
	 * Retrieve the data from some file. Return null if the file doesn't exist,
	 * or throw an exception if we just can't get it from any of the storage
	 * nodes
	 *
	 * @param key
	 * @return
	 */

	public InputStream getFileStream(String key) throws NoTrackersException,
															  TrackerCommunicationException,
															  StorageCommunicationException;

	public InputStream getFileStream(String key, boolean noverify) throws NoTrackersException,
																				 TrackerCommunicationException,
																				 StorageCommunicationException;

	/**
	 * Delete the given file. A non-existant file will not cause an error.
	 *
	 * @param key
	 * @throws NoTrackersException
	 */

	public void delete(String key) throws NoTrackersException, NoTrackersException;

	/**
	 * Tell the server to sleep for a few seconds.
	 *
	 * @throws NoTrackersException
	 */

	public void sleep(int seconds) throws NoTrackersException, TrackerCommunicationException;

	/**
	 * Rename the given key. Note that you won't get an error if the key doesn't
	 * exist.
	 *
	 * @param key
	 * @throws NoTrackersException
	 */

	public void rename(String fromKey, String toKey) throws NoTrackersException;

	/**
	 * Return a list of URL's that specify where this file is stored. Return
	 * null if there was an error from the server.
	 *
	 * @param key
	 * @return array of Strings that are URLs that specify where this file is
	 *         stored, or null if there was an error
	 * @throws NoTrackersException
	 */

	public String[] getPaths(String key, boolean noverify) throws NoTrackersException;

	/**
	 * Return the name of the domain this client is associated with
	 *
	 * @return
	 */
	public String getDomain();

	public boolean exists(String key) throws NoTrackersException;

	public String getUrl(String key);

	public void stop() throws Exception;
}