package yin.style.baselib.net.inter;

import java.io.File;
import java.util.Map;

public interface BInterceptor {
    Map<String, String> post(Map<String, String> postMap);

    Map<String, String> get(Map<String, String> getMap);

    Map<String, File> upload(Map<String, File> getMap);
}
