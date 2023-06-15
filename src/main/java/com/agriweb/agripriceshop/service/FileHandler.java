package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Item;
import com.agriweb.agripriceshop.domain.ItemPicture;
import com.agriweb.agripriceshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {

    @Autowired
    private final ItemRepository itemRepository;

    public FileHandler(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemPicture> parseFileInfo(
            Long itemId,
            List<MultipartFile> multipartFiles
    ) throws Exception {

        // 반환을 할 파일 리스트
        List<ItemPicture> fileList = new ArrayList<>();

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        // 파일 이름을 업로드한 날짜로 바꾸어서 저장
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대 경로를 설정 (Window의 Tomcat은 Temp 파일 이용)
        String absolutePath = new File("").getAbsolutePath() + "\\";

        // 경로를 지정하고 그곳에다가 저장
        String path = "images/" + current_date;
        File file = new File(path);
        // 저장할 위치의 디렉토리가 존재하지 않을 경우
        if(!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        for (MultipartFile multipartFile : multipartFiles) {
            // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다.
            if(!multipartFile.isEmpty()) {
                // jpeg, png, gif 파일들만 받아서 처리 예정
                String contentType = multipartFile.getContentType();
                String originalFileExtension;
                    // 확장자 명이 있어야한다.
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if(contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    }
                    // 다른 파일 명이면 아무 작업하지 않는다.
                    else {
                        break;
                    }
                }
                // 각 이름이 겹치면 안되므로 나노 초까지 동원하여 지정
                String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;

                // Item 불러오기
                Item targetItem = itemRepository.findOne(itemId);

                // 생성 후 리스트에 추가
                ItemPicture itemPicture = ItemPicture.builder()
                        .item(targetItem)
                        .original_file_name(multipartFile.getOriginalFilename())
                        .stored_file_path(path + "/" + new_file_name)
                        .file_size(multipartFile.getSize())
                        .build();
                fileList.add(itemPicture);

                // 저장된 파일로 변경하여 이를 보여주기 위함
                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);

            }

        }
        return fileList;




    }

}
