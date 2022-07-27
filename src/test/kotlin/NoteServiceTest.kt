import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addExisting() {
        val service = NoteService()
        val note = service.add(
            Note(
                nId = 0,      // идентификатор заметки
                title = "Заголовок",   //Заголовок заметки
                text = "Text",    //Текст заметки.
                isDelete = false
            )
        )
        val result = note != 0
        assertTrue(result)
    }

    @Test
    fun updateExisting() {
        val service = NoteService()
        val updateId = service.add(Note(title = "Привет", text = "Привет"))
        service.add(Note(title = "Пока", text = "Пока"))
        val update = Note(nId = updateId, text = "Ho-ho-ho")
        val result = service.update(update)
        assertTrue(result)
    }

    @Test
    fun updateDefunct() {
        val service = NoteService()
        val updateId = service.add(Note(title = "Привет", text = "Привет"))
        service.add(Note(title = "Пока", text = "Пока"))
        val update = Note(nId = 0, text = "Ho-ho-ho")
        val result = service.update(update)
        assertFalse(result)
    }

    @Test
    fun deleteExisting() {
        val service = NoteService()
        val deleteId = service.add(Note(title = "Привет", text = "Привет"))
        service.add(Note(title = "Пока", text = "Пока"))
        val result = service.delete(deleteId)
        assertTrue(result)
    }

    @Test
    fun deleteDefunct() {
        val service = NoteService()
        service.add(Note(title = "Привет", text = "Привет"))
        service.add(Note(title = "Пока", text = "Пока"))
        val result = service.delete(123)
        assertFalse(result)
    }
}