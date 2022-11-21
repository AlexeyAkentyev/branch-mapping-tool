package org.aa.branch.mapping;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BranchMappingController {

    @Autowired
    private final Datastore datastore;

    public BranchMappingController(Datastore datastore) {
        this.datastore = datastore;
    }

    @GetMapping("branch-connections")
    public List<BranchConnection> getBranchConnections() {
        return getConnections();
    }

    @PostMapping("branch-connection")
    public ResponseEntity<String> addBranchConnection(@RequestBody BranchConnection branchConnection) {
        Objects.requireNonNull(branchConnection);
        var existConnection = getConnections();
        Optional<BranchConnection> defaultAppConnection = getDefaultBranchConnection(branchConnection, existConnection);
        var response = validateBranchConnection(branchConnection, defaultAppConnection, existConnection);

        if (response.getStatusCode() == HttpStatus.OK) {
            var keyFactory = datastore.newKeyFactory().setKind("BranchConnection");
            FullEntity.Builder<IncompleteKey> keyBuilder;

            if (branchConnection.isDefault() && defaultAppConnection.isPresent()) {
                branchConnection.setId(defaultAppConnection.get().getId());
                keyBuilder = FullEntity.newBuilder(keyFactory.newKey(defaultAppConnection.get().getId()));
            } else {
                keyBuilder = FullEntity.newBuilder(keyFactory.newKey());
            }

            FullEntity<IncompleteKey> messageEntity = keyBuilder
                    .set("type", branchConnection.getType())
                    .set("application", branchConnection.getApplication())
                    .set("application_branch", branchConnection.getApplicationBranch())
                    .set("connected_branch", branchConnection.getConnectedBranch())
                    .set("is_default", branchConnection.isDefault())
                    .set("comment", branchConnection.getComment())
                    .build();

            datastore.put(messageEntity);
        }

        return response;
    }

    private ResponseEntity<String> validateBranchConnection(BranchConnection branchConnection, Optional<BranchConnection> defaultAppConnection, List<BranchConnection> actualConnections) {
        var response = new ResponseEntity<String>(HttpStatus.OK);

        if (actualConnections.contains(branchConnection)) {
            response = new ResponseEntity<>("Object already exist", HttpStatus.ALREADY_REPORTED);
        }

        if (defaultAppConnection.isEmpty() && !branchConnection.isDefault()) {
            response = new ResponseEntity<>(String.format("There is no default branch for application %s with connection type %s",
                    branchConnection.getApplication(),
                    branchConnection.getType()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    private static Optional<BranchConnection> getDefaultBranchConnection(BranchConnection branchConnection, List<BranchConnection> actualConnections) {
        return actualConnections.stream()
                .filter(connection -> connection.getType().equals(branchConnection.getType())
                        && connection.getApplication().equals(branchConnection.getApplication()))
                .filter(BranchConnection::isDefault)
                .findFirst();
    }

    private List<BranchConnection> getConnections() {
        List<BranchConnection> branchConnections = new ArrayList<>();

        Query<Entity> query = Query.newEntityQueryBuilder().setKind("BranchConnection").build();
        QueryResults<Entity> results = datastore.run(query);

        while (results.hasNext()) {
            var branchConnection = new BranchConnectionBuilder().createBranchConnection();
            Entity entity = results.next();

            branchConnection.setId(entity.getKey().getId());
            branchConnection.setType(entity.getString("type"));
            branchConnection.setApplication(entity.getString("application"));
            branchConnection.setApplicationBranch(entity.getString("application_branch"));
            branchConnection.setConnectedBranch(entity.getString("connected_branch"));
            branchConnection.setDefault(entity.getBoolean("is_default"));
            branchConnection.setComment(entity.getString("comment"));

            branchConnections.add(branchConnection);
        }

        return branchConnections;
    }
}
