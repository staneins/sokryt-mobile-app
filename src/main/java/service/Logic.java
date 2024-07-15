package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Poem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Logic {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File fileName = new File("/test.json");

    List<Poem> poemList = mapper.readValue(fileName, new TypeReference<List<Poem>>() {
    });


    public Logic() throws IOException {
    }

    public String textView(Integer id) {
        return poemList.stream()
                .filter(poem -> poem.getId().equals(id))
                .map(Poem::getText)
                .findFirst()
                .orElse("");
    }

public void getTitles(List<Poem> poemList) {
        String title;
        for(Poem poem : poemList) {
            title = poem.getTitle();
            System.out.println(title);
        }
}

}
