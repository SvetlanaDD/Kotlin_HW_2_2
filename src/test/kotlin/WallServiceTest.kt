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
                comments = null,
                likes = null,
                attachment = null
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
        val updateId = service.add(Post(text="Hi, it's me",comments = null,likes = null, attachment = null)).id
        service.add(Post(text="Hello",comments = null,likes = null, attachment = null))
        service.add(Post(text="Bay",comments = null,likes = null, attachment = null))

        // создаём информацию об обновлении
        val update = Post(id=updateId, text="Ho-ho-ho",comments = null,likes = null, attachment = null)

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
        service.add(Post(text="Hi, it's me",comments = null,likes = null, attachment = null))
        service.add(Post(text="Hello",comments = null,likes = null, attachment = null))
        service.add(Post(text="Bay",comments = null,likes = null, attachment = null))

        // создаём информацию об обновлении
        val update = Post(id=0, text="Ho-ho-ho",comments = null,likes = null, attachment = null)

        // выполняем целевое действие
        val result = service.update(update)

        // проверяем результат (используйте assertTrue или assertFalse)
        assertFalse(result)
    }
}