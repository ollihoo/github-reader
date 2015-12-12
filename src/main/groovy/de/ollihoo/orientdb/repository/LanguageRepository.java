package de.ollihoo.orientdb.repository;

import de.ollihoo.orientdb.orientdomain.Language;
import de.ollihoo.orientdb.orientdomain.Qualifications;
import org.springframework.data.gremlin.repository.GremlinRepository;
import org.springframework.data.neo4j.annotation.Query;


public interface LanguageRepository extends GremlinRepository<Language> {
    static String LANGUAGES_WITH_RANKING_REQUEST =
            "match (l:Language)<-[:IMPLEMENTED_IN]-(r:Repository)<-[:WORKS_ON]-(e:Employee) where l.name = {0} return l.name as language, e.name as employee, count(r) as projectAmount order by projectAmount desc";

    Language findByName(String name);

    @Query(LANGUAGES_WITH_RANKING_REQUEST)
    Iterable<Qualifications> getLanguagesOrderedByQualification(String language);
}
