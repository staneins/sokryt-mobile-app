package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "node__body")
@SecondaryTable(name = "node_field_data", pkJoinColumns = @PrimaryKeyJoinColumn(name = "nid", referencedColumnName = "entity_id") )
public class Poem {
    @Id
    @Column(name = "entity_id")
    private Integer id;
    @Column(table = "node_field_data",name = "title")
    private String title;
    @Lob
    @Column(name = "body_value")
    private String text;

    public Poem(Integer id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Poem() {

    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
