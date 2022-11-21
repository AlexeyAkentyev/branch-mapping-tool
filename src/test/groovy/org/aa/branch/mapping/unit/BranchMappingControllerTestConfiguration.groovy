package org.aa.branch.mapping.unit

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.testing.LocalDatastoreHelper
import org.aa.branch.mapping.BranchMappingController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BranchMappingControllerTestConfiguration {

    private Datastore datastore

    @Autowired
    private LocalDatastoreHelper localDatastoreHelper

    @Bean
    BranchMappingController getBookmarksController() {
        new BranchMappingController(datastore)
    }

    @Bean
    Datastore getDatastore() {
        datastore = localDatastoreHelper.getOptions().getService()

        datastore
    }

}
