package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team1897.robot.commands.ExampleCommand;

//import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * This class will process the the images coming in from the camera, send the 
 * images through the GRIP filter pipeline and finally send the processed
 * images to an MJPEGSTREAM in order to view the output on the smart
 * dashboard.
 */

public class FilteredCamera extends Subsystem {

	public void initDefaultCommand() {
		// Set the default command for the subsystem here.
		// setDefaultCommand(new ExampleCommand());
	}

	Thread visionThread;

	Mat mask;
	Mat hsvImage;
	Mat frame;
	Mat morphOutput;

	Relay ledRing;

	int minHue = 30;
	int minSat = 0;
	int minVal = 230;
	int maxHue = 130;
	int maxSat = 255;
	int maxVal = 255;

	boolean readyToPutVideo;

	static double centerX;
	static double expectedCenterX;

	double y1, y2, y3;

	static double lengthBetweenTargets;
	static double angleToTarget;
	static double horizontalOffset;
	static double measuredWidth;
	static double pixelsPerInch;
	double exposure;
	public static final double WIDTH_BETWEEN_TARGET = 8;
	public static final double WIDTH_OF_TAPE = 2; // INCHES

	public FilteredCamera() {
		ledRing = new Relay(0);

		startGearVisionThread();
	}

	// This method is the true "constructor" of the camera. All we really want
	// is to have the robot
	// spawn an individual thread to do the vision processing. The Pipeline was
	// all setup through
	// GRIP.
	//
	// Grab the filtered image from the grip pipeline, overlay a rectangle onto
	// the Mat object and
	// write the image to the output stream that should be available for display
	// on the smart dashboard.
	private void startGearVisionThread() {
		//
		// SmartDashboard.getNumber("minHue: ", 0.0);
		// SmartDashboard.getNumber("maxHue: ", 255.0);
		// SmartDashboard.getNumber("minSat: ", 0.0);
		// SmartDashboard.getNumber("maxSat: ", 255.0);
		// SmartDashboard.getNumber("minVal: ", 0.0);
		// SmartDashboard.tNumber("maxVal: ", 255.0);
		//
		Mat mat = new Mat(); // define mat in order to reuse it

		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			// Set the resolution
			camera.setResolution(640, 480);
			camera.setExposureManual(25);

			mask = new Mat();
			hsvImage = new Mat();
			frame = new Mat();
			morphOutput = new Mat();

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {

				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat. If there is an error notify the output.
				if (cvSink.grabFrame(frame) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}

				////////////////////////////////////////////////////////////////////////////////
				Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_BGR2HSV);

				// Scalar minValues = new Scalar(SmartDashboard.getNumber("min
				// hue", 0), SmartDashboard.getNumber("min sat", 0),
				// SmartDashboard.getNumber("min val", 0));
				// Scalar maxValues = new Scalar(SmartDashboard.getNumber("max
				// hue", 255), SmartDashboard.getNumber("max sat", 255),
				// SmartDashboard.getNumber("max val", 255));

				// minHue = (int) SmartDashboard.getNumber("min hue", 0);
				// minSat = (int) SmartDashboard.getNumber("min sat", 0);
				// minVal = (int) SmartDashboard.getNumber("min val", 0);
				// maxHue = (int) SmartDashboard.getNumber("max hue", 255);
				// maxSat = (int) SmartDashboard.getNumber("max sat", 255);
				// maxVal = (int) SmartDashboard.getNumber("max val", 255);

				Scalar minValues = new Scalar(minHue, minSat, minVal);
				Scalar maxValues = new Scalar(maxHue, maxSat, maxVal);

				// // Ethan's gets
				// SmartDashboard.putNumber("max hue", maxHue);
				// SmartDashboard.putNumber("max sat", maxSat);
				// SmartDashboard.putNumber("max val", maxVal);
				// SmartDashboard.putNumber("min hue", minHue);
				// SmartDashboard.putNumber("min sat", minSat);
				// SmartDashboard.putNumber("min val", minVal);

				// Scalar minValues = new Scalar(minHue, minSat, minVal);
				// Scalar maxValues = new Scalar(maxHue, maxSat, maxVal);
				Core.inRange(hsvImage, minValues, maxValues, mask);

				Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
				Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
				Imgproc.erode(mask, morphOutput, erodeElement);
				Imgproc.dilate(morphOutput, mask, dilateElement);

				ArrayList<MatOfPoint> contours = new ArrayList<>();
				Mat hierarchy = new Mat();
				Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

				if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
					for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
						Imgproc.drawContours(mask, contours, idx, new Scalar(250, 0, 0));
					}
				}

				int xMax = 9876587;

				for (int i = 0; i < contours.size(); i++) {
					try {
						Rect r1 = Imgproc.boundingRect(contours.get(i));
						int rectCenter = (r1.x + r1.width / 2);
						if (rectCenter < xMax) {
							xMax = rectCenter;
						}
					} catch (Exception e) {
					}
				}

				 centerX = xMax;

				// if (contours.size() < 2) {
				// centerX *= 2;
				// } else {
				//
				// try {
				// Rect r2 = Imgproc.boundingRect(contours.get(1));
				// centerX += (r2.x + r2.width / 2);
				// y2 = r2.y;
				// } catch (Exception e) {
				// }
				// }

				//////////////////////////////////////////////

				if (readyToPutVideo) {
					outputStream.putFrame(mask);
				}
			}
		});
		visionThread.setDaemon(true);
		//
	}

	public void startVisionThread() {
		visionThread.start();
	}

	public void stopVisionThread() {
		visionThread.suspend();
	}

	public void resumeVisionThread() {
		visionThread.resume();
	}

	public double getAvgCenterX() {
		return centerX;
	}

	public void putVideo(boolean bool) {
		readyToPutVideo = bool;
	}

	public void startLights() {
		ledRing.set(Relay.Value.kReverse);
	}

	public void stopLights() {
		ledRing.set(Relay.Value.kForward);
	}
	
	public void setThresholdVals(int minHue, int maxHue, int minSat, int maxSat, int minVal, int maxVal) {
		this.minHue = minHue;
		this.maxHue = maxHue;
		this.minSat = minSat;
		this.maxSat = maxSat;
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
}