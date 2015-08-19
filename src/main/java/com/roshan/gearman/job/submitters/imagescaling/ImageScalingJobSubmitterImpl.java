package com.roshan.gearman.job.submitters.imagescaling;

import org.apache.logging.log4j.Logger;

import com.roshan.gearman.job.inputs.imagescaling.ImageScaleJobInput;
import com.roshan.gearman.job.jobresult.DefaultGearmanJobResult;
import com.roshan.gearman.job.submitters.AbstractJobSubmitter;
import com.roshan.gearman.utils.GearmanUtils;
import org.gearman.GearmanJobEvent;
import org.gearman.GearmanJobReturn;
import org.slf4j.LoggerFactory;

/**
 * @author Roshan Alexander
 *
 */
public class ImageScalingJobSubmitterImpl extends AbstractJobSubmitter<ImageScaleJobInput, DefaultGearmanJobResult>
implements ImageScalingJobSubmitter {

	public ImageScalingJobSubmitterImpl() {
		super(DefaultGearmanJobResult.class);
	}

	// TODO Get logger instance
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImageScalingJobSubmitterImpl.class);

	@Override
	public void submitImageForScaling(ImageScaleJobInput imageScaleJobInput) {

		try {
			byte[] data = GearmanUtils.getBytesFromObject(imageScaleJobInput);

			if (isGearmanEnabled()) {
				logger.debug("Submitting backgroung job for image scaling. Url : " + imageScaleJobInput.getImageUrl());
				GearmanJobReturn jobReturn = gearmanClient.submitJob(functionName, data);
				while (!jobReturn.isEOF()) {

					// Poll the next job event (blocking operation)
					GearmanJobEvent event = jobReturn.poll();

					switch (event.getEventType()) {

						// success
						case GEARMAN_JOB_SUCCESS: // Job completed successfully
							// print the result
							System.out.println(new String(event.getData()));
							break;

						// failure
						case GEARMAN_SUBMIT_FAIL: // The job submit operation failed
						case GEARMAN_JOB_FAIL: // The job's execution failed
							System.err.println(event.getEventType() + ": "
									+ new String(event.getData()));
						default:
					}

				}
			} else {
				logger.debug("Submitting local job for image scaling. Url : " + imageScaleJobInput.getImageUrl());
				submitLocalJob(functionName, imageScaleJobInput, null);
			}
		} catch (Exception e) {
			logger.error("Error submitting the job for downscaling : Url : " + imageScaleJobInput.getImageUrl(), e);
			setStatusSubmitFailed(imageScaleJobInput.getImageUrl(), e);
		}
	}

	/**
	 * @param imageUrl
	 * @param e
	 */
	private void setStatusSubmitFailed(String imageUrl, Exception e) {
		// TODO Set the submit status as failed either in DB or log the
		// exception
	}

}
