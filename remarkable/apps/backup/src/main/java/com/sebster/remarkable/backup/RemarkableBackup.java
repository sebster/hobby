package com.sebster.remarkable.backup;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sebster.remarkable.backup.domain.RemarkableBackupService;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@SpringBootApplication
@AllArgsConstructor
public class RemarkableBackup implements ApplicationRunner {

	private final @NonNull RemarkableClientManager clientManager;
	private final @NonNull RemarkableBackupProperties backupProperties;
	private final @NonNull RemarkableBackupService backupService;

	@Override
	public void run(ApplicationArguments args) {
		getClients(args).forEach(client -> backupService.backup(client, backupProperties.getType()));
	}

	public List<RemarkableClient> getClients(ApplicationArguments args) {
		// Arguments override properties.
		if (args.getNonOptionArgs().size() > 0) {
			return args.getNonOptionArgs().stream().map(clientManager::getClient).collect(toList());
		}

		// Property overrides client config.
		if (backupProperties.getClientId() != null) {
			return singletonList(clientManager.getClient(backupProperties.getClientId()));
		}

		// Default to the client config.
		return clientManager.listClients();
	}

	public static void main(String[] args) {
		SpringApplication.run(RemarkableBackup.class, args);
	}

}