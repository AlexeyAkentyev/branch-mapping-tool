package org.aa.branch.mapping.unit

import com.google.cloud.datastore.testing.LocalDatastoreHelper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BranchMappingDataStoreTestConfiguration {

    @SuppressWarnings('NoDouble')
    public static final double DATASTORE_TEST_CONSISTENCY = 1.0

    private LocalDatastoreHelper localDatastoreHelper

    @Bean
    LocalDatastoreHelper getLocalDatastoreHelper() {
        LocalDatastoreHelper localDatastoreHelper = LocalDatastoreHelper.create(DATASTORE_TEST_CONSISTENCY)
        localDatastoreHelper.start()

        this.localDatastoreHelper = localDatastoreHelper

        localDatastoreHelper
    }

}
