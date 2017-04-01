package org.nzbhydra.database;


import org.springframework.data.jpa.repository.JpaRepository;

public interface IndexerSearchRepository extends JpaRepository<IndexerSearchEntity, Integer>{

    IndexerSearchEntity findByIndexerEntityAndSearchEntity(IndexerEntity indexerEntity, SearchEntity searchEntity);
}