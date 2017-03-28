package org.nzbhydra.web.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortModel {
    private String column;
    private int sortMode;

}
