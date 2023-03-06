package com.example.oblig2dat153.database;

import android.content.Context;

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

                // images from drwawable
                byte[] byteImageClifford = ConverterHelper.fromDrawableToByteArray(ctx, R.drawable.clifford);
                byte[] byteImageBrianGriffen = ConverterHelper.fromDrawableToByteArray(ctx, R.drawable.brian_griffin);
                byte[] byteImagePluto = ConverterHelper.fromDrawableToByteArray(ctx, R.drawable.pluto);
                byte[] byteImageScoopyDooPido = ConverterHelper.fromDrawableToByteArray(ctx, R.drawable.scooby_doo_pido);

                Image cliffordImage = new Image();
                cliffordImage.setImageName("Clifford");
                cliffordImage.setImageData(byteImageClifford);

                Image brianGriffenImage = new Image();
                brianGriffenImage.setImageName("Brian Griffen");
                brianGriffenImage.setImageData(byteImageBrianGriffen);

                Image plutoImage = new Image();
                plutoImage.setImageName("Pluto");
                plutoImage.setImageData(byteImagePluto);

                Image scoopyDooPidoImage = new Image();
                scoopyDooPidoImage.setImageName("Scoopy Doo Pido");
                scoopyDooPidoImage.setImageData(byteImageScoopyDooPido);

                imageDAO.insert(cliffordImage);
                imageDAO.insert(brianGriffenImage);
                imageDAO.insert(plutoImage);
                imageDAO.insert(scoopyDooPidoImage);
            }
        });


    }

}
