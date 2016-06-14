/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.mogilefs.exception.BadHostFormatException;
import com.ouer.solar.mogilefs.exception.MogileException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;
import com.ouer.solar.mogilefs.exception.StorageCommunicationException;
import com.ouer.solar.mogilefs.exception.TrackerCommunicationException;
import com.ouer.solar.nio.FileChannelUtil;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class LocalFileMogilefs implements Mogilefs {

	private static final Logger LOG = LoggerFactory.getLogger(LocalFileMogilefs.class);

	private final File topDir;
	private String domain;
	private File domainDir;

	public LocalFileMogilefs(File topDir, String domain) throws IOException {
		this.topDir = topDir;
		this.domain = domain;

		if (!topDir.exists()) {
			throw new FileNotFoundException(topDir.getAbsolutePath());
		}

		this.domainDir = new File(topDir, domain);
		if (!domainDir.exists()) {
			if (!domainDir.mkdir()) {
				throw new FileNotFoundException(domainDir.getAbsolutePath());
			}
		}
	}

	public void reload(String domain, String[] trackerStrings)
			throws NoTrackersException, BadHostFormatException {
		this.domain = domain;
		this.domainDir = new File(topDir, domain);
		if (!domainDir.exists()) {
			if (!domainDir.mkdir()) {
				LOG.error("couldn't make dir " + domainDir.getAbsolutePath());
				throw new NoTrackersException();
			}
		}
	}

	@Override
    public OutputStream newFile(String key, String storageClass, long byteCount)
			throws NoTrackersException, TrackerCommunicationException,
			StorageCommunicationException {

		final File file = new File(domainDir, key);
		try {
			return new FileOutputStream(file);

		} catch (final IOException e) {
			throw new StorageCommunicationException("couldn't open file "
					+ file.getAbsolutePath());
		}
	}

	@Override
	public void storeFile(String key, File file) throws MogileException {
		storeFile(key, "", file);
	}

	@Override
	public void storeFile(String key, String storageClass,
			InputStream inputStream) throws MogileException {

		OutputStream output = null;
		try {
			output = this.newFile(key, storageClass, inputStream.available());
			StreamUtil.io(inputStream, output);
		} catch (final IOException e) {

			throw new StorageCommunicationException(e.getMessage());
		} finally {
			StreamUtil.close(output);
			StreamUtil.close(inputStream);
		}

	}

	@Override
	public void storeFile(String key, InputStream inputStream)
			throws MogileException {
		storeFile(key, "", inputStream);
	}

	@Override
    public void storeFile(String key, String storageClass, File file)
			throws MogileException {
		final File storedFile = new File(domainDir, key);

		FileOutputStream out = null;
		FileInputStream in = null;

		try {
			out = new FileOutputStream(storedFile);
			in = new FileInputStream(file);

			StreamUtil.io(in, out);

		} catch (final IOException e) {

			throw new StorageCommunicationException(e.getMessage());
		} finally {
			StreamUtil.close(out);
			StreamUtil.close(in);
		}

	}

	@Override
	public void storeFiles(Map<String, InputStream> streams, String storageClass)
			throws MogileException {
		try {
			for (final Map.Entry<String, InputStream> entry : streams.entrySet()) {
				final String key = entry.getKey();
				final InputStream input = entry.getValue();

				final OutputStream output = this.newFile(key, storageClass,
						input.available());
				StreamUtil.io(input, output);
				StreamUtil.close(output);
				StreamUtil.close(input);
			}

		} catch (final IOException e) {
			throw new StorageCommunicationException(e.getMessage());
		}
	}

	@Override
	public void storeFiles(Map<String, InputStream> streams)
			throws MogileException {
		storeFiles(streams, "");
	}

	@Override
    public File getFile(String key, File destination)
			throws NoTrackersException, TrackerCommunicationException,
			IOException, StorageCommunicationException {
		final File storedFile = new File(domainDir, key);
		FileOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new FileOutputStream(destination);
			in = new FileInputStream(storedFile);

			// StreamUtil.copy(in, out);
			FileChannelUtil.copy(in, out);
			return destination;

		} catch (final IOException e) {

			throw new StorageCommunicationException(e.getMessage());
		} finally {
			StreamUtil.close(out);
			StreamUtil.close(in);
		}
	}

	@Override
    public byte[] getFileBytes(String key) throws NoTrackersException,
			TrackerCommunicationException, IOException,
			StorageCommunicationException {
		final File storedFile = new File(domainDir, key);

		final byte[] buffer = new byte[(int) storedFile.length()];

		FileInputStream in = null;
		try {
			in = new FileInputStream(storedFile);

			return StreamUtil.readBytesByFast(in, buffer.length);
		} finally {
			StreamUtil.close(in);
		}
	}

	@Override
    public InputStream getFileStream(String key) throws NoTrackersException,
			TrackerCommunicationException, StorageCommunicationException {
		final File storedFile = new File(domainDir, key);

		try {
			return new FileInputStream(storedFile);
		} catch (final IOException e) {
			throw new StorageCommunicationException(e.getMessage());
		}
	}

	@Override
	public InputStream getFileStream(String key, boolean noverify)
			throws NoTrackersException, TrackerCommunicationException,
			StorageCommunicationException {
		final File storedFile = new File(domainDir, key);

		try {
			return new FileInputStream(storedFile);
		} catch (final IOException e) {
			throw new StorageCommunicationException(e.getMessage());
		}
	}

	@Override
    public void delete(String key) throws NoTrackersException,
			NoTrackersException {
		final File storedFile = new File(domainDir, key);

		storedFile.delete();
	}

	@Override
    public void sleep(int seconds) throws NoTrackersException,
			TrackerCommunicationException {
		try {
			Thread.sleep(seconds * 1000);
		} catch (final InterruptedException e) {
			LOG.error("", e);
		}
	}

	@Override
    public void rename(String fromKey, String toKey) throws NoTrackersException {
		final File fromFile = new File(domainDir, fromKey);
		final File toFile = new File(domainDir, toKey);

		fromFile.renameTo(toFile);
	}

	@Override
    public String[] getPaths(String key, boolean noverify)
			throws NoTrackersException {
		final File storedFile = new File(domainDir, key);

		return new String[] { "file://" + storedFile.getAbsolutePath() };
	}

	@Override
    public String getDomain() {
		return domain;
	}

	@Override
	public boolean exists(String key) throws NoTrackersException {
		return getPaths(key, true) != null;
	}

	@Override
	public String getUrl(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}

}
