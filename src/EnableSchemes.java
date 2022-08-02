import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class EnableSchemes {
    public static void main(String[] args) throws IOException, JSONException {
        Workbook wb = new XSSFWorkbook("schemes_to_enable.xlsx");
        String adminPwd
                //      = "th!5is5p@rta";
                = "6Cc9r53fXn";

        FileOutputStream fout = new FileOutputStream("out_enable.xlsx");

        Sheet sheet = wb.getSheetAt(0);
        Scanner sc = new Scanner(System.in);
        System.out.println("enter start index");
        int start = sc.nextInt();
        System.out.println("enter end index");
        int end = sc.nextInt();
        Row r = sheet.getRow(0);
        // Cell c = r.getCell(0);
        for (int i = start; i < end; i++) {
            System.out.println("count: " + i);
            SchemeEnableModel schemeEnableModel = new SchemeEnableModel();
            r = sheet.getRow(i);
            r.getCell(0).setCellType(CellType.STRING);
            r.getCell(1).setCellType(CellType.STRING);

            schemeEnableModel.setSchemeMasterId(r.getCell(0).getStringCellValue());
            String ct = r.getCell(1).getStringCellValue();
            schemeEnableModel.setSipSchemeMasterId(ct);
            schemeEnableModel.setEnableOnetime(true);
            schemeEnableModel.setEnableSIP(true);
            schemeEnableModel.setEnableInApp(true);
            schemeEnableModel.bucketIds.add(2);
            schemeEnableModel.bucketIds.add(4);
            schemeEnableModel.setAdminPassword(adminPwd);
            String request = new ObjectMapper().writeValueAsString(schemeEnableModel);
            System.out.println(request);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, request);

            String smokeUrl = "https://sougatabasu.com/cashrich/scheme/enableScheme.json?uname=user@sm0Ke&pwd=sm0k3P@ss2022";
            String cronUrl = "https://cron.cashrichapp.in/cashrich/scheme/enableScheme.json?uname=u53R@PR0d!2!2!&pwd=Pa55@PR0d!2!2!";
            Request requestHttp = new Request.Builder()
                    .url(cronUrl)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(requestHttp).execute();
            String output = response.body().string();
            System.out.println("output: " + output);
            r.createCell(3).setCellValue(output);
            JSONObject jsonObject = new JSONObject(output);
            JSONObject status = jsonObject.getJSONObject("status");
            String code = status.getString("code");
            r.createCell(4).setCellValue(code);


        }

        wb.write(fout);
        fout.close();
        wb.close();
    }
}
