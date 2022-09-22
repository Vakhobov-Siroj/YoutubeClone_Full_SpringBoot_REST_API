package com.company.controller;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/init")
public class InitController {

    @RequestMapping("")
    public void test() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8"); // Add this
        messageSource.setBasenames("message"); // location/fileName
        System.out.println(messageSource.getMessage("email.exist", null, new Locale("en")));
        System.out.println(messageSource.getMessage("email.exist", null, new Locale("ru")));
        System.out.println(messageSource.getMessage("email.exist", null, new Locale("uz")));
    }
}
