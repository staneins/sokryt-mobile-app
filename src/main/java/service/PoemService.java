package service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entity.Poem;
import repository.PoemRepo;
import java.util.List;

public class PoemService {

    private final PoemRepo poemRepo;

    public PoemService(PoemRepo poemRepo) {
        this.poemRepo = poemRepo;
    }

    public List<Poem> getAllPoems() {
        return poemRepo.getAll();
    }

    public String getTitle(Long id) {
        Poem poemById = poemRepo.getById(id);
        String poemTitle = poemById.getTitle();
        return poemTitle;
    }

    public String getText(Long id) {
        Poem poemById = poemRepo.getById(id);
        String poemText = poemById.getText();
        return poemText;
    }
//    private final ObjectMapper mapper = new ObjectMapper();
//    private final File fileName = new File("/test.json");
//
//    List<Poem> poemList = mapper.readValue(fileName, new TypeReference<List<Poem>>() {
//    });
//
//
//    public PoemService() throws IOException {
//    }
//
//    public String textView(Integer id) {
//        return poemList.stream()
//                .filter(poem -> poem.getId().equals(id))
//                .map(Poem::getText)
//                .findFirst()
//                .orElse("");
//    }
//
//public void getTitles(List<Poem> poemList) {
//        String title;
//        for(Poem poem : poemList) {
//            title = poem.getTitle();
//            System.out.println(title);
//        }
//}

}
