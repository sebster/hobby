package com.sebster.remarkable.backup;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sebster.remarkable.backup.domain.RemarkableBackupService;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@SpringBootApplication
@AllArgsConstructor
public class RemarkableBackup implements CommandLineRunner {

	private final @NonNull RemarkableClientManager clientManager;
	private final @NonNull RemarkableBackupProperties backupProperties;
	private final @NonNull RemarkableBackupService backupService;

	@Override
	public void run(String... args) {
		getClients(args).forEach(client -> backupService.backup(client, backupProperties.getType()));
	}

	public List<RemarkableClient> getClients(String... args) {
		// Arguments override properties.
		if (args.length > 0) {
			return Stream.of(args).map(clientManager::getClient).collect(toList());
		}

		// Property overrides client config file.
		if (backupProperties.getClientId() != null) {
			return singletonList(clientManager.getClient(backupProperties.getClientId()));
		}

		// Default to the client config file.
		return clientManager.listClients();
	}

	public static void main(String[] args) {
		SpringApplication.run(RemarkableBackup.class, args);
	}

}