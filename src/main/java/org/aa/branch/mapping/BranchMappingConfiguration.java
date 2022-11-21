package org.aa.branch.mapping;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BranchMappingConfiguration {

    @Bean
    public Datastore getDatastore() {
        return DatastoreOptions.getDefaultInstance().getService();
    }

}
