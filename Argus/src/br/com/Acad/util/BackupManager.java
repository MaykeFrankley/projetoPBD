package br.com.Acad.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import com.smattme.MysqlExportService;
import com.smattme.MysqlImportService;

public class BackupManager {

	Properties properties = new Properties();
	public static final String BACKUP = "Backup";
	public static final String RESTORE = "Restore";


	public BackupManager(String option, String path) {

		switch (option) {
		case BACKUP:
			properties.setProperty(MysqlExportService.DB_NAME, "argus");
			properties.setProperty(MysqlExportService.DB_USERNAME, "root");
			properties.setProperty(MysqlExportService.DB_PASSWORD, "9612");

			//properties relating to email config
			properties.setProperty(MysqlExportService.EMAIL_HOST, "smtp.gmail.com");
			properties.setProperty(MysqlExportService.EMAIL_PORT, "587");
			properties.setProperty(MysqlExportService.EMAIL_USERNAME, "backupsistemaargus@gmail.com");
			properties.setProperty(MysqlExportService.EMAIL_PASSWORD, "admin9612");
			properties.setProperty(MysqlExportService.EMAIL_FROM, "backupsistemaargus@gmail.com");
			properties.setProperty(MysqlExportService.EMAIL_TO, "backupsistemaargus@gmail.com");

			//set the outputs temp dir
			properties.setProperty(MysqlExportService.TEMP_DIR, new File("backupTemp").getPath());
			properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
			properties.setProperty(MysqlExportService.ADD_IF_NOT_EXISTS, "true");


			MysqlExportService mysqlExportService = new MysqlExportService(properties);
			mysqlExportService.clearTempFiles(false);
			try {
				mysqlExportService.export();
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case RESTORE:

			try {
				String sql = new String(Files.readAllBytes(Paths.get(path)));
				MysqlImportService.builder()
				        .setDatabase("argus")
				        .setSqlString(sql)
				        .setUsername("root")
				        .setPassword("9612")
				        .setDeleteExisting(false)
				        .setDropExisting(false)
				        .importDatabase();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;
		}



	}

}
