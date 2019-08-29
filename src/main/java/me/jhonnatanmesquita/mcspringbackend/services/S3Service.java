package me.jhonnatanmesquita.mcspringbackend.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import me.jhonnatanmesquita.mcspringbackend.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile){

        try {
        String fileName = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        String contentType = multipartFile.getContentType();
        return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentTyoe) {
        try{
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentTyoe);
            s3client.putObject(bucketName, fileName, inputStream, meta);
            return s3client.getUrl(bucketName, fileName).toURI();
        }catch (URISyntaxException e) {
            e.printStackTrace();
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}
