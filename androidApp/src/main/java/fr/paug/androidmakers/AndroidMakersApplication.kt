package fr.paug.androidmakers

import android.app.Application
import android.os.Build
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.repo.PartnersRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.repo.VenueRepository
import fr.androidmakers.store.graphql.ApolloClientBuilder
import fr.androidmakers.store.graphql.PartnersGraphQLRepository
import fr.androidmakers.store.graphql.RoomsGraphQLRepository
import fr.androidmakers.store.graphql.SessionsGraphQLRepository
import fr.androidmakers.store.graphql.SpeakersGraphQLRepository
import fr.androidmakers.store.graphql.VenueGraphQLRepository
import fr.paug.androidmakers.util.BookmarksStore
import io.openfeedback.android.OpenFeedback



class AndroidMakersApplication : Application() {
  lateinit var getAgendaUseCase: GetAgendaUseCase
  lateinit var partnersRepository: PartnersRepository
  lateinit var roomsRepository: RoomsRepository
  lateinit var sessionsRepository: SessionsRepository
  lateinit var speakersRepository: SpeakersRepository
  lateinit var venueRepository: VenueRepository

  lateinit var openFeedback: OpenFeedback

  override fun onCreate() {
    instance_ = this

    val apolloClient = ApolloClientBuilder(
        context = this,
        url = if (false && Build.PRODUCT == "sdk_gphone64_arm64") {
          "http://10.0.2.2:8080/graphql"
        } else {
          "https://androidmakers-2023.ew.r.appspot.com/graphql"
        },
        conference = "androidmakers2023").build()
    partnersRepository = PartnersGraphQLRepository(apolloClient)
    roomsRepository = RoomsGraphQLRepository(apolloClient)
    sessionsRepository = SessionsGraphQLRepository(apolloClient)
    speakersRepository = SpeakersGraphQLRepository(apolloClient)
    venueRepository = VenueGraphQLRepository(apolloClient)
    getAgendaUseCase = GetAgendaUseCase(
        sessionsRepository = sessionsRepository,
        roomsRepository = roomsRepository,
        speakersRepository = speakersRepository
    )

    super.onCreate()
    BookmarksStore.init(this)

    openFeedback = OpenFeedback(
        context = this,
        openFeedbackProjectId = "am2023",
        firebaseConfig = OpenFeedback.FirebaseConfig(
            projectId = "open-feedback-42",
            applicationId = "1:635903227116:web:31de912f8bf29befb1e1c9",
            apiKey = "AIzaSyB3ELJsaiItrln0uDGSuuHE1CfOJO67Hb4",
            databaseUrl = "https://open-feedback-42.firebaseio.com"
        )
    )
  }

  companion object {
    private var instance_: AndroidMakersApplication? = null
    fun instance(): AndroidMakersApplication {
      return instance_!!
    }
  }
}
