package tiw1.repository;

import tiw1.domain.Trottinette;

import java.util.Map;

public interface TrottinetteLoader {
    Map<Long, Trottinette> getTrottinettes();

    Trottinette getTrottinette(Long id);
}
