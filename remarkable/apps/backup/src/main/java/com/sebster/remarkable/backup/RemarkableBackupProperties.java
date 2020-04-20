package com.sebster.remarkable.backup;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "remarkable.backup")
public class RemarkableBackupProperties {

	private File location;

}
