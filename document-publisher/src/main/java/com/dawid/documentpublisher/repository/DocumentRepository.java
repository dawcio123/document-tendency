package com.dawid.documentpublisher.repository;

import com.dawid.documentpublisher.model.document.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Repository
public class DocumentRepository {

    public Document createDocument(){
        return Document.builder()
                .id(chooseUUID())
                .name("doc name")
                .body("doc body")
                .build();
    }



    private String chooseUUID(){
        List<String> uuidList = new ArrayList<>();
        uuidList.add("51103cda-e8dd-4750-a63b-d6a7cee82f9d");
        uuidList.add("df3fa2d4-1557-4afa-a5f1-891eb79ec0d7");
        uuidList.add("9f8643e4-9bce-43cf-b8a2-34b01e229b2d");
        uuidList.add("893c36b8-e7a3-4657-a29a-282d870b8768");
        uuidList.add("b39280b4-5eed-4bf1-9555-62b5f4e18489");

        Random rand = new Random();
        return uuidList.get(rand.nextInt(uuidList.size()));
    }
}
