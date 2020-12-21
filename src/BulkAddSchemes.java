import okhttp3.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class BulkAddSchemes {
    public static void main(String[] args) throws IOException {

        Workbook wb = new XSSFWorkbook("addSchemes.xlsx");

        FileOutputStream fout = new FileOutputStream("out.xlsx");

        //getall sheets
        Sheet bseSheet = wb.getSheetAt(0);
        Sheet schemeMasterSheet = wb.getSheetAt(1);
        Sheet sipSchemeSheet = wb.getSheetAt(2);
        Sheet additional = wb.getSheetAt(3);

        //Bse cells map
        HashMap<Integer, String> bseHash = new HashMap<>();
        bseHash.put(0, "bseSchemeId");
        bseHash.put(1, "uniqueNo");
        bseHash.put(2, "schemeCode");
        bseHash.put(3, "rtaschemeCode");
        bseHash.put(4, "amcSchemeCode");
        bseHash.put(5, "isinNo");
        bseHash.put(6, "amcCode");
        bseHash.put(7, "schemeName");
        bseHash.put(8, "purchase_transaction_modepurchaseTransactionMode");
        bseHash.put(9, "minPurchaseAmt");
        bseHash.put(10, "additionalPurchaseAmtMultiple");
        bseHash.put(11, "maxPurchaseAmt");
        bseHash.put(12, "purchaseAllowed");
        bseHash.put(13, "purchaseAllowed");
        bseHash.put(14, "redeemTransactionMode");
        bseHash.put(15, "minRedeemQty");
        bseHash.put(16, "redeemQtyMultiplier");
        bseHash.put(17, "maxRedeemQty");
        bseHash.put(18, "redeemAllowed");
        bseHash.put(19, "redeemCutOffTime");
        bseHash.put(20, "rtaAgentCode");
        bseHash.put(21, "amcActiveFlag");
        bseHash.put(22, "dividentReinvstFlag");
        bseHash.put(23, "schemeType");
        bseHash.put(24, "sipFlag");
        bseHash.put(25, "stpFlag");
        bseHash.put(26, "swpFlag");
        bseHash.put(27, "settlementType");
        bseHash.put(28, "purchaseAmtMultiplier");
        bseHash.put(29, "createdTimestamp");
        bseHash.put(30, "updatedTimestamp");
        bseHash.put(31, "redeemSchemeCode");
        bseHash.put(32, "minRedeemAmt");
        bseHash.put(33, "maxRedemptionAmt");
        bseHash.put(34, "exitLoad");
        bseHash.put(35, "lockInPeriod");
        bseHash.put(36, "switchFlag");

        //keep track of clums that are numeric
        Integer[] bseNumeric = {1, 9, 10, 11, 15, 16, 17, 21, 28, 32, 33, 34, 35};
        //use set for quick search
        HashSet<Integer> bseNumericSet = new HashSet<>(Arrays.asList(bseNumeric));

        //Scheme master cells map

        HashMap<Integer, String> schemeMasterHash = new HashMap<>();
        schemeMasterHash.put(0, "schemeMasterId");
        schemeMasterHash.put(1, "schemeName");
        schemeMasterHash.put(2, "schemeCategory");
        schemeMasterHash.put(3, "schemeType");
        schemeMasterHash.put(4, "schemeHouse");
        schemeMasterHash.put(5, "schemeOtherInfo");
        schemeMasterHash.put(6, "schemeKnowMoreLink");
        schemeMasterHash.put(7, "schemeStatus");
        schemeMasterHash.put(8, "schemeNAV");
        schemeMasterHash.put(9, "createdTimestamp");
        schemeMasterHash.put(10, "updatedTimestamp");
        schemeMasterHash.put(11, "schemeRating");
        schemeMasterHash.put(12, "schemeReturn");
        schemeMasterHash.put(13, "schemeISIN");


        Integer schemeMasterNumeric[] = {2, 3, 4, 7, 8, 11,};
        HashSet<Integer> schemeMasterSet = new HashSet<>(Arrays.asList(schemeMasterNumeric));

        //Sip Cells map

        HashMap<Integer, String> sipHash = new HashMap<>();
        sipHash.put(0, "sipSchemeId");
        sipHash.put(1, "schemeMFMapping");
        sipHash.put(2, "transactionMode");
        sipHash.put(3, "frequency");
        sipHash.put(4, "allowedDates");
        sipHash.put(5, "minimumGap");
        sipHash.put(6, "maximumGap");
        sipHash.put(7, "instalmentGap");
        sipHash.put(8, "mfStatus");
        sipHash.put(9, "minInstalmentAmount");
        sipHash.put(10, "maxInstalmentAmount");
        sipHash.put(11, "multiplierAmount");
        sipHash.put(12, "minInstalmentNumber");
        sipHash.put(13, "maxInstalmentNumber");
        sipHash.put(14, "status");
        sipHash.put(15, "createdTimestamp");
        sipHash.put(16, "updatedTimestamp");
        sipHash.put(17, "sipRating");
        sipHash.put(18, "schemeReturn");

        Integer sipSchemeNmeric[] = {3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 17};
        HashSet<Integer> sipSchemeSet = new HashSet<>(Arrays.asList(sipSchemeNmeric));

        Row r = bseSheet.getRow(0);
        int start = 0, end = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter start index");
        start = sc.nextInt();
        System.out.println("Enter end index");
        end = sc.nextInt();
        try {


            for (int i = start; i <= end; i++) {
                //Creating bse object
                r = bseSheet.getRow(i);
                JSONObject request = new JSONObject();
                createBSeObject(bseHash, bseNumericSet, r, request);


                //creating scheme master object
                r = schemeMasterSheet.getRow(i);
                createOneTimeObject(schemeMasterHash, schemeMasterSet, r, request);

                //creating sip scheme master
                r = sipSchemeSheet.getRow(i);
                createSipObject(sipHash, sipSchemeSet, r, request);

                //Getting scheme category details
                r = additional.getRow(i);
                request.put("suggestionCategory", r.getCell(0).getNumericCellValue());
                request.put("adminPassword", "th!5is5p@rta");


                System.out.println(request);


                //make api call
                Response response = callApi(request);
                int status = response.code();
                String output = response.body().string();
                System.out.println(status + " " + output);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        wb.write(fout);
        fout.close();
        wb.close();
    }

    @NotNull
    private static Response callApi(JSONObject request) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, request.toString());
        String localUrl = "http://localhost:8080/cashrich/scheme/addScheme.json?uname=crdev&pwd=crdev567";
        Request requestHttp = new Request.Builder()
                .url(localUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        return client.newCall(requestHttp).execute();
    }

    private static void createSipObject(HashMap<Integer, String> sipHash, HashSet<Integer> sipSchemeSet, Row r, JSONObject request) throws JSONException {
        JSONObject sipSchemeMaster = new JSONObject();

        for (int j = 0; j <= 18; j++) {
            Cell c = r.getCell(j);

            if (c != null) {
                if (sipSchemeSet.contains(j)) {
                    sipSchemeMaster.put(sipHash.get(j), c.getNumericCellValue());
                } else {
                    c.setCellType(CellType.STRING);
                    sipSchemeMaster.put(sipHash.get(j), c.getStringCellValue());
                }
            }


        }
        request.put("sipSchemeMasterDTO", sipSchemeMaster);
    }

    private static void createOneTimeObject(HashMap<Integer, String> schemeMasterHash, HashSet<Integer> schemeMasterSet, Row r, JSONObject request) throws JSONException {
        JSONObject schemeMaster = new JSONObject();
        for (int j = 0; j <= 13; j++) {
            Cell c = r.getCell(j);
            if (c != null) {
                if (schemeMasterSet.contains(j)) {
                    schemeMaster.put(schemeMasterHash.get(j), c.getNumericCellValue());
                } else {
                    c.setCellType(CellType.STRING);
                    schemeMaster.put(schemeMasterHash.get(j), c.getStringCellValue());
                }

            }
        }

        request.put("schemeMasterDTO", schemeMaster);
    }

    private static void createBSeObject(HashMap<Integer, String> bseHash, HashSet<Integer> bseNumericSet, Row r, JSONObject request) throws JSONException {


        JSONObject bseSchemeMaster = new JSONObject();
        for (int j = 0; j <= 36; j++) {
            Cell c = r.getCell(j);

            if (c != null) {
                if (bseNumericSet.contains(j)) {
                    bseSchemeMaster.put(bseHash.get(j), r.getCell(j).getNumericCellValue());
                } else {
                    c.setCellType(CellType.STRING);
                    bseSchemeMaster.put(bseHash.get(j), r.getCell(j).getStringCellValue());
                }


            }
        }
        request.put("bseSchemeMasterDTO", bseSchemeMaster);
    }

}
