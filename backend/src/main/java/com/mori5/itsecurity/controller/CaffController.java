package com.mori5.itsecurity.controller;

import com.mori5.itsecurity.cpp.CPPParserCaller;
import com.mori5.itsecurity.cpp.CreatorsImages;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class CaffController {

    @GetMapping("/hello")
    public String dummy(@RequestParam String message) {
        return "Hello " + message;
    }

    @PostMapping("/hello")
    public ResponseEntity<Resource> dummy(@RequestParam("file") MultipartFile file) throws IOException {

        CPPParserCaller caller = new CPPParserCaller();

        CreatorsImages creatorsImages = caller.parse(file.getBytes());

        BufferedImage bufferedImage = new BufferedImage((int)creatorsImages.images.width,(int)creatorsImages.images.height,BufferedImage.TYPE_INT_RGB);

        int counter = 0;
        for(int i = 0; i < (int) creatorsImages.images.height; i++) {
            for(int j = 0; j < (int) creatorsImages.images.width; j++) {
                int r = creatorsImages.images.pixels.get(counter).red;
                int g = creatorsImages.images.pixels.get(counter).green;
                int b = creatorsImages.images.pixels.get(counter++).blue;
                int rgb = r;
                rgb = (rgb << 8) + g;
                rgb = (rgb << 8) + b;
                bufferedImage.setRGB(j, i, rgb);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bufferedImage, "bmp", baos );

        baos.flush();
        byte[] imageInByte = baos.toByteArray();


        ByteArrayResource resource = new ByteArrayResource(imageInByte);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fuilasd");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(imageInByte.length)
                .contentType(MediaType.parseMediaType("image/bmp")) // TODO ilyen nincs is, hogy bmp szerintem
                .body(resource);

        //return ResponseEntity.ok(creatorsImages.images.width);

    }

}
