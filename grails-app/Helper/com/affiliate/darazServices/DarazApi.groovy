package com.affiliate.darazServices

import org.json.JSONArray
import org.json.JSONObject

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by yousu on 6/14/2017.
 */
class DarazApi {

    def Create(JSONArray products){

        String XML = '<?xml version="1.0" encoding="UTF-8" ?>\n<Request>\n';
        for(int i = 0 ; i < products.length();i++){
            JSONObject product = (JSONObject) products.get(i);
            String productXML = '<Product>\n';
            if(product.has("technify_id") //for sku
                    && product.has("name")
                    && product.has("product_category")
                    && product.has("description")
                    && product.has("store_id")
                    && product.has("price")){
                productXML = productXML + '     <SellerSku>'+product.get("technify_id")+'</SellerSku>\n' + // should replaced
                            '    <Name>'+product.get("name")+'</Name>\n' +
                            '    <PrimaryCategory>4</PrimaryCategory>\n' + // should replaced
                            '    <Description><![CDATA['+product.get("description")+']]>\n</Description>\n' + //must check
                            '    <Brand>Apple</Brand>\n' + // api hit to get brand name from store_id
                            '    <Price>'+product.get("price")+'</Price>\n' +
                            '    <TaxClass>default</TaxClass>\n'
                if(product.has("quantity"))
                    productXML = productXML +   '    <Quantity>'+product.get("quantity")+'</Quantity>\n'

                XML = XML + productXML + '  </Product>\n'
            }
            else {
                println("Mandatory fields are missing!");
                return
            }
        }
//                '  <Product>\n' +
//                '    <SellerSku>4105382173aaee4</SellerSku>\n' +
//                '    <ParentSku/>\n' +
//                '    <Status>active</Status>\n' +
//                '    <Name>Magic Product</Name>\n' +
//                '    <Variation>XXL</Variation>\n' +
//                '    <PrimaryCategory>4</PrimaryCategory>\n' +
//                '    <Categories>2,3,5</Categories>\n' +
//                '    <Description><![CDATA[This is a <b>bold</b> product.]]></Description>\n' +
//                '    <Brand>ASM</Brand>\n' +
//                '    <Price>1.00</Price>\n' +
//                '    <SalePrice>32.5</SalePrice>\n' +
//                '    <SaleStartDate>2013-09-03T11:31:23+00:00</SaleStartDate>\n' +
//                '    <SaleEndDate>2013-10-03T11:31:23+00:00</SaleEndDate>\n' +
//                '    <TaxClass>default</TaxClass>\n' +
//                '    <ShipmentType>dropshipping</ShipmentType>\n' +
//                '    <ProductId>xyzabc</ProductId>\n' +
//                '    <Condition>new</Condition>\n' +
//                '    <ProductData>\n' +
//                '      <Megapixels>490</Megapixels>\n' +
//                '      <OpticalZoom>7</OpticalZoom>\n' +
//                '      <SystemMemory>4</SystemMemory>\n' +
//                '      <NumberCpus>32</NumberCpus>\n' +
//                '      <Network>This is network</Network>\n' +
//                '    </ProductData>\n' +
//                '    <Quantity>10</Quantity>\n' +
//                '  </Product>\n' +
//                '  <Product>\n' +
//                '    <SellerSku>513558029156743ab4e3</SellerSku>\n' +
//                '    <ParentSku/>\n' +
//                '    <Status>active</Status>\n' +
//                '    <Name>Normal Product</Name>\n' +
//                '    <Variation>XS</Variation>\n' +
//                '    <PrimaryCategory>4</PrimaryCategory>\n' +
//                '    <Categories>2,3,5</Categories>\n' +
//                '    <Description><![CDATA[This is a <i>cursive</i> product.]]></Description>\n' +
//                '    <Brand>BIN</Brand>\n' +
//                '    <Price>2.50</Price>\n' +
//                '    <SalePrice>1.50</SalePrice>\n' +
//                '    <SaleStartDate>2016-07-01T11:15:07+00:00</SaleStartDate>\n' +
//                '    <SaleEndDate>2016-07-01T11:15:07+00:00</SaleEndDate>\n' +
//                '    <TaxClass>default</TaxClass>\n' +
//                '    <ShipmentType>dropshipping</ShipmentType>\n' +
//                '    <ProductId>foobar</ProductId>\n' +
//                '    <Condition>new</Condition>\n' +
//                '    <ProductData>\n' +
//                '      <Megapixels>1</Megapixels>\n' +
//                '      <OpticalZoom>100</OpticalZoom>\n' +
//                '      <SystemMemory>2</SystemMemory>\n' +
//                '      <NumberCpus>3</NumberCpus>\n' +
//                '      <Network>This is network</Network>\n' +
//                '    </ProductData>\n' +
//                '    <Quantity>5</Quantity>\n' +
//                '  </Product>\n' +
        XML = XML+'</Request>'

        println(XML)

        Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", com.affiliate.helpers.ProjectConstants.USERNAME);
        params.put("Timestamp", getCurrentTimestamp());
        params.put("Version", "1.0");
        params.put("Action", "ProductCreate");
        params.put("Format","JSON");
        final String apiKey = com.affiliate.helpers.ProjectConstants.darazApi;

        final String out = getSellercenterApiResponse(params, apiKey, XML); // provide XML as an empty string when not needed
        System.out.println(out); // print out the XML response
    }





    def Update(JSONArray products){
        Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", com.affiliate.helpers.ProjectConstants.USERNAME);
        params.put("Timestamp", getCurrentTimestamp());
        params.put("Version", "1.0");
        params.put("Action", "ProductUpdate");
        params.put("Format","JSON");
        final String apiKey = com.affiliate.helpers.ProjectConstants.darazApi;
        final String XML = data;
        final String out = getSellercenterApiResponse(params, apiKey, XML); // provide JSON as an empty string when not needed
        System.out.println(out); // print out the JSON response
    }







    public static String getSellercenterApiResponse(Map<String, String> params, String apiKey, String XML) {
        String queryString = "";
        String Output = "";
        HttpURLConnection connection = null;
        URL url = null;
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        queryString = toQueryString(sortedParams);
        final String signature = hmacDigest(queryString, apiKey, com.affiliate.helpers.ProjectConstants.HASH_ALGORITHM);
        queryString = queryString.concat("&Signature=".concat(signature));
        final String request = com.affiliate.helpers.ProjectConstants.ScApiHost.concat("?".concat(queryString));
        try {
            url = new URL(request);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", com.affiliate.helpers.ProjectConstants.CHAR_UTF_8);
            connection.setUseCaches(false);
            if (!XML.equals("")) {
                connection.setRequestProperty("Content-Length", "" + Integer.toString(XML.getBytes().length));
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(XML);
                wr.flush();
                wr.close();
            }
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                Output += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Output;
    }








    private static String toQueryString(Map<String, String> data) {
        String queryString = "";
        try{
            StringBuffer params = new StringBuffer();
            for (Map.Entry<String, String> pair : data.entrySet()) {
                params.append(URLEncoder.encode((String) pair.getKey(), com.affiliate.helpers.ProjectConstants.CHAR_UTF_8) + "=");
                params.append(URLEncoder.encode((String) pair.getValue(), com.affiliate.helpers.ProjectConstants.CHAR_UTF_8) + "&");
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            queryString = params.toString();
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return queryString;
    }











    private static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes(com.affiliate.helpers.ProjectConstants.CHAR_UTF_8), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);
            final byte[] bytes = mac.doFinal(msg.getBytes(com.affiliate.helpers.ProjectConstants.CHAR_ASCII));
            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest;
    }






    private static String getCurrentTimestamp(){
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        df.setTimeZone(tz);
        final String nowAsISO = df.format(new Date());
        return nowAsISO;
    }
}
