package com.roshan.gearman.function.imagescaling;

import com.roshan.gearman.utils.GearmanUtils;
import org.apache.logging.log4j.Logger;
import org.gearman.GearmanFunctionCallback;

import com.roshan.gearman.function.AbstractGearmanFunction;
import com.roshan.gearman.job.inputs.imagescaling.ImageScaleJobInput;
import com.roshan.gearman.job.jobresult.DefaultGearmanJobResult;

/**
 * @author Roshan Alexander
 *
 */
public class ImageScalingFunction extends AbstractGearmanFunction<ImageScaleJobInput, DefaultGearmanJobResult> {

	// TODO Get logger instance
	//public static final Logger logger = null;

	@Override
	public DefaultGearmanJobResult doWork(String function, ImageScaleJobInput imageScaleJobInput,
			GearmanFunctionCallback callback) {
		// TODO Actual logic for scaling the image
		// TODO Remove the sleep(). Included here to mimic the image scaling
		// operation
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		byte[] data = null;
		imageScaleJobInput.setImageUrl("http://twentwo.cn");
		try {
			data = GearmanUtils.getBytesFromObject(imageScaleJobInput);
		} catch (Exception e) {
			logger.error("Error serializing the job input object for gearman function : " + function, e);
			e.printStackTrace();
		}
		return new DefaultGearmanJobResult(data);
	}

	@Override
	public Class<ImageScaleJobInput> getInputClassType() {
		return ImageScaleJobInput.class;
	}

}
