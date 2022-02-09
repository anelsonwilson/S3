package com.swl.s3.poc;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
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
		//This exception represents an error response from an AWS service.
		catch (AmazonServiceException amazonServiceException)
		{
			// deleting the created bucket, if any
			System.out.println("deleting the S3 bucket - if any, transaction roolback.");
			BucketUtils.deleteBucket(newBucketName, s3Client);
			amazonServiceException.printStackTrace();
		}
		//This exception is handled, if a caller tries to use a client to make a service call, but no network connection is present, an AmazonClientException will be 
		//thrown to indicate that the client wasn't able to successfully make the service call, and no information from the service is available.
		catch (AmazonClientException amazonClientException)
		{
			// deleting the created bucket, if any
			System.out.println("No N/w present, unable to make call to AWS servie");
			BucketUtils.deleteBucket(newBucketName, s3Client);
			amazonClientException.printStackTrace();
		}
		//Naming standards of the Bucket name etc.
		catch (IllegalArgumentException argumentException)
		{
			argumentException.printStackTrace();
		}
		catch (Exception ex)
		{
			// deleting the created bucket
			System.out.println("deleting the S3 bucket, transaction roolback.");
			BucketUtils.deleteBucket(newBucketName, s3Client);
			ex.printStackTrace();
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
