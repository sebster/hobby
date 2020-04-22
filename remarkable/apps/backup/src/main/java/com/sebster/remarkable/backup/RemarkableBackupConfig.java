package com.sebster.remarkable.backup;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.remarkable.backup.domain.RemarkableBackupService;
import com.sebster.remarkable.backup.domain.RemarkableBackupStorageService;
import com.sebster.remarkable.backup.infrastructure.FileSystemRemarkableBackupStorageService;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;

@Configuration
@EnableConfigurationProperties(RemarkableBackupProperties.class)
public class RemarkableBackupConfig {

	@Bean
	public RemarkableBackupStorageService storageService(RemarkableBackupProperties properties, ObjectMapper objectMapper) {
		return new FileSystemRemarkableBackupStorageService(properties.getLocation(), objectMapper);
	}

	@Bean
	public RemarkableBackupService remarkableBackupService(
			RemarkableClientManager clientManager,
			RemarkableBackupStorageService storageService
	) {
		return new RemarkableBackupService(clientManager, storageService);
	}

}
