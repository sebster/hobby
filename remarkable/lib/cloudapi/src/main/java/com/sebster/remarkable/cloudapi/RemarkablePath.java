package com.sebster.remarkable.cloudapi;

import static com.sebster.commons.collections.Lists.map;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toCollection;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
public final class RemarkablePath implements Iterable<String> {

	private static final RemarkablePath EMPTY_PATH = new RemarkablePath(emptyList());

	private final List<String> components;

	@Override
	public Iterator<String> iterator() {
		return components.iterator();
	}

	public boolean isEmpty() {
		return components.isEmpty();
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	public String getName() {
		checkNotEmpty("Empty path does not have a name.");
		return components.get(components.size() - 1);
	}

	public RemarkablePath getParent() {
		checkNotEmpty("Empty path does not have a parent.");
		return new RemarkablePath(components.subList(0, components.size() - 1));
	}

	public String getHead() {
		checkNotEmpty("Empty path does not have a head.");
		return components.get(0);
	}

	public RemarkablePath getTail() {
		checkNotEmpty("Empty path does not have a tail.");
		return new RemarkablePath(components.subList(1, components.size()));
	}

	public List<String> getComponents() {
		return Collections.unmodifiableList(components);
	}

	public RemarkablePath append(@NonNull String name) {
		return append(new RemarkablePath(singletonList(name)));
	}

	public RemarkablePath append(@NonNull RemarkablePath path) {
		List<String> components = new ArrayList<>(this.components);
		components.addAll(path.getComponents());
		return new RemarkablePath(components);
	}

	@Override
	public String toString() {
		return String.join("/", components);
	}

	private void checkNotEmpty(String message) {
		if (isEmpty()) {
			throw new IllegalStateException(message);
		}
	}

	public static RemarkablePath parsePath(String path) {
		return new RemarkablePath(
				Stream.of(path.split("/"))
						.filter(component -> !component.isEmpty())
						.collect(toCollection(ArrayList::new))
		);
	}

	public static List<RemarkablePath> parsePaths(@NonNull Collection<String> paths) {
		return map(paths, RemarkablePath::parsePath);
	}

	public static RemarkablePath empty() {
		return EMPTY_PATH;
	}

}
