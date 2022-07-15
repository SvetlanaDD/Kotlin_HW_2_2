//import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WallServiceTest {

    @Test
    fun addExisting() {
        val service = WallService()
        val post = service.add(
            Post(
                id = 0,         // Идентификатор записи
                date = 36000,   // Время публикации записи в формате unixtime
                text = "Hi!",   // Текст записи
            )
        )
        val result = post.id != 0
        assertTrue(result)
    }

    @Test
    fun updateExisting() {
        // создаём целевой сервис
        val service = WallService()
        // заполняем несколькими постами
        val updateId = service.add(Post(text="Hi, it's me")).id
        service.add(Post(text="Hello"))
        service.add(Post(text="Bay"))

        // создаём информацию об обновлении
        val update = Post(id=updateId, text="Ho-ho-ho")

        // выполняем целевое действие
        val result = service.update(update)

        // проверяем результат (используйте assertTrue или assertFalse)
        assertTrue(result)
    }

    @Test
    fun updateDefunct() {
        // создаём целевой сервис
        val service = WallService()
        // заполняем несколькими постами
        service.add(Post(text="Hi, it's me"))
        service.add(Post(text="Hello"))
        service.add(Post(text="Bay"))

        // создаём информацию об обновлении
        val update = Post(id=0, text="Ho-ho-ho")

        // выполняем целевое действие
        val result = service.update(update)

        // проверяем результат (используйте assertTrue или assertFalse)
        assertFalse(result)
    }
}