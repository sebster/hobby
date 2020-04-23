package com.sebster.remarkable.backup;

import static com.sebster.remarkable.backup.domain.RemarkableBackupType.INCREMENTAL;

import java.io.File;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.sebster.remarkable.backup.domain.RemarkableBackupType;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "remarkable.backup")
public class RemarkableBackupProperties {

	private UUID clientId;
	private File location;
	private RemarkableBackupType type = INCREMENTAL;

}
