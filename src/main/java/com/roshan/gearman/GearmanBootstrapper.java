package com.roshan.gearman;

import com.roshan.gearman.job.inputs.imagescaling.ImageScaleJobInput;
import com.roshan.gearman.job.submitters.imagescaling.ImageScalingJobSubmitter;
import org.slf4j.LoggerFactory;

/**
 *
 * This class is used to create and start the gearman workers. The workers are
 * created according to the properties specified in the gearman.properties file
 *
 * @author Roshan Alexander
 *
 */
public class GearmanBootstrapper extends AbstractBootstrapper {

	/**
	 * @throws Exception
	 */
	protected GearmanBootstrapper() throws Exception {
		super();
	}

	// TODO Find how to get logger instance
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GearmanBootstrapper.class);

	private static final String PROCESS_NAME = "Gearman Workers Bootstrapper Process";

	@Override
	protected String[] getApplicationContextConfigurationFiles() {
		return new String[] { "spring/applicationContext-service.xml", "spring/applicationContext-batch.xml" };
	}

	public static void main(String[] args) throws Exception {
		logger.info(PROCESS_NAME);
		GearmanBootstrapper gearmanBootstrapper = new GearmanBootstrapper();
		ImageScalingJobSubmitter imageScalingJobSubmitter = (ImageScalingJobSubmitter)GearmanBootstrapper
				.getApplicationContext().getBean("imageScalingJobSubmitter");
		ImageScaleJobInput imageScaleJobInput = new ImageScaleJobInput();
		imageScaleJobInput.setImageUrl("http://jkskjdfkj");
		while (true){
			imageScalingJobSubmitter.submitImageForScaling(imageScaleJobInput);
			//Thread.sleep(20);
		}

	}

}
