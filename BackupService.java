package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    private final Path backupRoot;
    
    public BackupService(String backupRootDir) {
    	this.backupRoot=Paths.get(backupRootDir);
    	try {
    		if(!Files.exists(backupRoot)) {
    			Files.createDirectories(backupRoot);
    		}
    	}
    	catch(IOException e) {
    		throw new RuntimeException("could not create backup directory",e);
    		
    	}
    }
    public Path createBackup(String sourceDir) throws IOException{
    	Path source = Paths.get(sourceDir);
    	if(!Files.exists(source)) {
    		throw new IllegalArgumentException("source folder does not exist:"+sourceDir);
    		
    	}
    	String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    	Path targetDir = backupRoot.resolve("Backup_"+timestamp);
    	Files.createDirectories(targetDir);
    	
    	Files.walkFileTree(source, new SimpleFileVisitor<>() {
    		@Override
    		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
    			Path targetFile=targetDir.resolve(source.relativize(file));
    			Files.createDirectories(targetFile.getParent());
    			Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
    			return FileVisitResult.CONTINUE;
    		}
    	});
    	return targetDir;
    	}
    public long computeFolderSize(Path folder) throws IOException{
    	final long[] size = {0};
    	Files.walkFileTree(folder, new SimpleFileVisitor<>() {
    		@Override
    		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)throws IOException{
    			size[0] += attrs.size();
    			return FileVisitResult.CONTINUE;
    		}
    	});
    	return size[0];
    }
    
    
}
