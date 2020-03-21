package com;

import com.spring.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com")
@Import(MyImportBeanDefinitionRegistrar.class)
public class AppConfig {
}
