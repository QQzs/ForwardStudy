package com.zs.project.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEW_DATA".
*/
public class NewDataDao extends AbstractDao<NewData, Long> {

    public static final String TABLENAME = "NEW_DATA";

    /**
     * Properties of entity NewData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Src = new Property(1, String.class, "src", false, "SRC");
        public final static Property Weburl = new Property(2, String.class, "weburl", false, "WEBURL");
        public final static Property Time = new Property(3, String.class, "time", false, "TIME");
        public final static Property Pic = new Property(4, String.class, "pic", false, "PIC");
        public final static Property Title = new Property(5, String.class, "title", false, "TITLE");
        public final static Property Category = new Property(6, String.class, "category", false, "CATEGORY");
        public final static Property Url = new Property(7, String.class, "url", false, "URL");
    }


    public NewDataDao(DaoConfig config) {
        super(config);
    }
    
    public NewDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEW_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SRC\" TEXT," + // 1: src
                "\"WEBURL\" TEXT," + // 2: weburl
                "\"TIME\" TEXT," + // 3: time
                "\"PIC\" TEXT," + // 4: pic
                "\"TITLE\" TEXT," + // 5: title
                "\"CATEGORY\" TEXT," + // 6: category
                "\"URL\" TEXT);"); // 7: url
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEW_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NewData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String src = entity.getSrc();
        if (src != null) {
            stmt.bindString(2, src);
        }
 
        String weburl = entity.getWeburl();
        if (weburl != null) {
            stmt.bindString(3, weburl);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(5, pic);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(7, category);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NewData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String src = entity.getSrc();
        if (src != null) {
            stmt.bindString(2, src);
        }
 
        String weburl = entity.getWeburl();
        if (weburl != null) {
            stmt.bindString(3, weburl);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(5, pic);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(6, title);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(7, category);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public NewData readEntity(Cursor cursor, int offset) {
        NewData entity = new NewData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // src
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // weburl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pic
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // title
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // category
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // url
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NewData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSrc(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setWeburl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPic(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTitle(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCategory(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NewData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NewData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NewData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
