package com.devopsexam.gameofthrones

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.codahale.metrics.MetricRegistry
import java.util.concurrent.TimeUnit
import com.codahale.metrics.MetricFilter
import com.codahale.metrics.graphite.GraphiteReporter
import java.net.InetSocketAddress
import com.codahale.metrics.graphite.Graphite
import org.springframework.context.annotation.Profile

@Configuration
class GameOfThronesApplicationConfig {

    @Bean
    fun getRegistry(): MetricRegistry {
        return MetricRegistry()
    }

    @Bean
    @Profile("!test")
    fun getReporter(registry: MetricRegistry): GraphiteReporter {
        val graphite = Graphite(InetSocketAddress(System.getenv("GRAPHITE_HOST"), 2003))
        val reporter = GraphiteReporter.forRegistry(registry)
                .prefixedWith(System.getenv("HOSTEDGRAPHITE_APIKEY"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite)
        reporter.start(1, TimeUnit.SECONDS)
        return reporter
    }
}