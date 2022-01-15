package com.swl.s3.poc;

import java.io.File;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.BucketNameUtils;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class S3Upload {
	
	private static String newBucketName;
	private static AmazonS3Client s3Client;

	public static void main(String[] args) throws Exception {

		System.out.print("boom");
		// demoServerSideEncryptionNotResource();

		try
		{
			createAndPopulateSimpleBucket();
		}
		catch (Exception ex)
		{
			// deleting the created bbucket
			BucketUtils.deleteBucket(newBucketName, s3Client);
		}
	}

	public static void createAndPopulateSimpleBucket() throws Exception {

		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(Credentials.access_key_id,
				Credentials.secret_access_key);

		s3Client = new AmazonS3Client(awsCreds);

		// BucketUtils.deleteAllBuckets(s3Client);

		newBucketName = "nelson" + System.currentTimeMillis();

		s3Client.createBucket(newBucketName);

		 final String fileName = "/sometext.txt";
		// final String fileName = "English
		// Vinglish.2012.1080p.WEB-DL.AVC.AAC.ESub.DDR.mkv";
		//final String fileName = "100MB.bin";

		File file = new File(S3Upload.class.getResource(fileName).toURI());


		{
			PutObjectRequest putRequest1 = new PutObjectRequest(newBucketName,
					fileName + "." + System.currentTimeMillis(), file);
			PutObjectResult response1 = s3Client.putObject(putRequest1);
			System.out.println("Uploaded object encryption status is " + response1.getSSEAlgorithm());
		}
		
		//creating an exception knowingly.
		throw new Exception();

	}
}
