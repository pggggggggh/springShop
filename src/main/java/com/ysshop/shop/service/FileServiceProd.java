package com.ysshop.shop.service;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
@Log
public class FileServiceProd implements FileService { // AWS S3같은 데서 하는 파일업로드는 매우매우 어려울듯
    @Override
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        return null;
    }

    @Override
    public void deleteFile(String filePath) throws Exception {

    }
}
