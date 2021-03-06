package org.elassandra.action.admin.indices.snapshot;

import java.io.IOException;

import org.elasticsearch.action.support.replication.ReplicationRequest;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.index.shard.ShardId;

public class ShardSnapshotRequest extends ReplicationRequest<ShardSnapshotRequest> {

    private SnapshotRequest request = new SnapshotRequest();

    public ShardSnapshotRequest(SnapshotRequest request, ShardId shardId) {
        super(request, shardId);
        this.request = request;
    }

    public ShardSnapshotRequest() {
    }

    SnapshotRequest getRequest() {
        return request;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        request.readFrom(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        request.writeTo(out);
    }

    @Override
    public String toString() {
        return "snapshot {" + super.toString() + "}";
    }
}

