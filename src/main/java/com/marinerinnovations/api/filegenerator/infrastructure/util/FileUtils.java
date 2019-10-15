package com.marinerinnovations.api.filegenerator.infrastructure.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;

/**
 * util Class
 * @author adas1
 *
 */
public class FileUtils {

	/**
	 * get the File Object from a resource name
	 * @param fileName : the name of the resource file to get
	 * @return an object of type File from resource name
	 */
	public File getFileFromResources(String fileName) {
		return new File(getResource(fileName).getFile());
	}

	/**
	 * get the FileReader Object from a resource name
	 * @param fileName : the name of the resource file to get
	 * @return an object of type FileReader from resource name
	 */
	public FileReader getFileReaderFromResources(String fileName) throws FileNotFoundException {
		return new FileReader(getResource(fileName).getFile());
	}

	/**
	 * get the URL object from a file name
	 * @param fileName
	 * @return URL object
	 */
	private URL getResource(String fileName) {

		return getClass().getClassLoader().getResource(fileName);
	}
	
	/** get the InputStream object for the file name
	 * @param fileName
	 * @return
	 */
	public InputStream getInputStreamForFile(String fileName){
		return getClass().getClassLoader().getResourceAsStream(fileName);
	}
        
}