package edu.ccrm.config;

public class AppConfig {
	private static AppConfig instance;
	private String exportDir = "exports";
	private String backupDir = "backups";
	
	private AppConfig() {}
	public static AppConfig getInstance() {
		if(instance == null) {
			instance = new AppConfig();
		}
		return instance;
	}
	public String getExportDir() {return exportDir;}
	public void setExportDir(String exportDir) {this.exportDir=exportDir;}
	
	public String getBackupDir() {return backupDir;}
	public void setBackupDir(String backupDir) {this.backupDir=backupDir;}

}
