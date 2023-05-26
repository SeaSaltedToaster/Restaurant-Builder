package com.seaSaltedToaster.restaurantGame.save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SaveWriter {
	
	public FileWriter writer;
	
	private void setWriterFile(File file) {
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFile(File file, String string, boolean newFileWriter) {
		if(newFileWriter)
			setWriterFile(file);
		try {
			writer.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readFile(File path)
	{
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(path.toPath());
		} catch (IOException e) {
			return "NO_FILE";
		}
		return new String(encoded, StandardCharsets.US_ASCII);
	}

}
