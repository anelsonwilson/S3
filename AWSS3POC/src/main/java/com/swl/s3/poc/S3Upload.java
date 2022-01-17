package com.swl.s3.poc;

import java.io.File;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class S3Upload {
	
	private static String newBucketName;
	private static AmazonS3Client s3Client;

	public static void main(String[] args) throws Exception {

		System.out.println("boom");
		// demoServerSideEncryptionNotResource();

		try
		{
			createAndPopulateSimpleBucket();
		}
		catch (Exception ex)
		{
			// deleting the created bucket
			System.out.println("deleting the S3 bucket, transaction roolback.");
			BucketUtils.deleteBucket(newBucketName, s3Client);
		}
	}

	public static void createAndPopulateSimpleBucket() throws Exception {

		
		final BasicAWSCredentials awsCreds = new BasicAWSCredentials(Credentials.access_key_id,
				Credentials.secret_access_key);

		s3Client = new AmazonS3Client(awsCreds);

		// BucketUtils.deleteAllBuckets(s3Client);

		newBucketName = "swl_" + System.currentTimeMillis();

		s3Client.createBucket(newBucketName);
		
		
		 final String fileName = "/sometext.txt";
		//final String fileName = "English
		//final String fileName = "100MB.bin";

		final File file = new File(S3Upload.class.getResource(fileName).toURI());


		{
			PutObjectRequest putRequest1 = new PutObjectRequest(newBucketName,
					fileName + "." + System.currentTimeMillis(), file);
			PutObjectResult response1 = s3Client.putObject(putRequest1);
			System.out.println("Uploaded object encryption status is: " + response1.getSSEAlgorithm());
		}
		
		//creating an exception knowingly. Comment this if we want to save the file in the S3 bucket.
		//throw new Exception();

	}
}
