package com.sebster.remarkable.backup;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.remarkable.backup.domain.RemarkableBackupService;
import com.sebster.remarkable.backup.domain.RemarkableBackupStorageService;
import com.sebster.remarkable.backup.infrastructure.FileSystemRemarkableBackupStorageService;
import com.sebster.remarkable.backup.infrastructure.FileSystemItemMetadataStore;
import com.sebster.remarkable.cloudapi.RemarkableClient;

@Configuration
@EnableConfigurationProperties(RemarkableBackupProperties.class)
public class RemarkableBackupConfig {

	@Bean
	public FileSystemItemMetadataStore metadataStore(ObjectMapper objectMapper) {
		return new FileSystemItemMetadataStore(objectMapper);
	}

	@Bean
	public RemarkableBackupStorageService storageService(RemarkableBackupProperties properties, FileSystemItemMetadataStore metadataStore) {
		return new FileSystemRemarkableBackupStorageService(properties.getLocation(), metadataStore);
	}

	@Bean
	public RemarkableBackupService remarkableBackupService(RemarkableClient client, RemarkableBackupStorageService storageService) {
		return new RemarkableBackupService(client, storageService);
	}

}
