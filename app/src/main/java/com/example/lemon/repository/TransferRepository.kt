


package com.example.lemon.repository

import com.example.lemon.mappers.toDashboardDTO
import com.example.lemon.network.DTO.DashboardDTO
import com.example.lemon.network.DashboardService
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val dashboardService: DashboardService
) {

    suspend fun getDashboard(): DashboardDTO =
        dashboardService.getDashboard().toDashboardDTO()
}
