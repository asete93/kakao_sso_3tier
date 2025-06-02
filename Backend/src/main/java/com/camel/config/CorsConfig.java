// package com.camel.config;
// import java.util.Arrays;
// import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;

// @Configuration

// public class CorsConfig {
//     private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

//     @Bean
//     @Profile("local")
//     public CorsFilter localCorsFilter() {
//         logger.info("Configuring CORS for local environment");
//         return createCorsFilter(List.of(""));
//     }

//     @Bean
//     @Profile("prod")
//     public CorsFilter prodCorsFilter() {
//         logger.info("Configuring CORS for production environment");
//         return createCorsFilter(List.of(""));
//     }

//     private CorsFilter createCorsFilter(List<String> allowedOrigins) {
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         CorsConfiguration config = new CorsConfiguration();

//         config.setAllowedOrigins(allowedOrigins);
//         config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//         config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//         config.setAllowCredentials(true);
//         config.setMaxAge(3600L);

//         source.registerCorsConfiguration("/**", config);

//         logger.info("CORS Configuration:");
//         logger.info("Allowed Origins: {}", allowedOrigins);
//         logger.info("Allowed Methods: {}", config.getAllowedMethods());
//         logger.info("Allowed Headers: {}", config.getAllowedHeaders());
//         logger.info("Exposed Headers: {}", config.getExposedHeaders());
//         logger.info("Allow Credentials: {}", config.getAllowCredentials());
//         logger.info("Max Age: {}", config.getMaxAge());

//         return new CorsFilter(source);
//     }
// }