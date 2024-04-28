package io.github.bootystar.helper.minio.client;

import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MinioEnhancedClient{

    protected String defaultBucket;

    protected final Logger log ;

    protected MinioClient minioClient;

    public MinioEnhancedClient(MinioClient minioClient) {
        this.minioClient = minioClient;
        log = LoggerFactory.getLogger(getClass());
    }
    public MinioEnhancedClient(MinioClient minioClient,String defaultBucket) {
        this.minioClient = minioClient;
        log = LoggerFactory.getLogger(getClass());
        this.defaultBucket = defaultBucket;
    }

    public MinioClient getMinioClient() {
        return minioClient;
    }

    public String getDefaultBucket() {
        return defaultBucket;
    }

    public void setDefaultBucket(String defaultBucket) {
        this.defaultBucket = defaultBucket;
    }

    public ObjectWriteResponse upload(String bucketName, String filename, InputStream is) {
        try {
            ObjectWriteResponse put = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            // The field file exceeds its maximum permitted size of 1048576 bytes
                            .stream(is, -1, 10485760)
                            //                        .stream(is, is.available(), -1)
                            .build()
            );
            return put;
        }  catch (Exception e) {
            log.error("upload => file error" , e);
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("upload => close  inputStream failed" , e);
            }
        }
        return null;
    }
    public ObjectWriteResponse upload(String filename, InputStream is) {
        if (defaultBucket==null) throw new IllegalStateException("defaultBucket is null, please set defaultBucket");
        return upload(defaultBucket, filename, is);
    }


    public boolean fileExists(String bucketName, String filename){
        StatObjectResponse statObjectResponse = null;
        try {
            statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(filename).build());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        System.out.println(statObjectResponse);
        return statObjectResponse.size() > 0;
    }

    public boolean fileExists(String filename){
        if (defaultBucket==null) throw new IllegalStateException("defaultBucket is null, please set defaultBucket");
        return fileExists(defaultBucket, filename);
    }


    public void download(String bucketName, String filename, OutputStream os){
        try (InputStream is =
                     minioClient.getObject(
                             GetObjectArgs.builder()
                                     .bucket(bucketName)
                                     .object(filename)
                                     .build());){
            System.out.println(is.available());
            byte[] buf = new byte[16384];
            int bytesRead;
            while ((bytesRead = is.read(buf, 0, buf.length)) >= 0) {
                os.write(buf, 0, bytesRead);
            }
            os.flush();
        }catch (Exception e){
            log.error("download => close  inputStream failed" , e);
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("download => close  outputStream failed" , e);
                }
            }
        }
    }

    public void download(String filename, OutputStream os){
        if (defaultBucket==null) throw new IllegalStateException("defaultBucket is null, please set defaultBucket");
        download(defaultBucket, filename, os);
    }









}
