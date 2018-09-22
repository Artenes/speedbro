package io.github.artenes.speedbro.speedrun.com.website;

import org.jsoup.nodes.Document;

/**
 * A parser that convert html documents to a POJO
 *
 * @param <T> the type of the POJO
 */
public interface Parser<T> {

    T parse(Document document);

}