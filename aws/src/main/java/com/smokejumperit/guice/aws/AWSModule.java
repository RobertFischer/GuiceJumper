package com.smokejumperit.guice.aws;

import com.smokejumperit.guice.InstallingModule;
import com.smokejumperit.guice.aws.auth.AWSAuthModule;

/**
 * Installs all the AWS support modules.
 */
public class AWSModule extends InstallingModule {

	public AWSModule() {
		super(new AWSAuthModule());
	}

}
