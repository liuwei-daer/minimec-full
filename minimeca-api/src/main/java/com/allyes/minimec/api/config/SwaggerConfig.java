package com.allyes.minimec.api.config;

import com.allyes.minimec.api.dto.CurrentUserInfo;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Configuration
@EnableSwagger2
@ComponentScan("com.allyes.minimec")
@ConfigurationProperties("swagger")
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enable;
    /**标题**/
    @Value("${swagger.title}")
    private String title;
    /**描述**/
    @Value("${swagger.description}")
    private String description;
    /**版本**/
    @Value("${swagger.version}")
    private String version;
    /**许可证**/
    @Value("${swagger.license}")
    private String license;
    /**许可证URL**/
    @Value("${swagger.license-url}")
    private String licenseUrl;
    @Value("${swagger.base-package}")
    private String basePackage;

    @Value("${swagger.base-path}")
    private List<String> basePath = new ArrayList<>();

    @Value("${swagger.exclude-path}")
    private List<String> excludePath = new ArrayList<>();

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(CurrentUserInfo.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(paths())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }

    private Predicate<String> paths() {
        if (basePath.isEmpty()) {
            basePath.add("/**");
        }
        List<Predicate<String>> basePathList = new ArrayList();
        for (String path : basePath) {
            basePathList.add(PathSelectors.ant(path));
        }

        // exclude-path处理
        List<Predicate<String>> excludePathList = new ArrayList();
        for (String path : excludePath) {
            excludePathList.add(PathSelectors.ant(path));
        }

        return Predicates.and(
                Predicates.not(Predicates.or(excludePathList)),
                Predicates.or(basePathList)
        );
    }
}
