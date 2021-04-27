package adroidtown.org.graduateproject.models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(adroidtown.org.graduateproject.models.Message user);

    @Update
    void update(adroidtown.org.graduateproject.models.Message user);

    @Query("UPDATE memoTable SET user_title = :t WHERE user_id =:id")
    void update(String t, int id);

    @Delete
    void delete(adroidtown.org.graduateproject.models.Message user);

    @Query("SELECT * FROM  memoTable")
    List<adroidtown.org.graduateproject.models.Message> getAll();

    @Query("DELETE FROM memoTable")
    void deleteAll();

    @Query("SELECT COUNT(*) as cnt FROM memoTable")
    int getDataCount();
}
