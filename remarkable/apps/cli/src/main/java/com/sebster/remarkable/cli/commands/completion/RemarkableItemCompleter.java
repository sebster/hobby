package com.sebster.remarkable.cli.commands.completion;

import static com.sebster.commons.collections.Lists.filter;
import static com.sebster.remarkable.cloudapi.RemarkablePath.parsePath;

import java.util.List;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkableItemType;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRoot;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableItemCompleter implements Completer {

	private final @NonNull CompletionContext completionContext;
	private final RemarkableItemType itemType;
	private final boolean completeMatches;

	@Override
	public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
		if (!completionContext.hasSelectedClient()) {
			return;
		}
		String start = line.word().substring(0, line.wordCursor());
		RemarkableRoot root = completionContext.getSelectedClient().list();
		RemarkablePath path = parsePath(start);
		if (path.isEmpty()) {
			completeCollection(root, "", candidates);
		} else if (start.endsWith("/")) {
			root.findFolder(path).ifPresent(folder -> completeCollection(folder, "", candidates));
		} else {
			root.findCollection(path.getParent()).ifPresent(parent -> completeCollection(parent, path.getName(), candidates));
		}
	}

	private void completeCollection(RemarkableCollection collection, String nameStart, List<Candidate> candidates) {
		List<RemarkableItem> matches = filter(collection.getItems(itemType), item -> item.getName().startsWith(nameStart));
		matches.forEach(item -> completeItem(item, candidates, matches));
	}

	private void completeItem(RemarkableItem item, List<Candidate> candidates, List<RemarkableItem> matches) {
		boolean complete = matches.size() == 1 && completeMatches && (item.isDocument() || item.asFolder().isEmpty());
		String value = item.getPath() + (item.isFolder() ? "/" : "");
		candidates.add(new Candidate(value, value, null, null, null, null, complete));
	}

}
