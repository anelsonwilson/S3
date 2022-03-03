package com.swl.s3.poc;

import java.io.File;
import java.lang.reflect.Method;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class S3UploadBAImpl implements S3UploadBA
{
	
	private static String newBucketName;
	private static AmazonS3Client s3Client;

	
	public void createAndPopulateSimpleBucket() throws Exception 
	{
		final BasicAWSCredentials awsCreds = new BasicAWSCredentials(Credentials.access_key_id,
				Credentials.secret_access_key);

		s3Client = new AmazonS3Client(awsCreds);

		newBucketName = "swl" + System.currentTimeMillis();

		s3Client.createBucket(newBucketName);
		
		final String fileName = "/sometext.txt";

		final File file = new File(S3Upload.class.getResource(fileName).toURI());


		{
			PutObjectRequest putRequest1 = new PutObjectRequest(newBucketName,
					fileName + "." + System.currentTimeMillis(), file);
			PutObjectResult response1 = s3Client.putObject(putRequest1);
			System.out.println("Uploaded object encryption status is: " + response1.getSSEAlgorithm());
		}
		
		//creating an exception knowingly. Comment this if we want to save the file in the S3 bucket.
		throw new Exception();

	}


	public void before(Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		
	}
}
