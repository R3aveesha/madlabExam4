import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.madlab4.Note

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertNote(note: Note){
        val db=writableDatabase
        val values =ContentValues().apply{
                put(COLUMN_TITLE,note.title)
                put(COLUMN_CONTENT,note.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getAllnotes():List<Note>{
        val notesList= mutableListOf<Note>()
        val db=readableDatabase
        val query="SELECT*FROM $TABLE_NAME"
        val cursir=db.rawQuery(query,null)

        while (cursir.moveToNext()){
            val id=cursir.getInt(cursir.getColumnIndexOrThrow(COLUMN_ID))
            val title=cursir.getString(cursir.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursir.getString(cursir.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note=Note(id, title, content)
            notesList.add(note)
        }
        cursir.close()
        db.close()
        return notesList
    }

    fun deleteNote(noteId:Int){
        val db=writableDatabase
        val whereClause="$COLUMN_ID=?"
        val whereArgs= arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}
