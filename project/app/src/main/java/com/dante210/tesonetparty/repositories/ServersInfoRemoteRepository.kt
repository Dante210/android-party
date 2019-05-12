package com.dante210.tesonetparty.repositories

import com.dante210.tesonetparty.api.TesonetApi
import org.koin.dsl.module

class ServersInfoRemoteRepository(val tesonetApi: TesonetApi) {
  companion object {
    val module = module {
      single { ServersInfoRemoteRepository(get()) }
    }
  }



}