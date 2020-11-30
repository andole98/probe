package domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter

@Document(indexName = "player")
public class AutoComplete {

    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text)
    private String name;

    @Field(name = "city", type = FieldType.Keyword)
    private String city;

    public AutoComplete(String name, String city) {
        this.name = name;
        this.city = city;
    }
}
