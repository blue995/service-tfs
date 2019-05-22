/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-rally
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 
package com.epam.reportportal.extension.bugtracking.delegation;

import com.epam.reportportal.commons.template.FreemarkerTemplateEngine;
import com.epam.reportportal.commons.template.TemplateEngine;
import com.epam.reportportal.extension.bugtracking.BugTrackingApp;
import com.epam.reportportal.extension.bugtracking.ExternalSystemStrategy;
import com.epam.reportportal.extension.bugtracking.delegation.services.DelegationStrategy;
import com.google.common.base.Charsets;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Locale;

/**
 * Entry point for an abstract integration service
 *
 * @author Andrei Varabyeu
 */
@ComponentScan
public class DelegationServiceApp extends BugTrackingApp {

	@Autowired
	private DelegationStrategy myStrategy;

	@Override
	public ExternalSystemStrategy externalSystemStrategy() {
		return myStrategy;
	}

	@Bean
	public TemplateEngine getTemplateEngine() {
		Version version = new Version(2, 3, 25);
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(version);

		cfg.setClassForTemplateLoading(DelegationServiceApp.class, "/");

		cfg.setIncompatibleImprovements(version);
		cfg.setDefaultEncoding(Charsets.UTF_8.toString());
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return new FreemarkerTemplateEngine(cfg);
	}


	public static void main(String[] args) {
		SpringApplication.run(DelegationServiceApp.class, args);
	}
}