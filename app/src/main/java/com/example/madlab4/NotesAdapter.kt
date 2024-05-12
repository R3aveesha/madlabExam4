import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madlab4.Note
import com.example.madlab4.R
import com.example.madlab4.updateActivity


class NotesAdapter(private val notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener{
            val intent= Intent(holder.itemView.context,updateActivity::class.java).apply { this }

        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView=itemView.findViewById(R.id.deleteButton)
    }

    

    fun refreshData(newNotes: List<Note>) {
     var notes = newNotes
        notifyDataSetChanged()
    }

    fun updateNote(note:Note){
        val db=writableDatabase
        val values=contentValues().apply{
            put(COlOUMN_TITLE,note.title )
            put(COLOUMN_CONTENT,note.content)
        }
        val whereClause="$COLOUMN_ID=?"
        val whereArgs= arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()

    }
    fun getNoteByID(noteId: Int): Note{
        val db = readable Database
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId" val cursor = db.rawQuery (query, selectionArgs: null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString (cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString (cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                cursor.close()
                db.close()
            return Note (id, title, content)
}
