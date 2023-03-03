package com.example.oblig2dat153.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.Utils.ConverterHelper;
import com.example.oblig2dat153.dao.ImageDAO;
import com.example.oblig2dat153.model.Image;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Image.class}, version = 1)
public abstract class ImageDatabase extends RoomDatabase {


    public abstract ImageDAO imageDAO();

    private static ImageDatabase instance;

    private static Context ctx;

    public static synchronized ImageDatabase getInstance(Context context) {
        ctx = context;
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ImageDatabase.class, "courses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // Callback
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Insert data when db is created...
            InitializeData();
        }
    };

    private static void InitializeData() {

        ImageDAO imageDAO = instance.imageDAO();


        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                ConverterHelper converterHelper = new ConverterHelper();

                // Categories
                Log.d("Yosafe", "here");

//                imageDAO.insert(image);

                // images from drwawable
                byte[] byteImageClifford = converterHelper.fromDrawableToByteArray(ctx, R.drawable.clifford);
                byte[] byteImageBrianGriffen = converterHelper.fromDrawableToByteArray(ctx, R.drawable.brian_griffin);
                byte[] byteImagePluto = converterHelper.fromDrawableToByteArray(ctx, R.drawable.pluto);
                byte[] byteImageScoopyDooPido = converterHelper.fromDrawableToByteArray(ctx, R.drawable.scooby_doo_pido);

                Image cliffordImage = new Image();
                cliffordImage.setImageName("Clifford");
                cliffordImage.setImageWrongName1("Selam");
                cliffordImage.setImageWrongName2("Semhar");
                cliffordImage.setImageData(byteImageClifford);

                Image brianGriffenImage = new Image();
                brianGriffenImage.setImageName("Brian Griffen");
                brianGriffenImage.setImageWrongName1("Michaela");
                brianGriffenImage.setImageWrongName2("Emone");
                brianGriffenImage.setImageData(byteImageBrianGriffen);

                Image plutoImage = new Image();
                plutoImage.setImageName("Pluto");
                plutoImage.setImageWrongName1("Benedikte");
                plutoImage.setImageWrongName2("Arsema");
                plutoImage.setImageData(byteImagePluto);

                Image scoopyDooPidoImage = new Image();
                scoopyDooPidoImage.setImageName("Scoopy Doo Pido");
                scoopyDooPidoImage.setImageWrongName1("Azeb");
                scoopyDooPidoImage.setImageWrongName2("Yosafe");
                scoopyDooPidoImage.setImageData(byteImageScoopyDooPido);

                imageDAO.insert(cliffordImage);
                imageDAO.insert(brianGriffenImage);
                imageDAO.insert(plutoImage);
                imageDAO.insert(scoopyDooPidoImage);
            }
        });


    }

}
