package adroidtown.org.graduateproject.models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Message.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static AppDatabase instance = null;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance =  Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "memo_Database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
