package app.oways.luckytrip.hilt

import app.oways.luckytrip.database.doa.DestinationDAO
import app.oways.luckytrip.repository.local.ILocalDestinationOperations
import app.oways.luckytrip.repository.local.LocalDestinationRepository
import app.oways.luckytrip.repository.remote.DestinationRepository
import app.oways.luckytrip.repository.remote.DestinationServiceApi
import app.oways.luckytrip.repository.remote.IDestinationOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRemoteDestinationRepository(destinationServiceApi: DestinationServiceApi): IDestinationOperations {
        return DestinationRepository(destinationServiceApi)
    }

    @Provides
    fun provideLocalDestinationRepository(destinationDAO: DestinationDAO): ILocalDestinationOperations {
        return LocalDestinationRepository(destinationDAO)
    }
}