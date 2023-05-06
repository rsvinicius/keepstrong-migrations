package com.keepstrong.migration.config

import org.flywaydb.core.Flyway
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class BatchConfiguration(
    private val dataSource: DataSource
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway = Flyway.configure().dataSource(dataSource).load()

    @Bean
    fun job(jobRepository: JobRepository, step: Step): Job {
        return JobBuilder("job", jobRepository)
            .start(step)
            .build()
    }

    @Bean
    fun step(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step {
        return StepBuilder("step", jobRepository)
            .tasklet({ _, _ ->
                println("Running Flyway migration...")
                RepeatStatus.FINISHED
            }, transactionManager)
            .build()
    }
}