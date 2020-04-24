package com.sebster.remarkable.backup;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.sebster.remarkable.backup.domain.RemarkableBackupService;
import com.sebster.remarkable.backup.domain.RemarkableBackupStorageService;
import com.sebster.remarkable.backup.infrastructure.FileSystemRemarkableBackupStorageService;

@Configuration
@EnableConfigurationProperties(RemarkableBackupProperties.class)
public class RemarkableBackupConfig {

	@Bean
	public RemarkableBackupStorageService storageService(
			RemarkableBackupProperties properties,
			Jackson2ObjectMapperBuilder objectMapperBuilder
	) {
		return new FileSystemRemarkableBackupStorageService(
				properties.getLocation(),
				objectMapperBuilder.indentOutput(true).build()
		);
	}

	@Bean
	public RemarkableBackupService remarkableBackupService(RemarkableBackupStorageService storageService) {
		return new RemarkableBackupService(storageService);
	}

}
