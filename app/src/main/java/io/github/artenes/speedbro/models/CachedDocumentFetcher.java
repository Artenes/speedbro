package io.github.artenes.speedbro.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Calendar;

import io.github.artenes.speedbro.db.CachedRequestDAO;
import io.github.artenes.speedbro.db.CachedResponse;
import io.github.artenes.speedbro.db.Database;
import io.github.artenes.speedbro.speedrun.com.website.DocumentFetcher;

/**
 * Version of DocumentFetcher class that provides
 * local cache mechanism
 */
public class CachedDocumentFetcher extends DocumentFetcher {

    private final CachedRequestDAO requestDAO;

    public CachedDocumentFetcher(Database database) {
        requestDAO = database.cachedRequestDAO();
    }

    @Override
    public Document asHtml(String url) throws IOException {
        CachedResponse request = getHtmlResponse(url, getExpireTime());
        return Jsoup.parse(request.getResponse());
    }

    private CachedResponse getHtmlResponse(String url, long expiresIn) throws IOException {
        CachedResponse request = requestDAO.getCachedRequest(url);

        //if nothing was found in db, there is no cache
        if (request == null) {
            //create a new one and make the request to cache the response
            request = new CachedResponse();
            request.setUrl(url);
            request.setExpires_in(expiresIn);
            Document document = Jsoup.connect(url).get();
            request.setResponse(document.toString());
            requestDAO.insert(request);
        } else {
            //there is a cache in db
            Calendar now = Calendar.getInstance();
            boolean isExpired = now.after(request.asCalendar());
            //if is expired make a new request and cache it
            if (isExpired) {
                requestDAO.remove(request);
                Document document = Jsoup.connect(url).get();
                request.setResponse(document.toString());
                requestDAO.insert(request);
            }
        }

        return request;
    }

    /**
     * Cache everything for 10 minutes
     *
     * @return the time to keep the cache alive
     */
    private long getExpireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        return calendar.getTimeInMillis();
    }

}