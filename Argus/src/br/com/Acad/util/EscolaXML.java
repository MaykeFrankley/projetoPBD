package br.com.Acad.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

import br.com.Acad.model.Escola;

public class EscolaXML {

	public static Escola escola;
	private static String userHomeFolder = System.getProperty("user.dir");
	private static XStream xStream = new XStream(new Dom4JDriver());
	private static File file = new File(userHomeFolder, "escola.xml");

	public static void Save(Escola escola) {

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			else {
				file.delete();
				file.createNewFile();
			}

			OutputStream stream = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(stream, Charset.forName("UTF-8"));
			xStream.toXML(escola, writer);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Escola get() {

		if (!file.exists()){
			return null;
		}
		else {
			return (Escola) xStream.fromXML(file);
		}

	}


}
