package com.swl.s3.poc;

public class MainClass {
	public static void main(final String... str) throws Exception
	{
		S3UploadBAImpl s3UploadBAImpl = new S3UploadBAImpl();
		s3UploadBAImpl.createAndPopulateSimpleBucket();
	}
}
