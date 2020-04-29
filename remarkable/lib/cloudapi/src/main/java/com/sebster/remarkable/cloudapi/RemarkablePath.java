package com.sebster.remarkable.cloudapi;

import static java.util.Collections.singletonList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
public final class RemarkablePath implements Iterable<String> {

	private final List<String> components;

	@Override
	public Iterator<String> iterator() {
		return components.iterator();
	}

	public String getName() {
		return components.get(components.size() - 1);
	}

	public Optional<RemarkablePath> getParent() {
		return components.size() > 1 ?
				Optional.of(new RemarkablePath(components.subList(0, components.size() - 1))) :
				Optional.empty();
	}

	public String getHead() {
		return components.get(0);
	}

	public Optional<RemarkablePath> getTail() {
		return components.size() > 1 ?
				Optional.of(new RemarkablePath(components.subList(1, components.size()))) :
				Optional.empty();
	}

	public List<String> getComponents() {
		return Collections.unmodifiableList(components);
	}

	@Override
	public String toString() {
		return String.join("/", components);
	}

	public static RemarkablePath parse(String path) {
		return new RemarkablePath(
				Stream.of(path.split("/"))
						.filter(component -> !component.isEmpty())
						.collect(Collectors.toCollection(ArrayList::new))
		);
	}

	public static RemarkablePath path(@NonNull String name) {
		return new RemarkablePath(singletonList(name));
	}

	public static RemarkablePath path(RemarkablePath parent, @NonNull String name) {
		if (parent == null) {
			return path(name);
		} else {
			List<String> components = new ArrayList<>(parent.getComponents());
			components.add(name);
			return new RemarkablePath(components);
		}
	}

}
