import org.junit.Test

import org.junit.Assert.*

class CommentNoteServiceTest {

    @Test
    fun addExisting() {
        val service = CommentNoteService()
        val note = service.add(
            CommentNote(
                cId = 0,      // идентификатор комментария
                message = "Text"    //Текст комментария
            )
        )
        val result = note != 0
        assertTrue(result)
    }

    @Test
    fun updateExisting() {
        val service = CommentNoteService()
        val updateId = service.add(CommentNote(message = "Привет"))
        service.add(CommentNote(message = "Пока"))
        val update = CommentNote(cId = updateId, message = "Ho-ho-ho")
        val result = service.update(update)
        assertTrue(result)
    }

    @Test
    fun updateDefunct() {
        val service = CommentNoteService()
        service.add(CommentNote(message = "Привет"))
        service.add(CommentNote(message = "Пока"))
        val update = CommentNote(cId = 0, message = "Ho-ho-ho")
        val result = service.update(update)
        assertFalse(result)
    }

    @Test
    fun deleteExisting() {
        val service = CommentNoteService()
        val deleteId = service.add(CommentNote(message = "Привет"))
        service.add(CommentNote(message = "Пока"))
        val result = service.delete(deleteId)
        assertTrue(result)
    }

    @Test
    fun deleteDefunct() {
        val service = CommentNoteService()
        service.add(CommentNote(message = "Привет"))
        service.add(CommentNote(message = "Пока"))
        val result = service.delete(123)
        assertFalse(result)
    }
}