package placeholder.model;

import java.util.Set;

public interface Saveable<T> {
    void save(Set<T> s, String outSideListKey, String insideListKey);
}
