package com.example.demo.service;

import com.example.demo.domain.Project;
import com.example.demo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PoiService {

    private final ProjectRepository projectRepository;

    public void create(long pid) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\SIWON\\Downloads\\템플릿.docx");
        XWPFDocument document = new XWPFDocument(fis);
        List<XWPFParagraph> paragraphs = document.getParagraphs(); // 문서 패러그래프의 정보
        Project project = projectRepository.findById(pid).orElseThrow();
        String company = project.getCompany();
        Date finishDate = project.getFinishDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        String finDate = sdf.format(finishDate);

        XWPFRun run;
        run = paragraphs.get(19).getRuns().get(0); // 19번째 패러그래프의 런 정보의 첫번째 값부터 가져옴; 날짜
        run.setText(finDate, 0);
        int runSize = paragraphs.get(19).getRuns().size();
        for (int i = runSize - 1; i > 0; i--) {
            paragraphs.get(19).removeRun(i);
        }

        run = paragraphs.get(21).getRuns().get(0);
        run.setText("㈜" + company + " 귀중", 0);
        runSize = paragraphs.get(21).getRuns().size();
        for (int i = runSize - 1; i > 0; i--) {
            paragraphs.get(21).removeRun(i);
        }

        /*
        * 셀 안에 패러그래프가 있고, 해당 패러그래프 안에 run들이 있는데
        * 이 들을 하나씩 잡아가면서 진행하는 것보다 아예 처음부터 세팅하고 수정하는 방향으로 정정함.
        * */
        int cellNo = 0;
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    if (cellNo == 0) {
                        XWPFRun cellRun = cell.getParagraphs().get(1).getRuns().get(0);
                        cellRun.setText("㈜" + company, 0);
                        runSize = cell.getParagraphs().get(1).getRuns().size();
                        for (int i = runSize - 1; i > 0; i--) {
                            cell.getParagraphs().get(1).removeRun(i);
                        }
                    }
                    if (cellNo == 3) {
                        cell.setText(project.getName());
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
                    }
                    if (cellNo == 5) {
                        cell.setText(project.getCustomer());
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
                    }
                    if (cellNo == 7) {
                        cell.setText(sdf.format(project.getStartDate()) + " ~ " + sdf.format(project.getEndDate()));
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
                    }
                    if (cellNo == 9) {
                        cell.setText(sdf.format(project.getFinishDate()));
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
                    }
                    if (cellNo == 14) {
                        XWPFRun cellRun = cell.getParagraphs().get(0).createRun();
                        String[] lines = project.getDescription().split("\\r?\\n"); // \r(윈도우 개행)이나 \n(일반개행)으로 파싱
                        cellRun.setText(lines[0]);
                        for (int i = 1; i < lines.length; i++) {
                            cellRun.addBreak();
                            cellRun.setText(lines[i]);
                        }
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
                    }
                    if (cellNo == 18) {
                        XWPFRun cellRun = cell.getParagraphs().get(0).createRun();
                        cellRun.setText("회사명 : ㈜다우기술");
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
//                        cellRun.setText("주  소 : 경기도 성남시 금토로69, 4층");
//                        cellRun.addBreak();
//                        cellRun.setText("담당자 : " + project.getProjectManager());
                        cell.addParagraph().createRun().setText("주  소 : 경기도 성남시 금토로69, 4층");
                        cell.getParagraphs().get(1).getRuns().get(0).setFontFamily("맑은 고딕");
                        cell.addParagraph().createRun().setText("담당자 : " + project.getProjectManager());
                        cell.getParagraphs().get(2).getRuns().get(0).setFontFamily("맑은 고딕");
                    }
                    if (cellNo == 19) {
                        XWPFRun cellRun = cell.getParagraphs().get(0).createRun();
                        cellRun.setText("고객사 : ㈜" + project.getCompany());
                        cell.getParagraphs().get(0).getRuns().get(0).setFontFamily("맑은 고딕");
                        cell.addParagraph().createRun().setText("주  소 : " + project.getAddress());
                        cell.getParagraphs().get(1).getRuns().get(0).setFontFamily("맑은 고딕");
                        cell.addParagraph().createRun().setText("고객담당자 :    " + project.getCustomer() + "   (서명)");
                        cell.getParagraphs().get(2).getRuns().get(0).setFontFamily("맑은 고딕");
//                        cellRun.addCarriageReturn();
//                        cellRun.setText("주  소 : " + project.getAddress());
//                        cellRun.addBreak();
//                        cellRun.setText("고객담당자 :    " + project.getCustomer() + "   (서명)");

                    }
                    cellNo += 1;
                }
            }
        }


        SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMdd");
        String newFileName = "개발완료확인서_" + company + "_" + sdf2.format(finishDate) + ".docx";
        FileOutputStream fos = new FileOutputStream("C:\\Users\\SIWON\\Downloads\\" + newFileName);
        document.write(fos);
        IOUtils.closeQuietly(fos);
        document.close();
    }

    public void readTest() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\SIWON\\Downloads\\템플릿.docx");
        XWPFDocument document = new XWPFDocument(fis);

        // 문서의 모든 단락을 가져옴 -> 단락만 읽음
        int p = 1;
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // 단락의 텍스트 출력
            System.out.println("paragraph: " + (p++));
            System.out.println(paragraph.getText());
        }

        int c = 1;
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    System.out.println("cell Text " + (c++) + " " + cell.getText());
                }
            }
        }
    }

    // 테스트용
    public void readAndWrite() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\SIWON\\Downloads\\템플릿.docx");
        FileOutputStream fos = new FileOutputStream("C:\\Users\\SIWON\\Downloads\\개발완료확인서_수정.docx");
        XWPFDocument document = new XWPFDocument(fis);

        List<XWPFParagraph> paragraphs = document.getParagraphs();

        // run: 입력 내용?
        XWPFRun run;
        run = paragraphs.get(19).getRuns().get(0); // 19번째 패러그래프의 런 정보의 첫번째 값부터 가져옴; 날짜
        run.setText("2024년 10월 05일", 0);
        int runSize = paragraphs.get(19).getRuns().size();
        for (int i = runSize - 1; i > 0; i--) {
            paragraphs.get(19).removeRun(i);
        }
        document.write(fos);
        IOUtils.closeQuietly(fos);
        document.close();
    }



}
