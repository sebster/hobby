package com.sebster.remarkable.backup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sebster.remarkable.backup.domain.RemarkableBackupService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@SpringBootApplication
@AllArgsConstructor
public class RemarkableBackup implements CommandLineRunner {

	private final @NonNull RemarkableBackupProperties backupProperties;
	private final @NonNull RemarkableBackupService backupService;

	@Override
	public void run(String... args) {
		backupService.backup(backupProperties.getClientId());
	}

	public static void main(String[] args) {
		SpringApplication.run(RemarkableBackup.class, args);
	}

}