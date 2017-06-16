package affiliate.daraz

import com.affiliate.darazServices.DarazApi
import com.affiliate.helpers.RetrofitInterface
import com.affiliate.retrofitModels.GetEntityById
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseCredentials
import com.affiliate.retrofitModels.Entity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.affiliate.helpers.ProjectConstants
import retrofit2.converter.gson.GsonConverterFactory

class BootStrap {

    def init = { servletContext ->
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Work\\GrailsTry\\affiliate-daraz\\grails-app\\assets\\firebase\\affiliate-52fff6775b75.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://affiliate-e48de.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = defaultDatabase.getReference("/product");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String entityName = dataSnapshot.getValue().getAt("entityName")
                String entityId = dataSnapshot.getValue().getAt("entityId")
                String event = dataSnapshot.getValue().getAt("event")
                String storeId = dataSnapshot.getValue().getAt("storeId")
                def socket1 = new DatagramSocket()
                String json1 ="{\n" +
                        "\t\"entityName\": "+entityName+",\n" +
                        "\t\"entityId\": "+entityId+",\n" +
                        "\t\"event\": "+event+",\n" +
                        "\t\"storeId\": \""+storeId+"\"\n" +
                        "}"
                def data1 = json1.getBytes("ASCII")
                def packet1 = new DatagramPacket(data1, data1.length,InetAddress.getByName("dev.technify.pk"),2214)
                socket1.send(packet1)
                final Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(ProjectConstants.protocol+ProjectConstants.IP)
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
//                        JSONObject jsonObj = new JSONObject(message);
//                        new DarazApi().Create(jsonObj["dataPacket"]["product"]);
                        def socket = new DatagramSocket()

                        def data = message.getBytes("ASCII")
                        def packet = new DatagramPacket(data, data.length,InetAddress.getByName("dev.technify.pk"),2214)
                        socket.send(packet)
                    }

                    @Override
                    void onFailure(Call<ResponseBody> call, Throwable t) {
                        println("Retrofit ka bhund hai");
                    }
                })
            }

            @Override
            void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    def destroy = {
    }
}
