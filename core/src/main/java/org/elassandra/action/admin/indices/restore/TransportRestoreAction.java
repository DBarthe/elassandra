/*
 * Copyright (c) 2017 Strapdata (http://www.strapdata.com)
 * Contains some code from Elasticsearch (http://www.elastic.co)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elassandra.action.admin.indices.restore;

import java.util.List;

import org.elasticsearch.action.ActionWriteResponse;
import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.replication.TransportBroadcastReplicationAction;
import org.elasticsearch.cluster.ClusterService;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

/**
 * Restore action.
 */
public class TransportRestoreAction extends TransportBroadcastReplicationAction<RestoreRequest, RestoreResponse, ShardRestoreRequest, ActionWriteResponse> {

    @Inject
    public TransportRestoreAction(Settings settings, ThreadPool threadPool, ClusterService clusterService,
                                  TransportService transportService, ActionFilters actionFilters,
                                  IndexNameExpressionResolver indexNameExpressionResolver,
                                  TransportShardRestoreAction shardRefreshAction) {
        super(RestoreAction.NAME, RestoreRequest.class, settings, threadPool, clusterService, transportService, actionFilters, indexNameExpressionResolver, shardRefreshAction);
    }

    @Override
    protected ActionWriteResponse newShardResponse() {
        return new ActionWriteResponse();
    }

    @Override
    protected ShardRestoreRequest newShardRequest(RestoreRequest request, ShardId shardId) {
        return new ShardRestoreRequest(request, shardId);
    }

    @Override
    protected RestoreResponse newResponse(int successfulShards, int failedShards, int totalNumCopies, List<ShardOperationFailedException> shardFailures) {
        return new RestoreResponse(totalNumCopies, successfulShards, failedShards, shardFailures);
    }
}
