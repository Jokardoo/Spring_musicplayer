package Daniil.Spring.models;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicSearchEngine {
    // через эту строку мы будем искать нужный нам трэк (String.format...)
    private static final String URL_FORMAT = "https://rur.hitmotop.com/";


    @SneakyThrows
    public List<Music> getMusicList(String trackName) {
        Document page = Jsoup.connect(URL_FORMAT + "search?q=" + trackName).get();
        // Это строка ведет к нужным нам html-элементам
        String tracksLink = "body > main.content > div.container > div.content-center.muslist "
                + "> div.content-inner > div.p-info.p-inner > ul.tracks__list > li.tracks__item.track.mustoggler";

        List<Music> trackList = new ArrayList<>();

        // выборка элементов - треков с нашей страницы
        var tracks = page.select(tracksLink);

        for (int i = 1; i < tracks.size(); i++) {
            // Название группы у данного трека
            String band = page.select(tracksLink).get(i).select("li.tracks__item.track.mustoggler "
                    + "> div.track__info > a.track__info-l > div.track__desc").text();

            // Название самой песни данного трека
            String songName = page.select(tracksLink).get(i).select("li.tracks__item.track.mustoggler "
                    + "> div.track__info > a.track__info-l > div.track__title").text();

            // Ссылка на скачивание данного трека
            String downloadLink = page.select(tracksLink).get(i).select("li.tracks__item.track.mustoggler "
                    + "> div.track__info > div.track__info-r > a.track__download-btn").attr("href");

            // Создаем трек и добавляем его в список. Также устанавливаем ему ссылку на скачивание
            Music music = new Music(band, songName);
            music.setDownload_link(downloadLink);

            trackList.add(music);
        }
        return trackList;
    }

    public MusicSearchEngine() {}

    public static void main(String[] args) {
        MusicSearchEngine musicSearchEngine = new MusicSearchEngine();

        List<Music> list = musicSearchEngine.getMusicList("muse");
        for (Music music : list) {
            System.out.println(music);
        }

    }
}
