package brightspot.imageitemstream;

import java.util.ArrayList;
import java.util.List;

import brightspot.itemstream.DateRangeMatch;
import brightspot.query.QueryBuilder;
import brightspot.query.QueryBuilderAnyMatch;
import brightspot.tag.TagMatch;
import com.psddev.dari.db.Record;
import com.psddev.dari.db.Recordable;

@Recordable.DisplayName("Any Match")
@Recordable.Embedded
public class DynamicImageItemStreamAnyMatch extends Record implements QueryBuilderAnyMatch {

    @CollectionMinimum(1)
    @Types({
        DateRangeMatch.class,
        DynamicImageItemStreamAllMatch.class,
        DynamicImageItemStreamNoneMatch.class,
        TagMatch.class
    })
    private List<QueryBuilder> rules;

    @Override
    public List<QueryBuilder> getRules() {
        if (rules == null) {
            rules = new ArrayList<>();
        }
        return rules;
    }

    @Override
    public void setRules(List<QueryBuilder> rules) {
        this.rules = rules;
    }
}
