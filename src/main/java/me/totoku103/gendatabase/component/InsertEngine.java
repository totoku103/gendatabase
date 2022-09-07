package me.totoku103.gendatabase.component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.totoku103.gendatabase.mapper.InsertMapper;
import me.totoku103.gendatabase.model.ImcMtMsg;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class InsertEngine {
    private final InsertMapper insertMapper;
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public String process(int insertCount) throws NumberParseException {
        final String threadName = Thread.currentThread().getName();
        final StopWatch stopWatch = new StopWatch("GEN-DATA" + threadName);
        stopWatch.start(threadName + " start");

        final LocalDateTime now = LocalDateTime.now();
        final List<ImcMtMsg> params = new ArrayList<>();
        long resultCount = 0;

//        final String internationalPhoneNumber = "+14438394437";
        final String internationalPhoneNumber = "+15714788202";
//        final String internationalPhoneNumber = "+821099999999";
        final Phonenumber.PhoneNumber parse = phoneNumberUtil.parse(internationalPhoneNumber, "");
        final LocalDate nowDate = LocalDate.now();


        for (int i = 1; i <= insertCount; i++) {
//            ImcMtMsg imcMtMsg = new ImcMtMsg("IM", "1", "N", now, "821027790002", String.valueOf(parse.getCountryCode()), "InfoSMS", String.format("%s 동해물과백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세 무궁화 삼천리 화려 강한 대한 사람 대한으로 길이 보전하세 %s %3d", nowDate, threadName, i));
            ImcMtMsg imcMtMsg = new ImcMtMsg("IM", "1", "N", now, String.valueOf(parse.getNationalNumber()), String.valueOf(parse.getCountryCode()), "18334022199", "동해물과백두산이 마르고 닳도록 하느님이 보우하사");
//            ImcMtMsg imcMtMsg = new ImcMtMsg("IM", "1", "N", now, String.valueOf(parse.getNationalNumber()).replaceAll("\\D", ""), String.valueOf(parse.getCountryCode()), "InfoSMS", String.format("%s 가나다라마바사아 감자 감자 hi %s %3d", nowDate, threadName, i));
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
