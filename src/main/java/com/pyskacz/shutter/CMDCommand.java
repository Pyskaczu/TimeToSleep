package com.pyskacz.shutter;

import java.io.BufferedInputStream;
import java.io.IOException;

public class CMDCommand {

	public static void execute(String command) throws IOException {
		Process proc = Runtime.getRuntime().exec(command);

		try(BufferedInputStream in = new BufferedInputStream(proc.getInputStream())){
			byte[] buff = new byte[255];

			while(proc.isAlive()) {
				if (in.available() > 0) {
					in.read(buff);
				}
			}
		}
	}
}
