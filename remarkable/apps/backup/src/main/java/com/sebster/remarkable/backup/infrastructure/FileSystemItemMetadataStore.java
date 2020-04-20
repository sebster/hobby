package com.sebster.remarkable.backup.infrastructure;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FileSystemItemMetadataStore {

	private final ObjectMapper mapper;

	public void save(@NonNull File file, @NonNull Collection<ItemMetadata> metadata) {
		try {
			mapper.writeValue(file, metadata);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public List<ItemMetadata> load(@NonNull File file) {
		try {
			return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, ItemMetadata.class));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
