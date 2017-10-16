package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

import application.entity.Sector;
import application.entity.Source;
import application.entity.User;


/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelperORM extends OrmLiteSqliteOpenHelper {


	// name of the database file for your application -- change to something appropriate for your app
	public static final String DATABASE_NAME = "OLEAD.DB";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	private Dao<User, Integer> userDao = null;
	private Dao<Sector, Integer> sectorDao = null;
	private Dao<Source, Integer> sourceDao = null;


	public DatabaseHelperORM(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {

			Log.i(DatabaseHelperORM.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, User.class);

			TableUtils.createTable(connectionSource, Sector.class);
			TableUtils.createTable(connectionSource, Source.class);


		} catch (SQLException e) {
			Log.e(DatabaseHelperORM.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {

			Log.i(DatabaseHelperORM.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, User.class, true);

			TableUtils.dropTable(connectionSource, Sector.class, true);
			TableUtils.dropTable(connectionSource, Source.class, true);

			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelperORM.class.getName(), "Can't drop databases", e);
			e.printStackTrace();
		}
	}

	public Cursor getrawQuery(String query, String args[]) {
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor cursor = database.rawQuery(query, args);
		return cursor;
	}

	/* DAO Getters and Setters*/
	public Dao<User, Integer> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(User.class);
		}
		return userDao;
	}

	public Dao<Sector, Integer> getSectorDao() throws SQLException {
		if (sectorDao == null) {
			sectorDao = getDao(Sector.class);
		}
		return sectorDao;
	}

	public Dao<Source, Integer> getSourceDao() throws SQLException {
		if (sourceDao == null) {
			sourceDao = getDao(Source.class);
		}
		return sourceDao;
	}

}
