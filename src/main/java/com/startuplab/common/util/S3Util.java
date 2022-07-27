package com.startuplab.common.util;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3Util {
    AmazonS3 s3;
    final String endPoint = "https://kr.object.ncloudstorage.com";
    final String regionName = "kr-standard";
    String accessKey = "6IdSAp6uDDGub7X7Bnnu";
    String secretKey = "BnZ4e9JDTLm2PrlbipT4psSLD9jGkiBazG4cjpBz";
    String bucketName = "startuplab";

    public S3Util() {
        this.initS3(this.accessKey, this.secretKey);
    }

    private void initS3(String accessKey, String secretKey) {
        try {
            if (s3 == null) {
                s3 = AmazonS3ClientBuilder.standard() //
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName)) //
                        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))) //
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadFile(File file, String objectPath) {
        /* 상대위치 
        ** 맨앞에 /가 없어야 함. */
        String url = "";
        try {
            if (objectPath.substring(0, 1).equals("/")) {
                objectPath = objectPath.substring(1);
            }
            String contentType = CUtil.getFileMineType(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.length());
            objectMetadata.setContentType(contentType);
            FileInputStream inputStream = new FileInputStream(file);
            PutObjectRequest v = new PutObjectRequest(this.bucketName, objectPath, inputStream, objectMetadata);
            v.setCannedAcl(CannedAccessControlList.PublicRead);
            this.s3.putObject(v);
            url = this.endPoint + "/" + this.bucketName + "/" + objectPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    };

    public String uploadFile(MultipartFile file, String objectPath) {
        /* 상대위치 
        ** 맨앞에 /가 없어야 함. */
        String url = "";
        try {
            if (objectPath.substring(0, 1).equals("/")) {
                objectPath = objectPath.substring(1);
            }
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            PutObjectRequest v = new PutObjectRequest(this.bucketName, objectPath, file.getInputStream(), objectMetadata);
            v.setCannedAcl(CannedAccessControlList.PublicRead);
            this.s3.putObject(v);
            url = this.endPoint + "/" + this.bucketName + "/" + objectPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    };

    public void deleteFile(String url) {
        /* 상대위치 
        ** 맨앞에 /가 없어야 함. */
        try {
            int begin = url.indexOf(this.bucketName);
            if (begin < 0) {
                log.warn("bucketName이 없습니다. bucketName:{}, url:{}", this.bucketName, url);
                return;
            }
            begin = begin + this.bucketName.length() + 1;
            String key = url.substring(begin);
            log.info("url: {}, key:{}", url, key);
            this.s3.deleteObject(this.bucketName, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
