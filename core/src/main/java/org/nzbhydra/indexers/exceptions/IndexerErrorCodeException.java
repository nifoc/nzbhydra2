package org.nzbhydra.indexers.exceptions;


import org.nzbhydra.mapping.newznab.RssError;

/**
 * Thrown when the indexer returns an error code that is not handled specifically (e.g. not an auth problem)
 */
public class IndexerErrorCodeException extends IndexerAccessException {
    public IndexerErrorCodeException(RssError response) {
        super(String.format("Indexer returned with error code %s and description %s", response.getCode(), response.getDescription()));
    }
}
