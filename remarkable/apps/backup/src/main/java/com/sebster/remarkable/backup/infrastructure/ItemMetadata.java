package com.sebster.remarkable.backup.infrastructure;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMetadata {

	private @NonNull UUID id;
	private int version;
	private UUID parentId;
	private @NonNull ItemType type;
	private @NonNull String name;
	private @NonNull Instant modificationTime;
	private int currentPage;
	private boolean bookmarked;

}
