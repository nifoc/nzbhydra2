package org.nzbhydra.github.mavenreleaseplugin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChangelogVersionEntry implements Comparable<ChangelogVersionEntry> {

    private String version;
    private List<ChangelogChangeEntry> changes;

    @Override
    public int compareTo(ChangelogVersionEntry o) {
        return new SemanticVersion(version).compareTo(new SemanticVersion(o.getVersion()));
    }
}
