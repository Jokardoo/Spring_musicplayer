package Daniil.Spring.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Table(name = "music")
@Entity
public class Music {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "band")
    private String band;

    @Column(name = "song")
    private String song;

    @Column(name = "genre")
    private String genre;

    @Column(name = "download_link")
    @Size(min = 2, max = 500, message = "name of genre should be between 2 and 500")
    private String download_link;

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public Music() {
        this.fullName = band + " - " + song;
    }

    public Music(String band, String song) {
        this.band = band;
        this.song = song;
    }

    public Music(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", band='" + band + '\'' +
                ", song='" + song + '\'' +
                ", genre='" + genre + '\'' +
                ", download_link='" + download_link + '\'' +
                '}';
    }
}
