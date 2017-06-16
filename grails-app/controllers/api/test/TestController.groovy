package api.test

import com.affiliate.darazServices.DarazApi
import com.affiliate.helpers.RetrofitInterface
import com.affiliate.retrofitModels.Entity
import com.affiliate.retrofitModels.GetEntityById
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestController {

    def update() {
        String XML = '<?xml version="1.0" encoding="UTF-8" ?>\n' +
                '<Request>\n' +
                '  <Product>\n' +
                '    <SellerSku>4105382173aaee4</SellerSku>\n' +
                '    <Price>12</Price>\n' +
                '  </Product>\n' +
                '  <Product>\n' +
                '    <SellerSku>4928a374c28ff1</SellerSku>\n' +
                '    <Quantity>4</Quantity>\n' +
                '  </Product>\n' +
                '</Request>'
        //new DarazApi().Update(XML)
        render("update hogaya")
    }
    def create(){


        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(com.affiliate.helpers.ProjectConstants.protocol+com.affiliate.helpers.ProjectConstants.IP)
                .build()
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        GetEntityById getEntityById = new GetEntityById();
        getEntityById.setAction("getEntityById")
        getEntityById.dataPacket.setLimit(0)
        getEntityById.dataPacket.setStart(0)
        List<Entity> entities = new ArrayList<Entity>();
        List<String> ids = new ArrayList<String>();
        ids.addAll(["10001328","10001328","10001330"]);
        entities.add(new Entity("product", ids));
        getEntityById.dataPacket.setEntity(entities);
        Call<ResponseBody> caller = service.getEntityById(getEntityById);
        caller.enqueue(new Callback<ResponseBody>() {
            @Override
            void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String message = response.body().string().toString();
                JSONObject jsonObj = new JSONObject(message);
                new DarazApi().Create(jsonObj["dataPacket"]["product"]);
                def socket = new DatagramSocket()

                def data = message.getBytes("ASCII")
                def packet = new DatagramPacket(data, data.length,InetAddress.getByName("dev.technify.pk"),2214)
                socket.send(packet)
            }

            @Override
            void onFailure(Call<ResponseBody> call, Throwable t) {
                println("Retrofit ka bhund hai");
            }
        });











//        String XML = '<?xml version="1.0" encoding="UTF-8" ?>\n' +
//                '<Request>\n' +
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
//                '</Request>'
        //new DarazApi().Create(XML)
        render("create hogaya")
    }
}
