package db.Table;

import db.DBConstant;
import db.Entity.User;

/**
 * Created by ChengCe on 2017/12/4.
 */
public class UserTable extends AbstractTable<User>{
    public UserTable(){
        this.tableName = DBConstant.COLLECTION_USER;
        this.dbInit();
    }
}
