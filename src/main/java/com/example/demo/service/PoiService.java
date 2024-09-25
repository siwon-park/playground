package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PoiService {

    /*
    * TO-DO
    * 1) 불린형으로 변경하여 컨트롤러에서 분기처리
    * 2) 템플릿을 읽은 다음에, 템플릿에 Project의 내용을 기반으로 작성하게 해야함
    * 3) 최종적으로 작성을 다 하고나면 pdf로 변환
    */

    public void create() {
        FileOutputStream fos = null;
        XWPFDocument documentWord = null;
        // 파일을 생성할 때는 try-catch 구문으로 사용해야 한다.
        try {
            String tmpDir  = System.getProperties().getProperty("java.io.tmpdir"); // 임시 경로
            String wordFileName = DateFormatUtils.format(new Date(), "YYMMdd") + "_SpringWord.docx";
            String wordFilePath = tmpDir + wordFileName;
            documentWord = new XWPFDocument();

            XWPFParagraph xwpfParagraph = documentWord.createParagraph();
            xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun testRun = xwpfParagraph.createRun();
            testRun.setFontFamily("맑은 고딕");
            testRun.setColor("2FB2F3");
            testRun.setFontSize(18);
            testRun.setText("개발완료확인서");

            fos = new FileOutputStream(wordFilePath);
            documentWord.write(fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                documentWord.close();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }

}
