/*
 *  (C) Copyright 2017 TheOtherP (theotherp@gmx.de)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.nzbhydra.searching;

import org.nzbhydra.indexers.IndexerStatusEntity;
import org.nzbhydra.indexers.IndexerStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CleanupIndexerStatusesTask {

    private static final Logger logger = LoggerFactory.getLogger(CleanupIndexerStatusesTask.class);

    private static final long HOUR = 1000 * 60 * 60 ;

    @Autowired
    private IndexerStatusRepository repository;


    @Scheduled(initialDelay = HOUR, fixedRate = HOUR)
    public void cleanup() {
        logger.debug("Running task to clean up indexer statuses");
        for (IndexerStatusEntity entity : repository.findAll()) {
            if (!entity.getDisabledPermanently() && entity.getDisabledUntil() != null && entity.getDisabledUntil().isBefore(Instant.now())) {
                entity.setDisabledUntil(null);
                repository.save(entity);
            }
        }
    }
}
