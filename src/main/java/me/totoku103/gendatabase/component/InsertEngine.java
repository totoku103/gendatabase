package me.totoku103.gendatabase.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.totoku103.gendatabase.mapper.InsertMapper;
import me.totoku103.gendatabase.model.ImcMtMsg;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class InsertEngine {
    private final InsertMapper insertMapper;

    public String process(int insertCount) {
        final String threadName = Thread.currentThread().getName();
        final StopWatch stopWatch = new StopWatch("GEN-DATA" + threadName);
        stopWatch.start(threadName + " start");

        final LocalDateTime now = LocalDateTime.now();
        final List<ImcMtMsg> params = new ArrayList<>();
        long resultCount = 0;
        for (int i = 1; i <= insertCount; i++) {
            ImcMtMsg imcMtMsg = new ImcMtMsg("IM", "1", "N", now, "01027790002", "82", "0267131234", String.format("가나다라마바사아 감자 감자 hi %s %3d", threadName, i));
            params.add(imcMtMsg);

            if (i % 2000 == 0
                    && params.size() > 0) {
                resultCount += 2000;
                resultCount += insertMapper.insertBulk(params);
                params.clear();
            }
        }

        if (params.size() > 0) {
            resultCount += insertMapper.insertBulk(params);
            params.clear();
        }
        stopWatch.stop();
        return stopWatch.shortSummary() + ". result: " + resultCount;
    }
}
