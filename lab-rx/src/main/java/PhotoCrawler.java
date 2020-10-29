import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import model.Photo;
import model.PhotoSize;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {
        try {
            photoDownloader.getPhotoExamples().compose(this::processPhotos).subscribe(photoSerializer::savePhoto);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForQuery(String query) throws IOException {
        try {
            photoDownloader.searchForPhotos(query).compose(this::processPhotos).subscribe(photoSerializer::savePhoto);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) throws IOException {
        photoDownloader.searchForPhotos(queries).compose(this::processPhotos).subscribe(photoSerializer::savePhoto);
    }

    public @NonNull Observable<Photo> processPhotos(Observable<Photo> photos) {
        //        return photos.filter(photoProcessor::isPhotoValid).groupBy(PhotoSize::resolve).map(photoProcessor::convertToMiniature);
        return photos.filter(photoProcessor::isPhotoValid).groupBy(PhotoSize::resolve).flatMap(photoSizes -> {
            
        });
    }
}
