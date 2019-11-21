package br.com.Acad.util;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Settings {

	private static String userHomeFolder = System.getProperty("user.dir");

	public static void Save(JSONObject object) {

		try(FileWriter file = new FileWriter(userHomeFolder+"/Settings.json")) {
			file.write(object.toJSONString());
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static JSONObject get() {
		JSONObject options = new JSONObject();
		JSONParser jsonparser = new JSONParser();
		try(FileReader reader = new FileReader(userHomeFolder+"/Settings.json")){
			Object obj = jsonparser.parse(reader);
			return (JSONObject) obj;
		} catch (Exception e) {
			options.put("TemaEscuro", true);
			options.put("Animacoes", true);
			options.put("Hora", "00:00");
			options.put("DiaDoMes", 1);
			options.put("minAlunos", 5);
			options.put("maxAlunos", 40);
			Save(options);
		}
		return options;
	}

	public static void SaveDadosBancarios(JSONObject object) {

		try(FileWriter file = new FileWriter(userHomeFolder+"/DadosBancarios.json")) {
			file.write(object.toJSONString());
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static JSONObject getDadosBancarios() {
		JSONObject options = new JSONObject();
		JSONParser jsonparser = new JSONParser();
		try(FileReader reader = new FileReader(userHomeFolder+"/DadosBancarios.json")){
			Object obj = jsonparser.parse(reader);
			return (JSONObject) obj;
		} catch (Exception e) {
			options.put("escola", "Null");
			options.put("numeroConta", "Null");
			options.put("digitoConta", "Null");
			options.put("agencia", "Null");
			options.put("cnpj", "Null");
			options.put("nomeBanco", "Null");
			SaveDadosBancarios(options);
		}
		return options;
	}

}
