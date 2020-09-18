package XLS;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    public static final String file = "CompanyIndicators.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        List<Company> listCompany = new ArrayList<>();
        String parrent = "";
        int i = -1;
        Workbook workbook = WorkbookFactory.create(new File(file));
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Company company = new Company();
            Company company1 = new Company();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                for (int e = 0; e < sheet.getNumMergedRegions(); e++) {
                    CellRangeAddress region = sheet.getMergedRegion(e); //Region of merged cells
                    for (int j = region.getFirstRow(); j <= region.getLastRow(); j++) {
                        for (int k = region.getFirstColumn(); k <= region.getLastColumn(); k++) {
                            if (cell.getColumnIndex() == k && cell.getRowIndex() == j) {
                                Row rowNew = sheet.getRow(region.getFirstRow());
                                cell = rowNew.getCell(region.getFirstColumn());
                            }
                        }
                    }
                }
                if (row.getRowNum() != 0) {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            company.setParrent(null);
                            if (!parrent.equals(cell.getStringCellValue())) {
                                parrent = cell.getStringCellValue();
                                company.setCompany(cell.getStringCellValue());
                                listCompany.add(company);
                                i++;
                            }
                            ;
                            break;
                        case 1:
                            company1.setParrent(parrent);
                            company1.setCompany(cell.getStringCellValue());
                            break;
                        case 2:
                            company1.setPrognoz(cell.getNumericCellValue());
                            break;
                        case 3:
                            company1.setFact(cell.getNumericCellValue());
                            company1.setPercent(company1.getFact() / company1.getPrognoz());
                            listCompany.get(i).setSubsidiary(company1);
                            break;
                    }
                }

                System.out.print(cell + "\t");
            }
            company.setPercent(company.getFact() / company.getPrognoz());
            System.out.println();
        }

        for (Company company : listCompany) {
            double percent = 0;
            double prognoz = 0;
            double fact = 0;
            for (Company subsidiary : company.subsidiary) {
                percent = percent + subsidiary.percent;
                fact = fact + subsidiary.fact;
                prognoz = prognoz + subsidiary.prognoz;
            }
            company.setPercent(percent / company.subsidiary.size());
            company.setFact(fact);
            company.setPrognoz(prognoz);
        }
    }
}