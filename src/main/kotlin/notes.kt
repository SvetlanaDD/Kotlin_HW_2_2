data class Note(
    val nId: Int = 0,      // идентификатор заметки
    val title: String = "Заголовок",   //Заголовок заметки
    val text: String = "Текст",    //Текст заметки.
    val privacy: Int = 0,    // Уровень доступа к заметке. 0 — все пользователи, 1 — только друзья, 2 — друзья и друзья друзей,
    //3 — только пользователь.
    val commentPrivacy: Int = 0, //Уровень доступа к комментированию заметки.0 — все пользователи, 1 — только друзья, 2 — друзья и друзья друзей,
    //3 — только пользователь.
    val privacyView: String = "q", //Настройки приватности просмотра заметки в специальном формате.
    val privacyComment: String = "w",  //Настройки приватности комментирования заметки в специальном формате.
    val isDelete: Boolean = false       //удалена заметка или нет. true - удалена, false - не удалена
)

data class CommentNote(
    val cId: Int = 0,       // Идентификатор комментария
    val noteId: Int = 0,    // Идентификатор заметки.
    val replyTo: Int = 0,   // Идентификатор пользователя, ответом на комментарий которого является добавляемый комментарий (не передаётся, если комментарий не является ответом).
    val message: String = "Текст комментария"    //Текст комментария.
)

class CommentNoteService : CrudService<CommentNote> {
    private var commentNotes = mutableListOf<CommentNote>()
    override fun add(element: CommentNote): Int {
        val cid = element.hashCode()
        commentNotes += element.copy(cId = cid)
        return cid
    }

    override fun update(element: CommentNote): Boolean {
        for ((index, oldCommentNote) in commentNotes.withIndex()) {
            if (element.cId == oldCommentNote.cId) {
                commentNotes[index] = oldCommentNote.copy(
                    oldCommentNote.cId,
                    element.noteId,
                    element.replyTo,
                    element.message
                )
                return true
            }
        }
        return false
    }

    override fun delete(id: Int): Boolean {
        for ((index, note) in commentNotes.withIndex()) {
            if (note.cId == id) {
                commentNotes.removeAt(index)
                return true
            }
        }
        return false
    }

}

class NoteService : CrudService<Note> {
    private var notes = mutableListOf<Note>()
    override fun add(element: Note): Int {
        val nid = element.hashCode()
        notes.add(element.copy(nId = nid))
        return nid
    }

    override fun update(element: Note): Boolean {
        for ((index, oldNote) in notes.withIndex()) {
            if (element.nId == oldNote.nId) {
                notes[index] = oldNote.copy(
                    oldNote.nId,
                    element.title,
                    element.text,
                    element.privacy,
                    element.commentPrivacy,
                    element.privacyView,
                    element.privacyComment,
                    element.isDelete
                )
                return true
            }
        }
        return false
    }

    override fun delete(id: Int): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.nId == id) {
                notes[index] = note.copy(isDelete = true)
                return true
            }
        }
        return false
    }
}


// https://overcoder.net/q/1029018/%D0%BA%D0%BE%D1%82%D0%BB%D0%B8%D0%BD-%D0%BA%D0%B0%D0%BA-%D0%B2%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D1%81%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82%D0%BE%D0%B2-%D0%B2-%D0%BA%D0%BE%D0%BC%D0%BD%D0%B0%D1%82%D1%83
interface CrudService<T> {
    fun add(element: T): Int
    fun update(element: T): Boolean
    fun delete(id: Int): Boolean
}

