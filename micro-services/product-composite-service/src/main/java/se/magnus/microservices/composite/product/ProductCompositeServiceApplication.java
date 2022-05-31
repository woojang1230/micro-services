package se.magnus.microservices.composite.product;

import static java.util.Collections.emptyList;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.OAS_30;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import se.magnus.microservices.composite.product.services.ProductCompositeIntegration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("se.magnus")
@EnableSwagger2
public class ProductCompositeServiceApplication {
    @Value("${api.common.version}")
    private String apiVersion;
    @Value("${api.common.title}")
    private String apiTitle;
    @Value("${api.common.description}")
    private String apiDescription;
    @Value("${api.common.termsOfServiceUrl}")
    private String apiTermsOfServiceUrl;
    @Value("${api.common.license}")
    private String apiLicense;
    @Value("${api.common.licenseUrl}")
    private String apiLicenseUrl;
    @Value("${api.common.contact.name}")
    private String apiContactName;
    @Value("${api.common.contact.url}")
    private String apiContactUrl;
    @Value("${api.common.contact.email}")
    private String apiContactEmail;


    public static void main(String[] args) {
        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }

    @Bean
    public Docket apiDocumentation() {
        // Swagger 3.0 사용 시 DocumentationType.OAS_30 세팅
        return new Docket(OAS_30)
                // API 선택을 위한 빌더 시작
                .select()
                // basePackage와 일치하는 모든 request handler를 포함. 현재 api 모듈의 se.magnus.api.composite.product.ProductCompositeRestController가 @Api 어노테이션으로 선언되어 ㅣㅆ다.
                .apis(basePackage("se.magnus.microservices.composite.product"))
                // 모든 경로를 포함하도록 한다. Path가 URL을 의미하는지는 정확하게 확인이 어려운 상황.
                .paths(PathSelectors.any())
                // select() 이후 설정들로 Docket을 생성
                .build()
                // 기본 응답 메시지를 사용할 것인지 선택. 기본값은 true
                .useDefaultResponseMessages(false)
                // http 요청 수준에서 기본 응답 메시지를 재정의. useDefaultResponseMessages(true)일 때 함께 정의해서 사용. API 별 응답을 정의하려면 Controller에 어노테이션을 사용하여 정의.
                // .globalResponses(HttpMethod.GET, emptyList())
                // api의 메타정보 설정
                .apiInfo(new ApiInfo(apiTitle, apiDescription, apiVersion, apiTermsOfServiceUrl,
                        new Contact(apiContactName, apiContactUrl, apiContactEmail), apiLicense, apiLicenseUrl,
                        emptyList()));
    }

//    @Autowired
//    HealthAggregator healthAggregator;
//
//    @Autowired
//    ProductCompositeIntegration integration;
//
//    @Bean
//    ReactiveHealthIndicator coreServices() {
//
//        ReactiveHealthIndicatorRegistry registry = new DefaultReactiveHealthIndicatorRegistry(new LinkedHashMap<>());
//
//        registry.register("product", () -> integration.getProductHealth());
//        registry.register("recommendation", () -> integration.getRecommendationHealth());
//        registry.register("review", () -> integration.getReviewHealth());
//
//        return new CompositeReactiveHealthIndicator(healthAggregator, registry);
//    }
}
